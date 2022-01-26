package com.tellioglu.landmarkbookjava;

import java.io.Serializable;

public class Lendmark implements Serializable {
    String name;
    String country;
    int id;

    public Lendmark(String name, String country, int id) {
        this.name = name;
        this.country = country;
        this.id = id;
    }
}
