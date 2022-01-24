package com.backend.backend;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table
public class FileDetails{

    @Id
    String id;

    @Lob //for blob and clob
    @Type(type = "org.hibernate.type.BinaryType")
    byte[] image;
    int width;
    int height;

    public FileDetails() {
    }
    

    public FileDetails(byte[] image, int width, int height) {
        this.image = image;
        this.width = width;
        this.height = height;
    }
    public FileDetails(String id,byte[] image, int width, int height) {
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

    public byte[] getImage() {
        return this.image;
    }

    public void setImage(byte[] image) {
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