package main;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class WebScraper {
    private PizzaMenu cheeseBoard;
    private PizzaMenu sliverTelegraph;
    private WebDriver driver;

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

        System.setProperty("webdriver.chrome.driver", "Resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get(url);

        final WebElement telegraph = driver.findElement(By.id("block-yui_3_17_2_1_1551316212180_29122"));
        for (WebElement element: telegraph.findElements(By.cssSelector("div[class*=\"summary-item-has-excerpt\"]"))) {
            if (element.getText().length() != 0) {
                final String date = element.findElement(By.cssSelector("span.summary-thumbnail-event-date-month")).getText()
                        + " " + element.findElement(By.cssSelector("span.summary-thumbnail-event-date-day")).getText();

                List<WebElement> text = element.findElements(By.cssSelector("div.summary-excerpt p"));
                final String toppings = text.get(2).getText();

                System.out.println(date);
                System.out.println(toppings);
                System.out.println("----------");
                sliverTelegraph.addPizza(date, toppings);
            }
        }

        telegraph.findElement(By.cssSelector("span[class=\"summary-carousel-pager-next next\"]")).click();
        final WebElement telegraphClicked = driver.findElement(By.id("block-yui_3_17_2_1_1551316212180_29122"));
        for (WebElement element: telegraph.findElements(By.cssSelector("div[class*=\"summary-item-has-excerpt\"]"))) {
            if (element.getText().length() != 0) {
                final String date = element.findElement(By.cssSelector("span.summary-thumbnail-event-date-month")).getText()
                        + " " + element.findElement(By.cssSelector("span.summary-thumbnail-event-date-day")).getText();

                List<WebElement> text = element.findElements(By.cssSelector("div.summary-excerpt p"));
                final String toppings = text.get(2).getText();

                System.out.println(date);
                System.out.println(toppings);
                System.out.println("----------");
                sliverTelegraph.addPizza(date, toppings);
            }
        }
    }
}
