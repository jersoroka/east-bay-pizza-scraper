package main;


import java.util.HashMap;
import java.util.Map;

public class PizzaMenu {
    private String restaurant;
    private Map<String, String> pizzas;

    public PizzaMenu(String name) {
        this.restaurant = name;
        pizzas = new HashMap<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a pizza to pizzas
    public void addPizza(String date, String ingredients) {
        pizzas.put(date, ingredients);
    }

    @Override
    public String toString() {
        return "PizzaMenu{" +
                "restaurant='" + restaurant + '\'' +
                ", pizzas=" + pizzas +
                '}';
    }
}
