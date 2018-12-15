package br.pucrio.inf.les.ese.dianalyzer.worker.report;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class WorkbookCreator implements IWorkbookCreator {
	
	private final Log log = LogFactory.getLog(WorkbookCreator.class);

	//TODO remove generic exceptions. Create exceptions specific for problem. Handle it here
	@Override
	public void create(Report report, String outputPath) throws IOException, FileNotFoundException {
		
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet(report.getProject());

		// Create the header
		Row headerRow = sheet.createRow((short)0);
		// Put values
		for(int i=0;i<report.getHeaders().size();i++){
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(report.getHeaders().get(i));
		}
		
		log.info("Header created.");

		// Put results now
		for(int i=0;i<report.getLines().size();i++){
			int index = i + 1;
			
			Row row = sheet.createRow((short)index);
			
			for(int column=0;column<report.getLines().get(i).size();column++){				
				String cellValue = report.getLines().get(i).get(column);
				row.createCell(column).setCellValue(cellValue);
			}
		}
		
		log.info("Lines processed.");
		
		String filename = buildFilename(outputPath,report.getProject());

		// Write the output to a file
		FileOutputStream fileOut = new FileOutputStream(filename);
		wb.write(fileOut);
		fileOut.close();
		
		wb.close();
		
	}
	
	private String buildFilename(String outputPath, String basefilename){
		
		LocalDateTime now = LocalDateTime.now();
		int year = now.getYear();
		int month = now.getMonthValue();
		int day = now.getDayOfMonth();
		int hour = now.getHour();
		int minute = now.getMinute();
		int second = now.getSecond();
		int millis = now.get(ChronoField.MILLI_OF_SECOND); 
		
		String dateForFilename = year+"_"+month+"_"+day+"_"+hour+"_"+minute+"_"+second+"_"+millis;
		
		return outputPath+"\\"+basefilename+"_"+dateForFilename+".xls";
		
	}

}
