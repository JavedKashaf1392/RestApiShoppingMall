package com.ps.view;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ps.model.Product;

public class ExcelNewView {
	
	public static ByteArrayInputStream customersToExcel(List<Product>products) throws IOException {
		String[] COLUMNs = {"ID", "NAME", "PRICE","QUANTITY","View", "DISCOUNT","AVILABLE","SIZE","TAX"};
		
		
		
		try(
				Workbook workbook = new XSSFWorkbook();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
		){
			CreationHelper createHelper = workbook.getCreationHelper();
	 
			Sheet sheet = workbook.createSheet("Products");
	 
			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.BLUE.getIndex());
	 
			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);
	 
			// Row for Header
			Row headerRow = sheet.createRow(0);
	 
			// Header
			for (int col = 0; col < COLUMNs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(COLUMNs[col]);
				cell.setCellStyle(headerCellStyle);
			}
	 
			// CellStyle for Age
			CellStyle ageCellStyle = workbook.createCellStyle();
			ageCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#"));
	 
			int rowIdx = 1;
			for (Product product : products) {
				Row row = sheet.createRow(rowIdx++);
	 
				row.createCell(0).setCellValue(product.getId());
				row.createCell(1).setCellValue(product.getProductName());
				row.createCell(2).setCellValue(product.getProductPrice());
				
				row.createCell(3).setCellValue(product.getProductQyt());
				row.createCell(4).setCellValue(product.getView());
				row.createCell(5).setCellValue(product.getDiscount());
				row.createCell(6).setCellValue(product.isAvailable());
				
				
				
				row.createCell(7).setCellValue(product.getSize());
				row.createCell(8).setCellValue(product.getTax());
			}
	 
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		}
	}

}
