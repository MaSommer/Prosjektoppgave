package prosjektoppgave;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class StockReader {
	
	private Map<String, Double[][]> stockInformation;
	
	
	public StockReader(){
		this.stockInformation = new HashMap<String, Double[][]>();
		readFile();
	}
	
	public void readFile() {
		Scanner scanner;
		try {
			scanner = new Scanner(new File("target/S&P500.csv"));
			scanner.useDelimiter(",");
			int c = 0;
			while(scanner.hasNext()){
				String nextCell = scanner.next();
				
				System.out.print(nextCell+" | ");
				c++;
				if (nextCell.equals("endrow") || c > 2){
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
