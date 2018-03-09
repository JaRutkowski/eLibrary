package com.javafee.hibernate.dto.library;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude = {"worker", "libraryData"})
@Embeddable
public class LibraryWorkerId implements Serializable {
	private static final long serialVersionUID = 9005724542871956989L;

	@ManyToOne
	private Worker worker;

	@ManyToOne
	private LibraryData libraryData;
}
