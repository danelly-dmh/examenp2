package com.example.danelly.exa_platii_prac_2;

public class Archivo {
    int userId;
    String path;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Archivo(int userId, String path) {

        this.userId = userId;
        this.path = path;
    }

    public Archivo() {

    }
}
