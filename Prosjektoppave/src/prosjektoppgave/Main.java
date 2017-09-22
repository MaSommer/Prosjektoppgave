package prosjektoppgave;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
	
	public static void main(String[] args) {
		ArrayList<Integer> selectedSP500 = SelectedStockReader.readFile("S&P500.txt");
		StockReader srSP500 = new StockReader("S&P500.txt", "19.09.2002", "20.09.2017", selectedSP500);
	}

}
