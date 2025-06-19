package bank.io.controller;

import java.util.List;

import bank.io.model.Product;
import bank.io.model.repository.ProductRepository;

public class ProductController {
	private final ProductRepository repository = new ProductRepository();

	public List<Product> getRecommendations(int age, String gender, String job) {
		return repository.findByUserCondition(age, gender, job);
	}
}
