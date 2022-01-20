package com.backend.backend;

import java.io.File;

public class FileDetails {
    File image;
    int width;
    int height;

    public FileDetails() {
    }
    

    public FileDetails(File image, int width, int height) {
        this.image = image;
        this.width = width;
        this.height = height;
    }
    
        @Override
        public String toString() {
            return "{" +
                " image='" + getImage() + "'" +
                ", width='" + getWidth() + "'" +
                ", height='" + getHeight() + "'" +
                "}";
        }

    public File getImage() {
        return this.image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}