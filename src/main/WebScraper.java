package main;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class WebScraper {
    private PizzaMenu cheeseBoard;
    private PizzaMenu sliverTelegraph;

    public WebScraper() {
        cheeseBoard = new PizzaMenu("CheeseBoard");
        sliverTelegraph = new PizzaMenu("Sliver Telegraph");
    }

    // MODIFIES: this
    // EFFECTS: scrapes the weekly pizza menu for the Cheese Board Collective
    public void scrapeCheeseBoard() {
        final String url = "https://cheeseboardcollective.coop/pizza/";

        try {
            final Document document = Jsoup.connect(url).get();
            Elements body = document.select("div.pizza-list");
            //System.out.println(body);

            for (Element element: body.select("article")) {
                final String date = element.select("div.date").select("p").text();
                final String fullPizzaText = element.select("div.menu p").text();
                final String repeatedPizzaText = element.select("div p b i").text();
                final String toppings =  fullPizzaText.replace(repeatedPizzaText + " ", "");

                System.out.println(date);
                System.out.println(toppings);
                System.out.println("----------");
                cheeseBoard.addPizza(date, toppings);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // MODIFIES: this
    // EFFECTS: scrapes the weekly pizza menu for Sliver Pizzeria at the Telegraph and Shattuck locations
    public void scrapeSliver() {
        final String url = "https://www.sliverpizzeria.com/menu-weekly";

        try {
            final Document document = Jsoup.connect(url).get();
            Elements telegraph = document.select("div#block-yui_3_17_2_1_1551316212180_29122");
            for (Element element: telegraph.select("div[class*=\"summary-item-has-excerpt\"]")) {
                final String date = element.select("span.summary-thumbnail-event-date-month").text() + " " +
                        element.select("span.summary-thumbnail-event-date-day").text();

                String unfilteredToppings = element.select("div.summary-excerpt p").text();
                String preToppingsInformation = element.select("div.summary-excerpt p strong").text();
                final String toppings = unfilteredToppings.replace(preToppingsInformation + " ", "");
                System.out.println(date);
                System.out.println(toppings);
                System.out.println("----------");
                sliverTelegraph.addPizza(date, toppings);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
