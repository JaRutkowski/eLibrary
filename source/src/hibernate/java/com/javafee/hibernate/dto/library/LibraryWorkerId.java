package com.javafee.hibernate.dto.library;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class LibraryWorkerId implements Serializable {
	private static final long serialVersionUID = 9005724542871956989L;

	@ManyToOne
	private Worker worker;

	@ManyToOne
	private LibraryData libraryData;

	public Worker getWorker() {
		return worker;
	}

	public void setWorker(Worker worker) {
		this.worker = worker;
	}

	public LibraryData getLibraryData() {
		return libraryData;
	}

	public void setLibraryData(LibraryData libraryData) {
		this.libraryData = libraryData;
	}
}
