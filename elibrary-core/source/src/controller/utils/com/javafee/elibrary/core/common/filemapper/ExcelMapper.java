package com.javafee.elibrary.core.common.filemapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

import com.javafee.elibrary.core.common.SystemProperties;

public class ExcelMapper implements MapperStrategy {
	@Override
	public File to(List<List<Object>> data, Path path) throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook();
		// CreationHelper createHelper = workbook.getCreationHelper();
		HSSFSheet sheet = workbook.createSheet(SystemProperties.getInstance().getResourceBundle().getString("excelMapper.sheetName"));

		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		//headerFont.setFontHeightInPoints((short) 14);
		//headerFont.setColor(IndexedColors.RED.getIndex());

		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		HSSFRow headerRow = sheet.createRow(0);

		int cellNumber = 0;
		for (Object headerCell : data.get(0)) {
			HSSFCell cell = headerRow.createCell(cellNumber);
			cell.setCellValue(headerCell != null ? headerCell.toString() : "");
			cell.setCellStyle(headerCellStyle);
			cellNumber++;
		}

		// Create Cell Style for formatting Date
		// CellStyle dateCellStyle = workbook.createCellStyle();
		// dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

		int rowNum = 0;
		for (List<Object> row : data) {
			if (rowNum != 0) {
				HSSFRow sheetRow = sheet.createRow(rowNum);
				int colNum = 0;
				for (Object cell : row) {
					sheetRow.createCell(colNum)
							.setCellValue(cell != null ? cell.toString() : "");
					colNum++;
				}
			}
			rowNum++;
		}

		for (int i = 0; i < data.get(0).size(); i++)
			sheet.autoSizeColumn(i);

		File file = path.toFile();
		FileOutputStream fileOut = new FileOutputStream(file);
		workbook.write(fileOut);

		fileOut.close();
		workbook.close();
		return file;
	}

	@Override
	public List<List<Object>> from(File file) throws IOException {
		return null;
	}
}
