package com.javarush.task.task40.task4001;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/* 
POST, а не GET
*/

public class Solution {
    public static void main(String[] args) throws Exception {
        Solution solution = new Solution();
        solution.sendPost(new URL("http://requestb.in/1cse9qt1"), "name=zapp&mood=good&locale=&id=777");
    }

    public void sendPost(URL url, String urlParameters) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");

        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));

        bufferedWriter.write(urlParameters);

        bufferedWriter.flush();

        int responseCode = connection.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String responseLine;
        StringBuilder response = new StringBuilder();

        while ((responseLine = bufferedReader.readLine()) != null) {
            response.append(responseLine);
        }
        bufferedReader.close();

        System.out.println("Response: " + response.toString());
    }
}
