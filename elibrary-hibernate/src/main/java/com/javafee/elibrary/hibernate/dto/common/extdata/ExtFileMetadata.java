package com.javafee.elibrary.hibernate.dto.common.extdata;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExtFileMetadata {
	private String localPath;
	private String extension;
	private Long size;
}
