package com.dfst.sorting.folders;

import java.util.ArrayList;

public class FolderExtAssociation {

    private String foldername;
    private ArrayList<String> extensions; // associated with foldername example: (png, jpg -> "images")

    public FolderExtAssociation() {
        extensions = new ArrayList<>();
    }

    public FolderExtAssociation(String foldername, ArrayList<String> extensions) {
        this.foldername = foldername;
        this.extensions = extensions;
    }

    public String getFoldername() {
        return foldername;
    }

    public ArrayList<String> getExtensions() {
        return extensions;
    }
}
