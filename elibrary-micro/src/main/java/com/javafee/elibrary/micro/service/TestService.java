package com.javafee.elibrary.micro.service;

import com.javafee.elibrary.micro.model.domain.Test;

import java.util.List;

public interface TestService {
    List<Test> getAllById(Integer id);

    List<Test> getAllV2();

    Test save(Test test);

    List<Test> getAllV3(String text, int number);
}
