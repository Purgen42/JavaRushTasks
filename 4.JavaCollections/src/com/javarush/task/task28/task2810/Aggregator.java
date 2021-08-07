package com.javarush.task.task28.task2810;

import com.javarush.task.task28.task2810.model.HHStrategy;
import com.javarush.task.task28.task2810.model.HabrCareerStrategy;
import com.javarush.task.task28.task2810.model.Model;
import com.javarush.task.task28.task2810.model.Provider;
import com.javarush.task.task28.task2810.view.HtmlView;
import com.javarush.task.task28.task2810.view.View;

public class Aggregator {
    public static void main(String[] args) {
        HtmlView view = new HtmlView();
//        Provider HHProvider = new Provider(new HHStrategy());
//        Model model = new Model(view, HHProvider);

//        new HabrCareerStrategy().getVacancies("Dnepropetrovsk");

        Provider HHProvider = new Provider(new HHStrategy());
        Provider HabrCareerProvider = new Provider(new HabrCareerStrategy());
        Model model = new Model(view, HHProvider, HabrCareerProvider);
        Controller controller = new Controller(model);
        view.setController(controller);
        view.userCitySelectEmulationMethod();
    }
}
