package main;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class WebScraper {
    private PizzaMenu cheeseBoard;

    public WebScraper() {
        cheeseBoard = new PizzaMenu("CheeseBoard");
    }

    public void scrapeCheeseBoard() {
        final String url = "https://cheeseboardcollective.coop/pizza/";

        try {
            final Document document = Jsoup.connect(url).get();
            Elements body = document.select("div.pizza-list");
            System.out.println(body);

            for (Element element: body.select("article")) {
                final String date = element.select("div.date").select("p").text();
                final String fullPizzaText = element.select("div.menu p").text();
                final String repeatedPizzaText = element.select("div p b i").text();
                final String toppings =  fullPizzaText.replace(repeatedPizzaText + " ", "");

                System.out.println(date);
                System.out.println(toppings);
                cheeseBoard.addPizza(date, toppings);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
