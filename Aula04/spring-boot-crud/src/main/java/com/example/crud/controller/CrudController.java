package com.example.crud.controller;

import com.example.crud.model.CrudModel;
import com.example.crud.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crud")
public class CrudController {

    @Autowired
    private CrudService crudService;

    @PostMapping
    public ResponseEntity<CrudModel> create(@RequestBody CrudModel crudModel) {
        CrudModel createdModel = crudService.create(crudModel);
        return ResponseEntity.ok(createdModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CrudModel> read(@PathVariable Long id) {
        CrudModel foundModel = crudService.read(id);
        return ResponseEntity.ok(foundModel);
    }

    @GetMapping
    public ResponseEntity<List<CrudModel>> readAll() {
        List<CrudModel> models = crudService.readAll();
        return ResponseEntity.ok(models);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CrudModel> update(@PathVariable Long id, @RequestBody CrudModel crudModel) {
        CrudModel updatedModel = crudService.update(id, crudModel);
        return ResponseEntity.ok(updatedModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        crudService.delete(id);
        return ResponseEntity.noContent().build();
    }
}