package com.dfst.logging;

import com.sun.istack.internal.NotNull;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Logger {

    private static final Path logFileFolder = Paths.get(System.getProperty("user.dir") + "/log");
    private static final File logFile = new File(logFileFolder + "/log.txt");
    private static ArrayList<LogEntry> logs = new ArrayList<>();

    public static void log(@NotNull String message) {
        logs.add(new LogEntry(message));
        System.out.println(message);
    }

    public static void save() {
        try {
            if (Files.notExists(logFileFolder)) {
                Files.createDirectory(logFileFolder);
            }
            logFile.createNewFile();
        } catch (IOException e) {
            System.err.println("problem with creating the log file");
        }

        try (PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter(logFile, true)))){
            for (LogEntry entry : logs)
                printer.println(entry.toString());
        } catch (IOException e) {
            System.err.println("problem with saving log file");
        }
    }
}
