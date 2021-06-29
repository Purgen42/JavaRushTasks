package com.javarush.task.task33.task3310.strategy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class FileBucket {
    private Path path;

    public FileBucket() {
        try {
            path = Files.createTempFile(null, null);
            Files.deleteIfExists(path);
            Files.createFile(path);
            path.toFile().deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public long getFileSize() {
//        if (path == null) return 0L;
//        if (!Files.exists(path)) return 0L;
//        if (!path.toFile().isFile()) return 0L;
        long size = 0L;
        try {
            size = Files.size(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return size;
    }

    public void putEntry(Entry entry) {
        try {
            OutputStream fileOutput = Files.newOutputStream(path);
            ObjectOutputStream output = new ObjectOutputStream(fileOutput);
            output.writeObject(entry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Entry getEntry() {
        if (getFileSize() == 0L) return null;
        Entry result = null;

        try ( ObjectInputStream input = new ObjectInputStream(Files.newInputStream(path))) {
            result = (Entry) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void remove() {
        try {
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
