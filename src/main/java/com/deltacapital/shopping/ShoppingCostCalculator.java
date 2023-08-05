package com.deltacapital.shopping;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ShoppingCostCalculator {

	private final List<Stock> inventory = new ArrayList<>();

	@Getter
	private  List<Item> itemList = new ArrayList<>();

	public ShoppingCostCalculator(List<Stock> inventory){
		this.inventory.addAll(inventory);
	}

	//Calculates total price of all items in the basket
	BigDecimal calculateBasketPrice(Stream<String> myBasket){
		BigDecimal totalPrice = BigDecimal.ZERO;
		makeItemList(myBasket);
		for (Item item:itemList) {
			calculateAfterSaleItemPrice(item);
			totalPrice = totalPrice.add(item.getTotalCost());
		}
		return totalPrice;
	}

	//Creates list of items with the count for each
	 void makeItemList(Stream<String> myBasket){
		//HashMap to store name of item as key and count as value
		Map<String,Long> itemMap = myBasket.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		itemMap.entrySet().forEach(m->itemList.add(new Item(m.getKey(),m.getValue())));
	}

	//Calculates price of item.getQuantity number of  item.getName
	 void calculateAfterSaleItemPrice(Item item){
		for(Stock stock:inventory) {
			if (stock.getName().equalsIgnoreCase(item.getName())) {
				item.setUnitCost(stock.getUnitCost());
				item.setTotalCost(item.getUnitCost().multiply(BigDecimal.valueOf(stock.getDiscount().apply(item.getQuantity()))));
				break;
			}
		}
	}

}
