package com.javafee.elibrary.core.common.filemapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface MapperStrategy {
	File to(List<List<Object>> data, Path path) throws IOException;

	List<List<Object>> from(File file) throws IOException;
}
