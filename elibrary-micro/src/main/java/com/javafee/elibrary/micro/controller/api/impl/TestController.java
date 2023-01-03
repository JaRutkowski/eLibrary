package com.javafee.elibrary.micro.controller.api.impl;

import com.javafee.elibrary.micro.infrastructure.aspect.annotation.AutoMeasuringTime;
import com.javafee.elibrary.micro.service.impl.TestServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.javafee.elibrary.micro.controller.api.TestControllerApi;
import com.javafee.elibrary.micro.model.domain.Test;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TestController implements TestControllerApi {
	private final TestServiceImpl testServiceImpl;

	@AutoMeasuringTime
	public ResponseEntity findAllById(Integer id) {
		return ResponseEntity.ok(testServiceImpl.getAllById(id));
	}

	@AutoMeasuringTime
	public ResponseEntity findAllV2() {
		return ResponseEntity.ok(testServiceImpl.getAllV2());
	}

	@AutoMeasuringTime
	public ResponseEntity save(Test test) {
		return ResponseEntity.status(HttpStatus.CREATED).body(testServiceImpl.save(test));
	}

	@AutoMeasuringTime
	public ResponseEntity findAllV3(String text, int number){
		return ResponseEntity.status(HttpStatus.CREATED).body(testServiceImpl.getAllV3(text, number));
	}
}
