package prosjektoppgave;

import java.util.Date;
import java.util.Map;

public class Stock {
	
	private String name;
	private Map<Date, Double> closingPrice;
	private Map<Date, Double> openingPrice;
	private Map<Date, Double> highPrice;
	private Map<Date, Double> lowPrice;
	private Map<Date, Double> marketCap;
	private Map<Date, Double> volume;
	
	public Stock(String name, Map<Date, Double> closingPrice, Map<Date, Double> openingPrice, Map<Date, Double> highPrice, Map<Date, Double> lowPrice, Map<Date, Double> marketCap, Map<Date, Double> volume){
		this.name = name;
		this.closingPrice = closingPrice;
		this.openingPrice = openingPrice;
		this.lowPrice = lowPrice;
		this.marketCap = marketCap;
		this.volume = volume;
	}

	public String getName() {
		return name;
	}

	public Map<Date, Double> getClosingPrice() {
		return closingPrice;
	}

	public Map<Date, Double> getOpeningPrice() {
		return openingPrice;
	}

	public Map<Date, Double> getHighPrice() {
		return highPrice;
	}

	public Map<Date, Double> getLowPrice() {
		return lowPrice;
	}

	public Map<Date, Double> getMarketCap() {
		return marketCap;
	}

	public Map<Date, Double> getVolume() {
		return volume;
	}
	
	

}
