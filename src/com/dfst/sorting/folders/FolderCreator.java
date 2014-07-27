package com.dfst.sorting.folders;


import com.dfst.preferences.Preferences;
import com.sun.istack.internal.NotNull;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;

public class FolderCreator {

    private Path root;
    Preferences preferences;

    public FolderCreator(@NotNull Path root, Preferences preferences) {
        this.root = root;
        this.preferences = preferences;
    }

    private void scanSourceFolder() {
        final File sourceFolder = new File(root.toUri());
        File[] files = sourceFolder.listFiles();
        String[] nameExt;
        ArrayList<String> extensions = new ArrayList<>();
        ArrayList<String> folders = new ArrayList<>();

        final byte NAME = 0;
        final byte EXT = 1;

        assert files != null;

        for (File file : files) {
            if (!file.isDirectory()) {
                nameExt = file.getName().split("\\.(?=[^\\.]+$)");
                if (nameExt.length == 2 && nameExt[NAME].length() > 0 && nameExt[EXT].length() > 0)
                    extensions.add(nameExt[EXT]);
            } else {
                folders.add(file.getName());
            }
        }
    }
}
