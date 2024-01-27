package com.example.demo.model;

import java.util.Hashtable;

public interface Advertisement {
    // Maximális megjelenési szám.
    public int getMaxAppearance();
    // Megjelenési súly.
    public double getWeight();
    // Utolsó n napon a megjelenések szám.
    public int lastAppearence(int dayIndex, int numberOfDays);
    // Reklám megjelennítése az adott napra
    public void showAdvertisement(int dayIndex);
    // A reklám összes megjelenése.
// Hashtable<nap index, napi megjelenések száma>
    public Hashtable<Integer,Integer> getAllAppearances();

    public String getTitle();
}