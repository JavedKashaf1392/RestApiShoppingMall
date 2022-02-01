package com.ps.view;

import java.awt.Color;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.ps.model.Category;
import com.ps.model.Product;

public class CategoryPdfView extends AbstractPdfView{
	
	 @Override
	protected void buildPdfMetadata(Map<String, Object> model,
			Document document, HttpServletRequest request) {
		 
		 HeaderFooter header=new HeaderFooter(new Phrase("CATEGORY DATA INFORMATION"),false);
		 HeaderFooter footer=new HeaderFooter(new Phrase("Copyrights @ Prospecta Tech Ltd |" +new Phrase( new Date().toString() +"  PAGE NUMBER# ")),new Phrase("."));
		 
		 header.setAlignment(1);
		 header.setBorder(Rectangle.BOTTOM);
		 
		 footer.setAlignment(2);
		 footer.setBorder(Rectangle.TOP);
		 
		 document.setHeader(header);
		 document.setFooter(footer);	 
	}
	 
	 
	@Override
	protected void buildPdfDocument(Map<String, Object> model,
			Document document, PdfWriter writer,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		//file name and download
		  response.addHeader("Content-Disposition", "attachment;filename=Product.pdf");
		
				Image image=Image.getInstance(new ClassPathResource("/static/image/javedlogo.png").getURL());
			//		image.scaleToFit(600,600);
				image.setAlignment(Image.MIDDLE);
				 image.scaleAbsolute(300.0f, 80.0f);
				 image.setBorderWidth(5.0f);
				 image.enableBorderSide(Rectangle.BOTTOM + Rectangle.TOP +Rectangle.LEFT + Rectangle.RIGHT);
				document.add(image);
		//font design here 
		Font fontTitle=new Font(Font.HELVETICA,20,Font.BOLD,new Color(222,161,20));
	    Paragraph p=new Paragraph("WELCOME TO CATEGORY PDF REPORT",fontTitle);
	    p.setAlignment(Paragraph.ALIGN_CENTER);
	    document.add(p);
	    
	    //reading data 
	    
	    @SuppressWarnings("unchecked")
		List<Category> list=(List<Category>) model.get("list");
		
		//Create one table with no.of columns to be display
				PdfPTable table = new PdfPTable(5); //no of columns
				table.setSpacingBefore(4.0f);
				table.setTotalWidth(new float[] {1.0f,1.0f,1.0f,1.0f,1.0f});
				
				// Defiles the relative width of the columns
	            float[] columnWidths = new float[]{10f, 10f, 10f, 10f, 10f, };
	            table.setWidths(columnWidths);
				
				
				//add data to columns using addCell method
				//once 6 cells done then it will chnage row automatically
				
				Font font1=new Font(Font.COURIER,8,Font.ITALIC, Color.BLUE);
				Font font2=new Font(Font.TIMES_ROMAN,8,Font.BOLD,Color.RED);
				
				table.addCell(new Phrase("ID",font1));
				table.addCell(new Phrase("Name",font1));
				table.addCell(new Phrase("Alias",font1));
				table.addCell(new Phrase("Image",font1));
				table.addCell(new Phrase("Enabled",font1));
				
				for(Category p1:list) {
					table.addCell(new Phrase(p1.getId().toString(),font2));
					/*table.addCell(st.getId().toString());*/
					table.addCell(p1.getName());
					table.addCell(String.valueOf(p1.getAlias()));
					table.addCell(String.valueOf(p1.getImage()));
					table.addCell(String.valueOf(p1.isEnabled()));
				}
				//add table to document
				document.add(table);
	}
}
