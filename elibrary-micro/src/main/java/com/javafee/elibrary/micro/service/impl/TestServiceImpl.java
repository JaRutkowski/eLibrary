package com.javafee.elibrary.micro.service.impl;

import java.util.List;

import com.javafee.elibrary.micro.infrastructure.aspect.annotation.AutoLoggable;
import com.javafee.elibrary.micro.service.TestService;
import org.springframework.stereotype.Service;

import com.javafee.elibrary.micro.model.domain.Test;
import com.javafee.elibrary.micro.model.repository.TestRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
	private final TestRepository testRepository;

	@AutoLoggable
	public List<Test> getAllById(Integer id) {
		return testRepository.findAllById(List.of(id));
	}

	@AutoLoggable
	public List<Test> getAllV2() {
		return testRepository.findAll();
	}

	@AutoLoggable
	public Test save(Test test) {
		testRepository.save(test);
		return test;
	}

	@Override
	public List<Test> getAllV3(String text, int number) {
		return testRepository.findAllByNameContainingAndIdGreaterThan(text, number);
	}

}
