package com.dfst.preferences;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PreferencesLoader {

    private final static Path preferencesFileFolder = Paths.get(System.getProperty("user.dir") + "/data");
    private final static File preferencesFile = new File(preferencesFileFolder + "/prefs.pfs");

    public static void save(Preferences container) throws IOException {
        checkFolderExists();
        preferencesFile.createNewFile();
        FileOutputStream fileStream = new FileOutputStream(preferencesFile);
        ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
        objectStream.writeObject(container);
        objectStream.close();
    }

    public static Preferences load() throws IOException, ClassNotFoundException {
        checkFolderExists();
        if (preferencesFile.createNewFile())
            return new Preferences();

        FileInputStream fileStream = new FileInputStream(preferencesFile);
        ObjectInputStream objectStream = new ObjectInputStream(fileStream);
        Preferences container = (Preferences)objectStream.readObject();
        objectStream.close();
        return container;
    }

    private static void checkFolderExists() throws IOException {
        if(Files.notExists(preferencesFileFolder))
            Files.createDirectory(preferencesFileFolder);
    }
}