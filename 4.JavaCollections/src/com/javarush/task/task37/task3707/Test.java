package com.javarush.task.task37.task3707;

import java.io.*;
import java.util.HashSet;

public class Test {
    public static void main(String[] args) {
        AmigoSet<String> amigoSet1 = new AmigoSet<>();
        amigoSet1.add("123");
        amigoSet1.add("abc");
        amigoSet1.add("qwe");
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        AmigoSet<String> amigoSet2 = null;

        try (ObjectOutputStream output = new ObjectOutputStream(byteOut)) {
            output.writeObject(amigoSet1);
            ObjectInputStream input = new ObjectInputStream(new ByteArrayInputStream(byteOut.toByteArray()));
            amigoSet2 = (AmigoSet<String>) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(amigoSet1.size());
        System.out.println(amigoSet2.size());
        HashSet set;
    }
}
