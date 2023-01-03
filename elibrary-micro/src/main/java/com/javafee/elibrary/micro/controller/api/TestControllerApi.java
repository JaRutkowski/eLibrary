package com.javafee.elibrary.micro.controller.api;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.javafee.elibrary.micro.infrastructure.apiversion.ApiVersion;
import com.javafee.elibrary.micro.model.domain.Test;

@RequestMapping(value = "/api/test", produces = MediaType.APPLICATION_JSON_VALUE)
@ApiVersion(1)
// @Validated //enable for f.eg. REST params validation
public interface TestControllerApi {
	@GetMapping("/{id}")
	ResponseEntity findAllById(@PathVariable Integer id); // http://localhost:8082/api/test

	@GetMapping
	@ApiVersion(2)
	ResponseEntity findAllV2();// http://localhost:8082/v2/api/test

	@PostMapping
	ResponseEntity save(@Valid @RequestBody Test test);

	@GetMapping("/filter/{text}/{number}")
	ResponseEntity findAllV3(@PathVariable String text, @PathVariable int number);// http://localhost:8082/api/test

	// request params:
	// http://localhost:8082/api/test?id=1&name=Jan
	// @RequestParam

	// path param:
	// http://localhost:8082/api/test/1
	// @PathVariable

	// request params&path param:
	// @GetMapping(name = "/filter/pattern/{text}/id/{id}/search")
	// ResponseEntity findAllV3(@PathVariable String text, @PathVariable Integer id, @RequestParam String name, @RequestParam Date date); // http://localhost:8082/api/test
	// http://localhost:8082/api/test/filter/pattern/choinka/id/41/search?name=XXX&date=2022-01-01
	// http://localhost:8082/api/test/filter/xxx/222
}
