package prosjektoppgave;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class StockReader {
	
	//index in the following arraylists are day number and value are the value for the different stocks
	private ArrayList<Double[]> dayToOpenPrice; //index 0
	private ArrayList<Double[]> dayToClosingPrice; //index 1
	private ArrayList<Double[]> dayToTurnoverVolume; //index 2
	private ArrayList<Double[]> dayToHighPrice; //index 3
	private ArrayList<Double[]> dayToLowPrice; //index 4
	private ArrayList<String[]> dayToExDivDay; //index 5
	private ArrayList<Double[]> dayToMarketCap; //index 6
	private Date fromDate;
	private Date toDate;
	private String file;
	private int numberOfSelected;
	private ArrayList<Integer> selectedStocks;
	private Map<String, Integer> monthMap;
	
	private final int OPEN_PRICE = 0;
	private final int CLOSING_PRICE = 1;
	private final int TURNOVER_VOLUME = 2;
	private final int HIGH_PRICE= 3;
	private final int LOW_PRICE = 4;
	private final int EX_DIV_DAY = 5;
	private final int MARKET_CAP = 6;
	
	
	public StockReader(String file, String fromDate, String toDate, ArrayList<Integer> selectedStocks){
		instansiateMonthMap();
		this.file = file;
		this.fromDate = createDateFromString(fromDate);
		this.toDate = createDateFromString(toDate);
		this.selectedStocks = selectedStocks;
		this.numberOfSelected = countNumberOfSelected(selectedStocks);
		dayToOpenPrice = new ArrayList<Double[]>();
		dayToClosingPrice = new ArrayList<Double[]>();
		dayToTurnoverVolume = new ArrayList<Double[]>();
		dayToHighPrice = new ArrayList<Double[]>();
		dayToLowPrice = new ArrayList<Double[]>();
		dayToExDivDay = new ArrayList<String[]>();
		dayToMarketCap = new ArrayList<Double[]>();
		readFile();
	}
	
	public void readFile() {
		Scanner scanner;
		long start = System.currentTimeMillis();
		try {
			scanner = new Scanner(new File("target/" + file));
			int row = 0;
			while(scanner.hasNext()){
				String nextLine = scanner.nextLine();
				String[] lineCells = nextLine.split("\t");
				if (row != 0 && checkIfDateIsWithinInterval(lineCells[0])){
					addAllColData(lineCells, row);
				}
//				System.out.println(nextLine+"");
				row++;
				
				if (row%50 == 0){
					long end = System.currentTimeMillis();
					double duration = (double)(end-start)/Math.pow(10, 3);
					System.out.println("Row: " + row + " after " + duration + "sec");			
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
	
	private Double[] generateStockDataForDay(String[] lineCells, int row, int dataToSelect){
		//start with -1 because we skip the date
		int col = -1;
		int numberOfColDataAdded = 0;
		Double[] colData = new Double[numberOfSelected*Parameters.NUMBER_OF_DIFFERENT_STOCK_DATA];
		for (String cell : lineCells) {
			if (row == 0){
				break;
			}
			if (col != -1 && checkIfSelected(col) && checkIfCorrectDataIsSelected(dataToSelect, col)){
				double dataPoint = getDataPoint(cell);
				colData[numberOfColDataAdded] = dataPoint;
				numberOfColDataAdded++;
			}
			col++;
		}
		return colData;
	}
	
	private String[] generateExDivDayForDay(String[] lineCells, int row, int dataToSelect){
		//start with -1 because we skip the date
		int col = -1;
		int numberOfColDataAdded = 0;
		String[] colData = new String[numberOfSelected*Parameters.NUMBER_OF_DIFFERENT_STOCK_DATA];
		for (String cell : lineCells) {
			if (row == 0){
				break;
			}
			if (col != -1 && checkIfSelected(col) && checkIfCorrectDataIsSelected(dataToSelect, col) && checkIfDate(cell)){
				colData[numberOfColDataAdded] =  cell;
				numberOfColDataAdded++;
			}
			col++;
		}
		return colData;
	}
	
	private void addAllColData(String[] lineCells, int row){
		for (int dataToSelect = 0; dataToSelect < Parameters.NUMBER_OF_DIFFERENT_STOCK_DATA; dataToSelect++) {
			if (dataToSelect == EX_DIV_DAY){
				dayToExDivDay.add(generateExDivDayForDay(lineCells, row, dataToSelect));
			}
			else{
				addColDataToMap(generateStockDataForDay(lineCells, row, dataToSelect), dataToSelect);				
			}
		}
	}
	
	private void addColDataToMap(Double[] colData, int dataToSelect){
		if (dataToSelect == OPEN_PRICE){
			dayToOpenPrice.add(colData);
		}
		else if (dataToSelect == CLOSING_PRICE){
			dayToClosingPrice.add(colData);
		}
		else if (dataToSelect == TURNOVER_VOLUME){
			dayToTurnoverVolume.add(colData);
		}
		else if (dataToSelect == HIGH_PRICE){
			dayToHighPrice.add(colData);
		}
		else if (dataToSelect == LOW_PRICE){
			dayToLowPrice.add(colData);
		}
		else if (dataToSelect == MARKET_CAP){
			dayToMarketCap.add(colData);
		}
	}
	
	private boolean checkIfCorrectDataIsSelected(int dataToSelect, int col){
		if (col % Parameters.NUMBER_OF_DIFFERENT_STOCK_DATA == dataToSelect){
			return true;
		}
		else{
			return false;
		}
	}
	
	//return -1 if cell is NA, $VALUE or anything bullshit
	private double getDataPoint(String cell){
		if (!cell.equals("") && Character.isDigit(cell.charAt(0))){
			return convertCommaToDotAndParseDouble(cell);
		}
		else{
			return -1.0;
		}
	}
	
	private double convertCommaToDotAndParseDouble(String input){
		String output = "";
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) == ','){
				output += ".";
			}
			else{
				output += input.charAt(i);
			}
		}
		return Double.parseDouble(output);
	}
	
	private boolean checkIfSelected(int col){
		if (selectedStocks.get(col%Parameters.NUMBER_OF_DIFFERENT_STOCK_DATA) == 0){
			return false;
		}
		else{
			return true;
		}
	}
	
	private int countNumberOfSelected(ArrayList<Integer> selectedStocks){
		int count = 0;
		for (Integer integer : selectedStocks) {
			if (integer == 1){
				count++;
			}
		}
		return count;
	}
	
	private boolean checkIfDate(String input){
		if (input.length() == 10 && numberOfDots(input) == 2){
			return true;
		}
		else{
			return false;
		}
	}
	
	private int numberOfDots(String input){
		int count = 0;
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) == '.'){
				count++;
			}
		}
		return count;
	}
	
	private boolean checkIfDateIsWithinInterval(String dateString){
		Date date = createDateFromString(dateString);
		if (fromDate.before(date) && toDate.after(date)){
			return true;
		}
		else{
			return false;
		}
	}
	
	private Date createDateFromString(String dateString){
		String year = dateString.substring(6, 10);
		String month = dateString.substring(3, 5);
		String day = dateString.substring(0, 2);
		
		return new GregorianCalendar(Integer.parseInt(year), monthMap.get(month), Integer.parseInt(day)).getTime();
	}
	
	private void instansiateMonthMap(){
		this.monthMap = new HashMap<String, Integer>();
		monthMap.put("01", Calendar.JANUARY);
		monthMap.put("02", Calendar.FEBRUARY);
		monthMap.put("03", Calendar.MARCH);
		monthMap.put("04", Calendar.APRIL);
		monthMap.put("05", Calendar.MAY);
		monthMap.put("06", Calendar.JUNE);
		monthMap.put("07", Calendar.JULY);
		monthMap.put("08", Calendar.AUGUST);
		monthMap.put("09", Calendar.SEPTEMBER);
		monthMap.put("10", Calendar.OCTOBER);
		monthMap.put("11", Calendar.NOVEMBER);
		monthMap.put("12", Calendar.DECEMBER);
	}
	
	
	

}
