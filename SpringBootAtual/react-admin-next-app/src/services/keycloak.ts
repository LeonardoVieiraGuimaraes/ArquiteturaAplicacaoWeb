import Keycloak from 'keycloak-js';

const keycloak = new Keycloak({
  url: 'http://localhost:8080/auth',
  realm: 'demo-realm', // ajuste para o nome do seu realm
  clientId: 'react-admin-app', // ajuste para o clientId do frontend
});

export default keycloak;
