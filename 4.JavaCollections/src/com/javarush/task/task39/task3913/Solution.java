package com.javarush.task.task39.task3913;

import java.nio.file.Paths;
import java.util.Date;

public class Solution {
    public static void main(String[] args) {
        LogParser logParser = new LogParser(Paths.get("c:/logs/"));
//        System.out.println(logParser.getNumberOfUniqueIPs(null, null));
//        System.out.println(logParser.getUniqueIPs(null, null));
//        System.out.println(logParser.getIPsForUser("Amigo" ,null, null));
//        System.out.println(logParser.getIPsForEvent(Event.LOGIN,null, null));
//        System.out.println(logParser.getIPsForStatus(Status.OK,null, null));

//        System.out.println(logParser.getLoggedUsers(null, null));
//        System.out.println(logParser.getDownloadedPluginUsers(null, null));

        System.out.println(logParser.execute("get ip for event = \"WRITE_MESSAGE\""));


    }
}