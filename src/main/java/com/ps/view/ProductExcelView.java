package com.ps.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.ps.model.Product;

public class ProductExcelView extends AbstractXlsView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// Download + filename
		response.setHeader("Content-Disposition", "attachment;filename=Product.xls");

		// read data from controller
		@SuppressWarnings("unchecked")
		List<Product> list = (List<Product>) model.get("obs");

		Sheet sheet = workbook.createSheet("Uom Report");
		setHead(sheet);
		setBody(sheet, list);
	}

	private void setHead(Sheet sheet) {
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue(" NAME");
		row.createCell(2).setCellValue("PRICE");
		row.createCell(3).setCellValue("QUANTITY");
		row.createCell(4).setCellValue("DISCOUNT");
		row.createCell(5).setCellValue("AVILABLE");
		row.createCell(6).setCellValue("PRICE");
		row.createCell(7).setCellValue("SIZE");
		row.createCell(8).setCellValue("TAX");
		row.createCell(9).setCellValue("View");
		row.createCell(10).setCellValue("IMAGE");

	}

	private void setBody(Sheet sheet, List<Product> list) {
		int rowNum = 1;
		for (Product p : list) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(p.getId());
			row.createCell(1).setCellValue(p.getProductName());
			row.createCell(2).setCellValue(p.getProductPrice());
			row.createCell(3).setCellValue(p.getProductQyt());
			row.createCell(4).setCellValue(p.getDiscount());
			row.createCell(5).setCellValue(p.isAvailable());
			row.createCell(6).setCellValue(p.getFinalPrice());
			row.createCell(7).setCellValue(p.getSize());
			row.createCell(8).setCellValue(p.getTax());
			row.createCell(9).setCellValue(p.getView());
			
			row.createCell(10).setCellValue(String.valueOf(p.getImage()));
		}

	}
}