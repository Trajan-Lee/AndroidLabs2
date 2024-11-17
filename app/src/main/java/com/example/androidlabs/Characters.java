package com.example.androidlabs;

public class Characters {
    private String name;
    private String height;
    private String mass;

    public Characters(String name, String height, String mass){
        this.name = name;
        this.height = height;
        this.mass = mass;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getMass() {
        return mass;
    }

    public void setMass(String mass) {
        this.mass = mass;
    }
}