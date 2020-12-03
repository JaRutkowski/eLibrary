package com.javafee.elibrary.web.rest.api.elibrarywebrestapi.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javafee.elibrary.web.rest.api.elibrarywebrestapi.model.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
