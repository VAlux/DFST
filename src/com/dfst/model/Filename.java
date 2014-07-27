package com.dfst.model;

import com.sun.istack.internal.NotNull;

public class Filename {

    private String name;
    private String extension;

    public Filename(@NotNull String name, @NotNull String extension) {
        this.name = name;
        this.extension = extension;
    }

    public String getName() {
        return name;
    }

    public String getExtension() {
        return extension;
    }

    public String toString() {
        return name + "." + extension;
    }
}
