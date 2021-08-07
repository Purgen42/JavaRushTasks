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

public class HabrCareerStrategy implements Strategy {
    private static final String URL_FORMAT = "https://career.habr.com/vacancies?q=java+%s&page=%d";



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


            Elements elements = doc.getElementsByClass("job");
            if (elements.size() == 0) break;

            for (Element e : elements) {
                Elements titleElements = e.getElementsByClass("title");
                String title = titleElements.size() > 0 ? titleElements.first().text() : "";
                Elements salaryElements = e.getElementsByAttributeValue("title", "зарплата");
                String salary = salaryElements.size() > 0 ? salaryElements.first().text() : "";
                Elements cityElements = e.getElementsByClass("location");
                String city = cityElements.size() > 0 ? cityElements.first().text() : "";
                Elements companyElements = e.getElementsByClass("company_name");
                String companyName = companyElements.size() > 0 ? companyElements.first().text() : "";

                String url = titleElements.size() > 0 ? "https://career.habr.com" + titleElements.first().getElementsByTag("a").first().attributes().get("href") : "";

                result.add(new Vacancy(title, salary, city, companyName, siteName, url));

//                System.out.println(result.get(result.size() - 1));
            }
//            break;
        }

        return result;
    }

    protected Document getDocument(String searchString, int page) throws IOException {
        String url = String.format(URL_FORMAT, searchString, page);
//        String url = "https://javarush.ru/testdata/big28data2.html";

        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.107 Safari/537.36")
                .referrer("https://astrakhan.hh.ru/")
                .get();
//        System.out.println(doc);

        return doc;
    }

}
