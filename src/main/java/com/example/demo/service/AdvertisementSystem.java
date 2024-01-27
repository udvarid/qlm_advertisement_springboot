package com.example.demo.service;


import com.example.demo.model.Advertisement;

import java.util.List;

public interface AdvertisementSystem {
    // Reklám regisztrálása.
    public void registerAdvertisement(Advertisement ad);
    // Következı reklám megjelenítése a megadott napon.
    public boolean showNextAdvertisement(int dayIndex);
    // Reklámok listája
    public List<Advertisement> getAdvertisementList();
}