package com.javafee.hibernate.dto.library;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

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
