package com.api.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "itens")
public class Item {
    @Id
    private String id;
    private String nome;
    private String descricao;
}