package com.javafee.elibrary.micro.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javafee.elibrary.micro.model.domain.Test;

import java.util.List;

public interface TestRepository extends JpaRepository<Test, Integer> {
     List<Test> findAllByNameContainingAndIdGreaterThan(String text, int number);
}
