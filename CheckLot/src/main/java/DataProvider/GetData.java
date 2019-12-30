package DataProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.groovy.ast.stmt.SwitchStatement;

public class GetData {
	XSSFSheet sheet;
	XSSFWorkbook wb;
	Cell cell;
	int lastRow;
	XSSFSheet sh;
	List<Integer> lotNumber = new ArrayList<Integer>();
	List<Integer> lotStage = new ArrayList<Integer>();
	List<Integer> sellerCode = new ArrayList<Integer>();
	List<String> Scenario = new ArrayList<String>();
	List<String> resposne = new ArrayList<String>();
	List<String> sellerCodes = new ArrayList<String>();
	List<String> lots = new ArrayList<String>();
	List<String> convertedCodes = new ArrayList<String>();
	List<String> queries = new ArrayList<String>();

	public XSSFSheet getSheets(String sheets) throws IOException {
		File file = new File("C:\\Users\\shbiradar\\workspace\\CheckLot\\CheckLot2.xlsx");

		FileInputStream ip = new FileInputStream(file);
		wb = new XSSFWorkbook(ip);
		sheet =wb.getSheet(sheets);
		return sheet;
	}

	public void getData() throws IOException {
		GetData g=new GetData();
		sh=g.getSheets("Seller");
		lastRow = sh.getLastRowNum();
		//queries.clear();
		for (int i = 1; i <= lastRow; i++) {
			DataFormatter formatter = new DataFormatter();
			Row row = sh.getRow(i);

			for (int j = 0; j < row.getLastCellNum(); j++) {

				if (sh.getRow(0).getCell(j).getStringCellValue().equalsIgnoreCase("Lot Number")) {

					if (!formatter.formatCellValue(row.getCell(j)).equals("")) {
						lotNumber.add(Integer.parseInt(formatter.formatCellValue(row.getCell(j))));
					}

				} else if (sh.getRow(0).getCell(j).getStringCellValue().equalsIgnoreCase("Lot Stage")) {

					lotStage.add(Integer.parseInt(formatter.formatCellValue(row.getCell(j))));
				} else if (sh.getRow(0).getCell(j).getStringCellValue().equalsIgnoreCase("Seller Code")) {

					if (!formatter.formatCellValue(row.getCell(j)).equals("")) {
						sellerCode.add(Integer.parseInt(formatter.formatCellValue(row.getCell(j))));
					}
				} else if (sh.getRow(0).getCell(j).getStringCellValue().equalsIgnoreCase("Scenario")) {

					Scenario.add(formatter.formatCellValue(row.getCell(j)));
				} else if (sh.getRow(0).getCell(j).getStringCellValue()
						.equalsIgnoreCase("Expected English Response")) {

					resposne.add(formatter.formatCellValue(row.getCell(j)));
				} else if (sh.getRow(0).getCell(j).getStringCellValue().equalsIgnoreCase("SQL Queries")) {

					queries.add(formatter.formatCellValue(row.getCell(j)));
				}

			}
		}
	}

	public List<String> convertSellerCode() throws ClassNotFoundException, IOException, SQLException {
		ConnectionDB db=new ConnectionDB();
		sellerCodes = db.getLots("LTSLRNBR");
		String result = "";
		System.out.println(sellerCodes);
		for (int i = 0; i < sellerCodes.size(); i++) {
			char[] c = sellerCodes.get(i).toCharArray();

			for (char d : c) {

				if (d <= '9')
					result += d;
				else if (d <= 'C')
					result += "2";
				else if (d <= 'F')
					result += "3";
				else if (d <= 'I')
					result += "4";
				else if (d <= 'L')
					result += "5";
				else if (d <= 'O')
					result += "6";
				else if (d <= 'S')
					result += "7";
				else if (d <= 'V')
					result += "8";
				else if (d <= 'Z')
					result += "9";
				else
					result += d;
			}
			convertedCodes.add(result);
			result = "";
		}
		System.out.println(convertedCodes);
		return convertedCodes;

	}

	public void writeData() throws IOException, ClassNotFoundException, SQLException {
		ConnectionDB db=new ConnectionDB();
		GetData g=new GetData();
		lots.addAll(db.getLots("LTLOTNBR"));
		System.out.println(lots);
		sh=g.getSheets("Seller");
		int lastRow = sh.getLastRowNum();
		for (int i = 1; i < lastRow; i++) {
			Row row = sh.getRow(i);
			for (int k = 0; k <= 2; k++) {
				for (int j = 0; j < lots.size(); j++) {

					if (k == 0) {
						String lot = lots.get(j);
						row.getCell(k).setCellValue(lot);
					} else if (k == 2) {
						String code = convertedCodes.get(j);
						row.getCell(2).setCellValue(code);
					} else {
						k++;
					}
				}
			}
		}
		try{
			FileOutputStream op=new FileOutputStream("C:\\Users\\shbiradar\\workspace\\CheckLot\\CheckLot2.xlsx");
			wb.write(op);
			System.out.println("Written Data");
			op.close();
		}catch(Exception e) { 
			e.printStackTrace(); 
		} 
	}
	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException{
		GetData g=new GetData();
		g.convertSellerCode();
		g.writeData();
//test
	}
}
