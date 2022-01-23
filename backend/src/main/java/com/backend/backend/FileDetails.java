package com.backend.backend;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.File;

import javax.persistence.Entity;


import javax.persistence.Id;
import javax.persistence.Table;



@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table
public class FileDetails{

    @Id
    String id;
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
    public FileDetails(String id,File image, int width, int height) {
        this.id = id;
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


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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