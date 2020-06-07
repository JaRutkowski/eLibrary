package com.javafee.elibrary.core.common.filemapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import com.javafee.elibrary.core.common.Constants;
import com.opencsv.CSVWriter;

public class CSVMapper implements MapperStrategy {
	@Override
	public File to(List<List<Object>> data, Path path) throws IOException {
		File file = path.toFile();
		FileWriter outputFile = new FileWriter(file);

		CSVWriter writer = new CSVWriter(outputFile, Constants.APPLICATION_CSV_SEPARATOR,
				CSVWriter.NO_QUOTE_CHARACTER,
				CSVWriter.DEFAULT_ESCAPE_CHARACTER,
				CSVWriter.DEFAULT_LINE_END);

		data.forEach(row -> {
			String[] rowStrings = new String[row.size()];
			for (var index = 0; index < row.size(); index++)
				rowStrings[index] = row.get(index) != null ? row.get(index).toString() : "";
			writer.writeNext(rowStrings);
		});

		writer.close();
		return file;
	}

	@Override
	public List<List<Object>> from(File file) throws IOException {
		return null;
	}
}
