package prosjektoppgave;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class StockReader {
	
	private Map<Date, Double[]> stockInformation;
	private String fromDate;
	private String toDate;
	private String file;
	private int[] stocksSelected;
	
	
	public StockReader(String file, String fromDate, String toDate, int[] stocksSelected){
		this.file = file;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.stocksSelected = stocksSelected;
		this.stockInformation = new HashMap<Date, Double[]>();
		readFile();
	}
	
	public void readFile() {
		Scanner scanner;
		try {
			scanner = new Scanner(new File("target/S&P500.txt"));
			int row = 0;
			while(scanner.hasNext()){
				String nextLine = scanner.nextLine();
				String[] lineCells = nextLine.split("\t");
				int col = 0;
				for (String cell : lineCells) {
					
					col++;
				}
				System.out.println(nextLine+"");
				row++;
				if (row > 3){
					System.exit(1);
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
