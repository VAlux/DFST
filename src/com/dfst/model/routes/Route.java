package com.dfst.model.routes;

import com.sun.istack.internal.NotNull;

import java.io.Serializable;

public class Route implements Serializable{

    private String extension;
    private String path;

    public Route() {
        extension = "empty";
        path = "empty";
    }

    public Route(@NotNull String extension, @NotNull String path) {
        this.extension = extension;
        this.path = path;
    }

    public String getExtension() {
        return extension;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "ext: " + extension + " path: " + path;
    }
}