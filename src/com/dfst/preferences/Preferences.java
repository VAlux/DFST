package com.dfst.preferences;

import com.dfst.model.routes.RoutesModel;

import java.io.File;
import java.io.Serializable;

public class Preferences implements Serializable {

    private File sourceFolder;
    private RoutesModel model;

    public Preferences() {
        sourceFolder = new File(System.getProperty("user.dir"));
        model = new RoutesModel();
    }

    public Preferences(String sourceFolderPath, RoutesModel model) {
        this.sourceFolder = new File(sourceFolderPath);
        this.model = model;
    }

    public void setSourceFolder(String sourceFolder) {
        this.sourceFolder = new File(sourceFolder);
    }

    public void setModel(RoutesModel model) {
        this.model = model;
    }

    public File getSourceFolder() {
        return sourceFolder;
    }

    public RoutesModel getModel() {
        return model;
    }
}
