package br.pucrio.inf.les.ese.dianalyzer.worker.report;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import br.pucrio.inf.les.ese.dianalyzer.worker.logic.ProjectExecutor;

public class WorkbookCreator implements IWorkbookCreator {
	
	private final Log log = LogFactory.getLog(WorkbookCreator.class);

	//TODO remove generic exceptions. Create exceptions specific for problem. Handle it here
	@Override
	public void create(Report report, String outputPath) throws IOException, FileNotFoundException {
		
		Workbook wb = new HSSFWorkbook();
		//Workbook wb = new XSSFWorkbook();
		CreationHelper createHelper = wb.getCreationHelper();
		Sheet sheet = wb.createSheet(report.getProject());

		// Create the header
		Row headerRow = sheet.createRow((short)0);
		// Put values
		for(int i=0;i<report.getHeaders().size();i++){
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(report.getHeaders().get(i));
		}

		// Put results now
		for(int i=0;i<report.getLines().size();i++){
			int index = i + 1;
			
			Row row = sheet.createRow((short)index);
			
			for(int column=0;column<report.getLines().get(i).size();column++){				
				String cellValue = report.getLines().get(i).get(column);
				row.createCell(column).setCellValue(cellValue);
			}
		}
		
		Date date = new Date();
		
		String dateForFilename = date.getYear()+"-"+date.getMonth()+"-"+date.getDay()+"-"+date.getHours()+"-"+date.getMinutes()+"-"+date.getSeconds();

		// Write the output to a file
		FileOutputStream fileOut = new FileOutputStream(outputPath+"\\workbook"+dateForFilename+".xls");
		wb.write(fileOut);
		fileOut.close();
		
		wb.close();
		
	}
	
	

}
