package com.javafee.elibrary.micro.infrastructure.exception;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import com.javafee.elibrary.micro.config.SystemProperties;
import com.javafee.elibrary.micro.service.dto.JfsExceptionDto;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
	// error handler for @Valid
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
	                                                              HttpHeaders headers,
	                                                              HttpStatus status, WebRequest request) {
		log.error(getEndpointData(request) + e.getLocalizedMessage());
		Map<String, Object> body = prepareResponseMap(e, status);
		return new ResponseEntity<>(body, headers, status);
	}

	// error handler for Exception, code INTERNAL_SERVER_ERROR
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<?> exception(Exception e, WebRequest request) {
		log.error(getEndpointData(request) + e.getLocalizedMessage());
		return new ResponseEntity<>(prepareCustomExceptionDto(e, HttpStatus.INTERNAL_SERVER_ERROR, Collections.singletonList(e.getMessage()),
				request.getDescription(false), ((ServletWebRequest) request).getHttpMethod().toString()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e, WebRequest request) {
		log.error(getEndpointData(request) + e.getLocalizedMessage());
		return new ResponseEntity<>(prepareCustomExceptionDto(e, HttpStatus.BAD_REQUEST, Collections.singletonList(e.getMessage()),
				request.getDescription(false), ((ServletWebRequest) request).getHttpMethod().toString()),
				HttpStatus.BAD_REQUEST);
	}

	private String getEndpointData(WebRequest request) {
		return MessageFormat.format(SystemProperties.getResourceBundle().getString("aspects.logTemplate.customGlobalExceptionHandler"),
				((ServletWebRequest) request).getHttpMethod().toString(), request.getDescription(false));
	}

	private Map prepareResponseMap(Exception e, HttpStatus status) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", new Date());
		body.put("status", status.value());

		//Get all errors
		List<String> errors = new ArrayList<>();
		if (e instanceof MethodArgumentNotValidException)
			errors = ((MethodArgumentNotValidException) e).getBindingResult()
					.getFieldErrors()
					.stream()
					.map(DefaultMessageSourceResolvable::getDefaultMessage)
					.collect(Collectors.toList());
		else
			errors.add(e.getMessage());
		body.put("errors", errors);
		return body;
	}

	private JfsExceptionDto prepareCustomExceptionDto(Exception e, HttpStatus status, List<String> messages, String path, String method) {
		return JfsExceptionDto.builder()
				.timestamp(new Date())
				.status(status.value())
				.messages(messages)
				.path(path)
				.method(method)
				.build();
	}
}
