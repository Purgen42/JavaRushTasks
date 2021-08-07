package com.javarush.task.task28.task2810.view;

import com.javarush.task.task28.task2810.Controller;
import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class HtmlView implements View {
    private Controller controller;
    private final String filePath = "./4.JavaCollections/src/" + this.getClass().getPackage().getName().replaceAll("\\.", "/") + "/vacancies.html";

    protected Document getDocument() throws IOException {
        File input = new File(filePath);
        return Jsoup.parse(input, "UTF-8");
    }

    private String getUpdatedFileContent(List<Vacancy> vacancies) {
        Document doc;
        try {
            doc = getDocument();

            Elements templateClassElements = doc.getElementsByClass("template");
            Elements templateElementsCopy = templateClassElements.clone();
            templateElementsCopy.removeAttr("style").removeClass("template");

            Element templateElement = templateElementsCopy.first();

        //        System.out.println(doc.select("tr:not(.vacancy.template)"));
            doc.select("tr.vacancy:not(.template)").remove();
        //        System.out.println(doc);
        //        System.out.println("=====================Vacancies===========================");

            for (Vacancy vacancy : vacancies) {
                Element newElement = templateElement.clone();
                newElement.getElementsByClass("city").first().text(vacancy.getCity());
                newElement.getElementsByClass("companyName").first().text(vacancy.getCompanyName());
                newElement.getElementsByClass("salary").first().text(vacancy.getSalary());
                newElement.getElementsByTag("a").first().attr("href", vacancy.getUrl()).text(vacancy.getTitle());
        //            System.out.println(newElement);
        //            System.out.println("=====================Outer HTML===========================");
        //            System.out.println(templateElement.outerHtml());
                doc.select("tr.vacancy.template").before(newElement.outerHtml());
            }
//            System.out.println(doc);
            return doc.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Some exception occurred";
        }
    }

    private void updateFile(String content) {
        try (FileOutputStream output = new FileOutputStream(filePath)) {
            output.write(content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(List<Vacancy> vacancies) {
        String content = getUpdatedFileContent(vacancies);
        updateFile(content);
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void userCitySelectEmulationMethod() {
        controller.onCitySelect("Odessa");
    }
}
