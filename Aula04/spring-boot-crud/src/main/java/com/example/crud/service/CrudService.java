package com.example.crud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.crud.model.CrudModel;
import com.example.crud.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CrudService {

    @Autowired
    private CrudRepository crudRepository;

    public List<CrudModel> findAll() {
        return crudRepository.findAll();
    }

    public Optional<CrudModel> findById(Long id) {
        return crudRepository.findById(id);
    }

    public CrudModel save(CrudModel crudModel) {
        return crudRepository.save(crudModel);
    }

    public void deleteById(Long id) {
        crudRepository.deleteById(id);
    }
}