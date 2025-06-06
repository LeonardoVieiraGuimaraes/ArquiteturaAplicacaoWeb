package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Author;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.security.SecurityService;
import com.example.demo.model.User;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private SecurityService securityService;

    public Author saveAuthor(Author author) {
        User user = securityService.getLoggedUser();
        if (user != null) {
            author.setUser(user);
        }
        return authorRepository.save(author);
    }

    public Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Optional<Author> updateAuthor(Long id, Author updatedAuthor) {
        return authorRepository.findById(id).map(existingAuthor -> {
            existingAuthor.setNome(updatedAuthor.getNome());
            existingAuthor.setDataNascimento(updatedAuthor.getDataNascimento());
            existingAuthor.setNacionalidade(updatedAuthor.getNacionalidade());
            return authorRepository.save(existingAuthor);
        });
    }

    public boolean deleteAuthor(Long id) {
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
