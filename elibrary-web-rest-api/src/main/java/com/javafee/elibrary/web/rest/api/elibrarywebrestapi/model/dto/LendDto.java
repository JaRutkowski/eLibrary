package com.javafee.elibrary.web.rest.api.elibrarywebrestapi.model.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LendDto {
	private String title;
	private String isbnNumber;
	private String inventoryNumber;
	private Date lendDate;
	private Date returnDate;
}
