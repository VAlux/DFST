package com.dfst.sorting;

import com.dfst.logging.Logger;
import com.dfst.model.Filename;
import com.dfst.model.routes.Route;
import com.dfst.model.routes.RoutesModel;
import com.dfst.preferences.Preferences;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Stack;

public class Sorter implements Runnable {

    private File sourceFolder;
    private RoutesModel model;
    private Stack<Filename> filenames;
    private final static long timeout = 5000L; // source folder scanning frequency.
    private volatile boolean isSorting;
    private Preferences preferences;

    public Sorter(Preferences preferences) {
        this.preferences = preferences;
        this.sourceFolder = preferences.getSourceFolder();
        model = preferences.getModel();
        isSorting = true;
    }

    private void scanSourceFolder() {
        sourceFolder = preferences.getSourceFolder(); // update src folder. may be changed by user in runtime.
        File[] files = sourceFolder.listFiles();
        String[] nameExt;
        filenames = new Stack<>();

        final byte NAME = 0;
        final byte EXT = 1;

        assert files != null;

        for (File file : files)
            if (!file.isDirectory()) {
                nameExt = file.getName().split("\\.(?=[^\\.]+$)");
                if (nameExt.length == 2 && nameExt[NAME].length() > 0 && nameExt[EXT].length() > 0)
                    filenames.push(new Filename(nameExt[NAME], nameExt[EXT]));
        }
    }

    private void sortFiles() throws IOException {
        if (filenames == null)
            return;

        while (!filenames.isEmpty()) {
            Filename filename = filenames.pop();
            for (Route route : model.getRoutes()) {
                if (route.getExtension().equals(filename.getExtension())) {
                    Path source = sourceFolder.toPath().resolve(filename.toString());
                    Path destination = Paths.get(route.getPath()).resolve(filename.toString());
                    Files.move(source, destination);
                    Logger.log("File [" + filename + "] moved from [" + source + "] to [" + destination + "]");
                }
            }
        }
    }

    @Override
    public void run() {
        while (isSorting) {
            try {
                sortFiles();
                scanSourceFolder();
                Thread.sleep(timeout);
            } catch (InterruptedException | IOException e) {
                if (e instanceof InterruptedException) {
                    setEnabled(false);
                }
            }
        }
    }

    public void setEnabled(boolean isEnabled){
        isSorting = isEnabled;
    }
}