package com.backend.backend;



import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.web.multipart.MultipartFile;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FileDetails implements Serializable{
    MultipartFile image;
    int width;
    int height;

    public FileDetails() {
    }
    

    public FileDetails(MultipartFile image, int width, int height) {
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

    public MultipartFile getImage() {
        return this.image;
    }

    public void setImage(MultipartFile image) {
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