package bank.io.model;

public class Product {
	public String name, type;
	public double interestRate;
	public int periodMonths;

	public Product(String name, String type, double interestRate, int periodMonths) {
		this.name = name;
		this.type = type;
		this.interestRate = interestRate;
		this.periodMonths = periodMonths;
	}

	@Override
	public String toString() {
		return name + " (" + interestRate + "%, " + periodMonths + "개월)";
	}
}
