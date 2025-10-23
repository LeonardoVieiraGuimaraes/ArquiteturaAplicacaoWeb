package com.example.demo.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.lang.NonNull;

/**
 * Extracts roles from Keycloak JWT (realm_access.roles and resource_access.*.roles)
 * and maps them to Spring Security authorities with the prefix ROLE_.
 */
public class KeycloakRealmRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(@NonNull Jwt jwt) {
        List<String> roles = new ArrayList<>();

        // Realm roles
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        if (realmAccess != null) {
            Object realmRoles = realmAccess.get("roles");
            if (realmRoles instanceof List<?> list) {
                roles.addAll(list.stream().filter(Objects::nonNull).map(Object::toString).toList());
            }
        }

        // Client roles from all resource_access entries
        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        if (resourceAccess != null) {
            for (Object clientObj : resourceAccess.values()) {
                if (clientObj instanceof Map<?, ?> clientMap) {
                    Object clientRoles = clientMap.get("roles");
                    if (clientRoles instanceof List<?> list) {
                        roles.addAll(list.stream().filter(Objects::nonNull).map(Object::toString).toList());
                    }
                }
            }
        }

        // Map roles -> ROLE_<role>
        return roles.stream()
                .distinct()
                .map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }
}
