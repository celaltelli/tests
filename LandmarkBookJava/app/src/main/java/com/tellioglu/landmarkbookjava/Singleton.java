package com.tellioglu.landmarkbookjava;

public class Singleton {
    private Lendmark sentLendmark;
    private Singleton() {
    }
    public Lendmark getSentLendmark() {
        return sentLendmark;
    }

    public void setSentLendmark(Lendmark sentLendmark) {
        this.sentLendmark = sentLendmark;
    }

    public static Singleton getInstance() {
        if(singleton ==null)
            singleton = new Singleton();
        return singleton;
    }




   private static  Singleton singleton;


}
