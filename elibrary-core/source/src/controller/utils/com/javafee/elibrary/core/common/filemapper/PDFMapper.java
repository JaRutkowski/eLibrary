package com.javafee.elibrary.core.common.filemapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.javafee.elibrary.core.common.Utils;

import lombok.extern.java.Log;

@Log
public class PDFMapper implements MapperStrategy {
	private Font font = FontFactory.getFont(Utils.getApplicationFont().getFontName(), 6);

	@Override
	public File to(List<List<Object>> data, Path path) {
		File file = path.toFile();
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			Document document = new Document();
			PdfWriter.getInstance(document, fileOutputStream);
			document.open();

			PdfPTable table = new PdfPTable(data.get(0).size());
			addTableHeader(table, data.get(0));
			addRows(table, data);
			document.add(table);

			document.close();
			fileOutputStream.close();
		} catch (DocumentException | IOException e) {
			log.severe(e.getMessage());
		}
		return file;
	}

	@Override
	public List<List<Object>> from(File file) {
		return null;
	}

	private void addTableHeader(PdfPTable table, List<Object> header) {
		header.forEach(cell -> {
			PdfPCell pdfCellHeader = new PdfPCell();
			pdfCellHeader.setBackgroundColor(Utils.getApplicationUserDefinedColorAsItextpdfBaseColor());
			pdfCellHeader.setBorderWidth(0.5f);
			pdfCellHeader.setPhrase(new Phrase(cell != null ? cell.toString() : "", font));
			table.addCell(pdfCellHeader);
		});
	}

	private void addRows(PdfPTable table, List<List<Object>> data) {
		int rowIndex = 0;
		for (List<Object> row : data) {
			if (rowIndex != 0)
				for (Object cell : row)
					table.addCell(new Phrase(cell != null ? cell.toString() : "", font));
			rowIndex++;
		}
	}
}
