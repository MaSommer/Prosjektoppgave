package prosjektoppgave;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class SelectedStockReader {
	
	public static ArrayList<Integer> readFile(String file){
		ArrayList<Integer> selectedStocks = new ArrayList<Integer>();
		Scanner scanner;
		try {
			scanner = new Scanner(new File("target/selected" + file));
			while(scanner.hasNext()){
				String nextSelection = scanner.nextLine();
				selectedStocks.add(Integer.parseInt(nextSelection));
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return selectedStocks;
	}
	
	

}
