package com.javafee.elibrary.core.common.filemapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class FileMapper implements MapperStrategy {
	private MapperStrategy mapperStrategy;

	public FileMapper(MapperStrategy mapperStrategy) {
		this.mapperStrategy = mapperStrategy;
	}

	@Override
	public File to(List<List<Object>> data, Path path) throws IOException {
		return mapperStrategy.to(data, path);
	}

	@Override
	public List<List<Object>> from(File file) throws IOException {
		return mapperStrategy.from(file);
	}
}
