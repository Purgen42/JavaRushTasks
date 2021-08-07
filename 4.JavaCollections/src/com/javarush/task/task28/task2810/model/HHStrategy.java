package com.javarush.task.task28.task2810.model;

import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HHStrategy implements Strategy {
    private static final String URL_FORMAT = "https://hh.ru/search/vacancy?text=java+%s&page=%d";

    @Override
    public List<Vacancy> getVacancies(String searchString) {
        URI uri = null;
        try {
            uri = new URI(String.format(URL_FORMAT, searchString, 0));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String siteName = uri.getHost();
        List<Vacancy> result = new ArrayList<>();
        int pageNum = 0;

        while (true) {

            Document doc = null;
            try {
                doc = getDocument(searchString, pageNum++);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Elements elements = doc.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy");
            if (elements.size() == 0) break;

            for (Element e : elements) {
                Elements titleElements = e.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-title");
                String title = titleElements.size() > 0 ? titleElements.first().ownText() : "";
                Elements salaryElements = e.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-compensation");
                String salary = salaryElements.size() > 0 ? salaryElements.first().ownText() : "";
                Elements cityElements = e.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-address");
                String city = cityElements.size() > 0 ? cityElements.first().ownText() : "";
                Elements companyElements = e.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-employer");
                String companyName = companyElements.size() > 0 ? companyElements.first().ownText() : "";

                String url = titleElements.size() > 0 ? titleElements.first().attributes().get("href") : "";

                result.add(new Vacancy(title, salary, city, companyName, siteName, url));
            }
//            System.out.println(result.size());
        }
        return result;
    }

    protected Document getDocument(String searchString, int page) throws IOException {
        String url = String.format(URL_FORMAT, searchString, page);

//        Document doc = Jsoup.connect("https://javarush.ru/testdata/big28data.html")
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.107 Safari/537.36")
                .referrer("https://astrakhan.hh.ru/")
                .get();
//        System.out.println(doc);

//        if (page > 0 && doc.baseUri().equals("https://javarush.ru/testdata/big28data.html")) return new Document(url);
        return doc;
    }
}
