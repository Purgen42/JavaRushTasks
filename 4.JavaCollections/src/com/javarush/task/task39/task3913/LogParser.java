package com.javarush.task.task39.task3913;

import com.javarush.task.task39.task3913.query.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogParser implements IPQuery, UserQuery, DateQuery, EventQuery, QLQuery {
    class Record {
        String ip;
        String username;
        Date date;
        Event event;
        Integer taskNum;
        Status status;

        public Record(String ip, String username, Date date, Event event, Integer taskNum, Status status) {
            this.ip = ip;
            this.username = username;
            this.date = date;
            this.event = event;
            this.taskNum = taskNum;
            this.status = status;
        }

        @Override
        public String toString() {
            return "Record{" +
                    "ip='" + ip + '\'' +
                    ", username='" + username + '\'' +
                    ", date=" + date +
                    ", event=" + event +
                    ", taskNum=" + taskNum +
                    ", status=" + status +
                    '}';
        }
    }

    private ArrayList<Record> parseResult;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    private List<String> readLines(Path path) {
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<String>();
    }

    public LogParser(Path logDir) {
        parseResult = new ArrayList<>();
        List<String> lines = new ArrayList<>();
        try (Stream<Path> dirStream = Files.walk(logDir)) {
            lines = dirStream
                    .filter(Files::isRegularFile)
                    .filter(p -> p.getFileName().toString().endsWith(".log"))
                    .flatMap(p -> readLines(p).stream())
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }



        for (String line : lines) {
//            System.out.println(line);
            String[] fields = line.split("\t");
            String ip = fields[0];
            String username = fields[1];
            Date date = null;
            try {
                date = dateFormat.parse(fields[2]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String eventString = fields[3];
            Event event;
            Integer taskNum = null;
            if (eventString.contains(" ")) {
                String[] eventTask = eventString.split(" ");
                event = Event.valueOf(eventTask[0]);
                taskNum = Integer.valueOf(eventTask[1]);
            }
            else {
                event = Event.valueOf(fields[3]);
            }
            Status status;
            status = Status.valueOf(fields[4]);

            parseResult.add(new Record(ip, username, date, event, taskNum, status));
//            System.out.println(parseResult.get(parseResult.size() - 1));

        }
    }

    private boolean isBetween(Date date, Date after, Date before) {
        return (after == null || date.after(after) || date.equals(after)) &&
                (before == null || date.before(before) || date.equals(before));
    }

    private List<Record> getDateRange(Date after, Date before) {
        List<Record> result = new ArrayList<>();
        result = parseResult.stream()
                .filter(r -> isBetween(r.date, after, before))
                .collect(Collectors.toList());
        return result;
    }

    @Override
    public int getNumberOfUniqueIPs(Date after, Date before) {
        Set<String> ips = getUniqueIPs(after, before);
        return ips.size();
    }

    @Override
    public Set<String> getUniqueIPs(Date after, Date before) {
        Set<String> result = new HashSet<>();
        result = getDateRange(after, before).stream()
                .map(r -> r.ip)
                .collect(Collectors.toSet());
        return result;
    }

    @Override
    public Set<String> getIPsForUser(String user, Date after, Date before) {
        Set<String> result = new HashSet<>();
        result = getDateRange(after, before).stream()
                .filter(r -> r.username.equals(user))
                .map(r -> r.ip)
                .collect(Collectors.toSet());
        return result;
    }

    @Override
    public Set<String> getIPsForEvent(Event event, Date after, Date before) {
        Set<String> result = new HashSet<>();
        result = getDateRange(after, before).stream()
                .filter(r -> r.event.equals(event))
                .map(r -> r.ip)
                .collect(Collectors.toSet());
        return result;
    }

    @Override
    public Set<String> getIPsForStatus(Status status, Date after, Date before) {
        Set<String> result = new HashSet<>();
        result = getDateRange(after, before).stream()
                .filter(r -> r.status.equals(status))
                .map(r -> r.ip)
                .collect(Collectors.toSet());
        return result;
    }

    private Set<String> getUsers(Date after, Date before) {
        return getDateRange(after, before).stream()
                .map(r -> r.username)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getAllUsers() {
        return getUsers(null, null);
    }

    @Override
    public int getNumberOfUsers(Date after, Date before) {
        return getUsers(after, before).size();
    }

    @Override
    public int getNumberOfUserEvents(String user, Date after, Date before) {
        return (int) getDateRange(after, before).stream()
                .filter(r -> r.username.equals(user))
                .map(r -> r.event)
                .distinct()
                .count();
    }

    @Override
    public Set<String> getUsersForIP(String ip, Date after, Date before) {
        return getDateRange(after, before).stream()
                .filter(r -> r.ip.equals(ip))
                .map(r -> r.username)
                .collect(Collectors.toSet());
    }

    private Set<String> getUsersForEvent(Event event, Integer task, Date after, Date before) {
        return getDateRange(after, before).stream()
                .filter(r -> r.event == event)
                .filter(r -> task == null || task.equals(r.taskNum))
                .map(r -> r.username)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getLoggedUsers(Date after, Date before) {
        return getUsersForEvent(Event.LOGIN, null, after, before);
    }

    @Override
    public Set<String> getDownloadedPluginUsers(Date after, Date before) {
        return getUsersForEvent(Event.DOWNLOAD_PLUGIN, null, after, before);
    }

    @Override
    public Set<String> getWroteMessageUsers(Date after, Date before) {
        return getUsersForEvent(Event.WRITE_MESSAGE, null, after, before);
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before) {
        return getUsersForEvent(Event.SOLVE_TASK, null, after, before);
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before, int task) {
        return getUsersForEvent(Event.SOLVE_TASK, task, after, before);
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before) {
        return getUsersForEvent(Event.DONE_TASK, null, after, before);
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before, int task) {
        return getUsersForEvent(Event.DONE_TASK, task, after, before);
    }

    private Set<Date> getDatesForUserAndEventAndTask(String user, Event event, Integer task, Date after, Date before) {
        return getDateRange(after, before).stream()
                .filter(r -> user.equals(r.username))
                .filter(r -> r.event == event)
                .filter(r -> task == null || r.taskNum.equals(task))
                .map(r -> r.date)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Date> getDatesForUserAndEvent(String user, Event event, Date after, Date before) {
        return getDatesForUserAndEventAndTask(user, event, null, after, before);
    }

    private Set<Date> getDatesForStatus(Status status, Date after, Date before) {
        return getDateRange(after, before).stream()
                .filter(r -> r.status == status)
                .map(r -> r.date)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Date> getDatesWhenSomethingFailed(Date after, Date before) {
        return getDatesForStatus(Status.FAILED, after, before);
    }

    @Override
    public Set<Date> getDatesWhenErrorHappened(Date after, Date before) {
        return getDatesForStatus(Status.ERROR, after, before);
    }

    @Override
    public Date getDateWhenUserLoggedFirstTime(String user, Date after, Date before) {
        Set<Date> result = getDatesForUserAndEvent(user, Event.LOGIN, after, before);
        if (result.size() == 0) return null;
        return Collections.min(result);
    }

    @Override
    public Date getDateWhenUserSolvedTask(String user, int task, Date after, Date before) {
        Set<Date> result = getDatesForUserAndEventAndTask(user, Event.SOLVE_TASK, task, after, before);
        if (result.size() == 0) return null;
        return Collections.min(result);
    }

    @Override
    public Date getDateWhenUserDoneTask(String user, int task, Date after, Date before) {
        Set<Date> result = getDatesForUserAndEventAndTask(user, Event.DONE_TASK, task, after, before);
        if (result.size() == 0) return null;
        return Collections.min(result);
    }

    @Override
    public Set<Date> getDatesWhenUserWroteMessage(String user, Date after, Date before) {
        return getDatesForUserAndEvent(user, Event.WRITE_MESSAGE, after, before);
    }

    @Override
    public Set<Date> getDatesWhenUserDownloadedPlugin(String user, Date after, Date before) {
        return getDatesForUserAndEvent(user, Event.DOWNLOAD_PLUGIN, after, before);
    }

    @Override
    public int getNumberOfAllEvents(Date after, Date before) {
        return getAllEvents(after, before).size();
    }

    private Set<Event> getEventsForUserAndIP(String user, String ip, Date after, Date before) {
        return getDateRange(after, before).stream()
                .filter(r -> user == null || user.equals(r.username))
                .filter(r -> ip == null || ip.equals(r.ip))
                .map(r -> r.event)
                .collect(Collectors.toSet());

    }

    @Override
    public Set<Event> getAllEvents(Date after, Date before) {
        return getEventsForUserAndIP(null, null, after, before);
    }

    @Override
    public Set<Event> getEventsForIP(String ip, Date after, Date before) {
        return getEventsForUserAndIP(null, ip, after, before);
    }

    @Override
    public Set<Event> getEventsForUser(String user, Date after, Date before) {
        return getEventsForUserAndIP(user, null, after, before);
    }

    private Set<Event> getEventsForStatus(Status status, Date after, Date before) {
        return getDateRange(after, before).stream()
                .filter(r -> status == null || status.equals(r.status))
                .map(r -> r.event)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Event> getFailedEvents(Date after, Date before) {
        return getEventsForStatus(Status.FAILED, after, before);
    }

    @Override
    public Set<Event> getErrorEvents(Date after, Date before) {
        return getEventsForStatus(Status.ERROR, after, before);
    }

    private int getNumberOfAllAttempts(int task, Event event, Date after, Date before) {
        return (int)getDateRange(after, before).stream()
                .filter(r -> r.taskNum == task)
                .filter(r -> r.event == event)
                .count();
    }

    @Override
    public int getNumberOfAttemptToSolveTask(int task, Date after, Date before) {
        Integer result = getAllSolvedTasksAndTheirNumber(after, before).get(task);
        return result == null ? 0 : result;
    }

    @Override
    public int getNumberOfSuccessfulAttemptToSolveTask(int task, Date after, Date before) {
        Integer result = getAllDoneTasksAndTheirNumber(after, before).get(task);
        return result == null ? 0 : result;
    }

    private Map<Integer, Integer> getTasksAndTheirNumberForEvent(Event event, Date after, Date before) {
        return getDateRange(after, before).stream()
                .filter(r -> r.event == event)
                .collect(Collectors.groupingBy(r -> r.taskNum, Collectors.collectingAndThen(Collectors.counting(), Long::intValue)));
    }

    @Override
    public Map<Integer, Integer> getAllSolvedTasksAndTheirNumber(Date after, Date before) {
        return getTasksAndTheirNumberForEvent(Event.SOLVE_TASK, after, before);
    }

    @Override
    public Map<Integer, Integer> getAllDoneTasksAndTheirNumber(Date after, Date before) {
        return getTasksAndTheirNumberForEvent(Event.DONE_TASK, after, before);
    }

    @Override
    public Set<Object> execute(String query) {
        Pattern pattern = Pattern.compile("get\\s+(ip|user|date|event|status)" +
                "(\\s+for\\s+(ip|user|date|event|status)\\s*=\\s*\\\"(.*?)\\\"" +
                "\\s*(and\\s+date\\s+between\\s*\\\"(.*?)\\\"\\s*and\\s*\\\"(.*?)\\\")?)?", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(query.trim());
        if (!matcher.find()) return null;
        Function<Record, Object> mapFunction;
        Predicate<Record> filterFunction;
        Stream<Record> sourceStream = Stream.empty();

        if (matcher.group(5) == null) {
            sourceStream = parseResult.stream();
        }
        else {
            sourceStream = parseResult.stream()
                    .filter(r -> {
                        try {
                            return r.date.after(dateFormat.parse(matcher.group(6))) && r.date.before(dateFormat.parse(matcher.group(7)));
                        } catch (ParseException e) {
                            return false;
                        }
                    });
        }

        String param = matcher.group(1).toLowerCase();

        switch (param) {
            case "ip":
                mapFunction = r -> r.ip;
                break;
            case "user":
                mapFunction = r -> r.username;
                break;
            case "date":
                mapFunction = r -> r.date;
                break;
            case "event":
                mapFunction = r -> r.event;
                break;
            case "status":
                mapFunction = r -> r.status;
                break;
            default:
                return null;
        }
        if (matcher.group(3) == null) return sourceStream
                .map(mapFunction)
                .collect(Collectors.toSet());

        String condition = matcher.group(3).toLowerCase();
        String value = matcher.group(4);

        switch (condition) {
            case "ip":
                filterFunction = r -> r.ip.equals(value);
                break;
            case "user":
                filterFunction = r -> r.username.equals(value);
                break;
            case "date":
                filterFunction = r -> {
                    try {
                        return r.date.equals(dateFormat.parse(value));
                    } catch (ParseException e) {
                        return false;
                    }
                };
                break;
            case "event":
                filterFunction = r -> r.event == Event.valueOf(value);
                break;
            case "status":
                filterFunction = r -> r.status == Status.valueOf(value);
                break;
            default:
                return null;
        }

        return sourceStream
                .filter(filterFunction)
                .map(mapFunction)
                .collect(Collectors.toSet());

    }
}