package com.cadastro.tarefas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "tb_roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;


    public enum Values {

        ADMIN(1L),
        BASIC(2L);

        long Id;

        Values(long Id) {
            this.Id = Id;
        }

        public long getRoleId() {
            return Id;
        }
    }
}