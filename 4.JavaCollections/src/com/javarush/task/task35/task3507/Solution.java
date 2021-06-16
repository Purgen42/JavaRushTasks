package com.javarush.task.task35.task3507;

import java.io.*;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;

/* 
ClassLoader - что это такое?
*/

public class Solution {
    public static void main(String[] args) {
//        Set<? extends Animal> allAnimals = getAllAnimals(Solution.class.getProtectionDomain().getCodeSource().getLocation().getPath() + Solution.class.getPackage().getName().replaceAll("[.]", "/") + "/data");
        Set<? extends Animal> allAnimals = getAllAnimals("C:\\Users\\Пользователь\\Java\\JavaRushTasks\\out\\production\\4.JavaCollections\\com\\javarush\\task\\task35\\task3507\\data");

        System.out.println(allAnimals);
    }

    public static Set<? extends Animal> getAllAnimals(String pathToAnimals) {
//        System.out.println(pathToAnimals);
        Set<Animal> result = new HashSet<>();
        File dir = new File(pathToAnimals);
        MyLoader loader = new MyLoader();
//        System.out.println(dir.exists());
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) continue;
            if (!file.getName().matches(".*\\.class")) continue;
//            System.out.println(file.getName());
            byte[] buffer;
            try {
                buffer = Files.readAllBytes(file.toPath());
//                URL url = dir.toURI().toURL();
//                URL[] urls = new URL[]{url};

//                System.out.println(clazz.getConstructor(null));
                try {
                    Class<? extends Animal> clazz = loader.getClassByBytes(buffer);
                    result.add(clazz.newInstance());
                } catch (InstantiationException | IllegalAccessException e) {
                    continue;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return result;
    }

    static class MyLoader extends ClassLoader {
        public Class getClassByBytes(byte[] bytes) {
            return defineClass(null, bytes, 0, bytes.length);
        }
    }
}
