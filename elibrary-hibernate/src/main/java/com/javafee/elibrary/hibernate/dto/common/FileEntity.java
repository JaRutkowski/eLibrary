package com.javafee.elibrary.hibernate.dto.common;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.google.gson.Gson;
import com.javafee.elibrary.hibernate.dto.common.extdata.ExtFileMetadata;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class FileEntity {
	@Column(name = "file")
	private byte[] file;

	@Column(name = "ext_file_metadata", columnDefinition = "text")
	private String extFileMetadata;

	public ExtFileMetadata getExtFileMetadata() {
		return new Gson().fromJson(extFileMetadata, ExtFileMetadata.class);
	}

	public void setExtFileMetadata(ExtFileMetadata extFileMetadata) {
		this.extFileMetadata = new Gson().toJson(extFileMetadata);
	}
}
