package com.deltacapital.shopping;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;


class ShoppingCostCalculatorTest {

	private static ShoppingCostCalculator sut;

	@BeforeAll
	static void setUp() {
		List<Stock> inventory = List.of(new Stock("Apple", BigDecimal.valueOf(0.35),q->q),
				new Stock("Banana",BigDecimal.valueOf(0.20),q->q),
				new Stock("Melon",BigDecimal.valueOf(0.50),q ->q/2 + q%2),
				new Stock("Lime",BigDecimal.valueOf(0.15),q -> (q/3)*2 + q%3)
		);
		sut = new ShoppingCostCalculator(inventory);
	}

	@AfterAll
	static void tearDown() {
		sut.getItemList().clear();
	}
	@ParameterizedTest
	@CsvSource({"Apple,5", "Banana,1"})
	void testCorrectCountOfItems(String itemName, Long expectedCount) {
		Stream<String> myBasket = Stream.of("Apple","Banana","Apple","Apple","Apple","Apple");
		sut.makeItemList(myBasket);
		for(Item item:sut.getItemList()){
			if (item.getName().equalsIgnoreCase(itemName))
					assertEquals(expectedCount,item.getQuantity());
		}
		sut.getItemList().clear();
	}

	@ParameterizedTest
	@CsvSource({"Apple,5,1.75", "Banana,1,0.20","Melon,3,1.00","Lime,3,0.30"})
	void testCorrectAfterSaleItemCost(String itemName, Long quantity,BigDecimal expectedAfterSaleCost) {
		Item item = new Item(itemName,quantity);
		sut.calculateAfterSaleItemPrice(item);
		assertEquals(0,item.getTotalCost().compareTo(expectedAfterSaleCost));
	}

	@Test
	void calculateBasketPrice() {
		Stream<String> basket = Stream.of("Apple","Banana","Apple","Melon","Melon","Lime","Melon","Lime","Lime");
		assertEquals(0,sut.calculateBasketPrice(basket).compareTo(BigDecimal.valueOf(2.20)));
	}






}
