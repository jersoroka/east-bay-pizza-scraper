package main;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class WebScraper {

    public static void scrapeCheeseBoard() {
        final String url = "https://cheeseboardcollective.coop/pizza/";

        try {
            final Document document = Jsoup.connect(url).get();
            Elements body = document.select("div.page-center-content");

            for (Element element: body.select("article")) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
