package com.example.demo.service;

import com.example.demo.model.Advertisement;
import com.example.demo.model.AdvertisementImpl;
import com.example.demo.service.util.AdvertisementStatistic;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class AdvertisementManager {

    private static final Logger logger = Logger.getLogger(AdvertisementManager.class.getName());
    public AdvertisementManager() {
        this.period = 3;
        this.advertisementSystem = new AdvertisementSystemImpl(period);
        Advertisement advertisement1 = new AdvertisementImpl(5, 0.1, "Advertisement 1");
        Advertisement advertisement2 = new AdvertisementImpl(5, 0.2, "Advertisement 2");
        Advertisement advertisement3 = new AdvertisementImpl(5, 0.3, "Advertisement 3");
        registerNewAdvertisement(advertisement1);
        registerNewAdvertisement(advertisement2);
        registerNewAdvertisement(advertisement3);
    }

    private AdvertisementSystem getDefaultAdvertisementSystem(int period) {
        AdvertisementSystem advSystem = new AdvertisementSystemImpl(period);
        Advertisement advertisement1 = new AdvertisementImpl(5, 0.1, "Advertisement 1");
        Advertisement advertisement2 = new AdvertisementImpl(5, 0.2, "Advertisement 2");
        Advertisement advertisement3 = new AdvertisementImpl(5, 0.3, "Advertisement 3");
        advSystem.registerAdvertisement(advertisement1);
        advSystem.registerAdvertisement(advertisement2);
        advSystem.registerAdvertisement(advertisement3);
        return advSystem;
    }

    private AdvertisementSystem advertisementSystem;

    private int day = 1;

    private final int period;

    public int getPeriod() {
        return period;
    }

    public void stepDay() {
        day++;
        logger.info(String.format("New day is %d", day));
    }

    public void askNewAdvertisement() {
        logger.info(String.format("Asking new advertisement for day  %d", day));
        advertisementSystem.showNextAdvertisement(day);
    }

    public void registerNewAdvertisement(Advertisement advertisement) {
        logger.info(String.format("Registering new advertisement with name %s", advertisement.getTitle()));
        advertisementSystem.registerAdvertisement(advertisement);
    }

    public AdvertisementSystem getAdvertisementSystem() {
        return advertisementSystem;
    }

    public List<AdvertisementStatistic> getStatistics() {
        return advertisementSystem.getAdvertisementList()
                .stream()
                .map(adv -> new AdvertisementStatistic(adv, adv.lastAppearence(this.day, this.period)))
                .toList();
    }

    public void setToDefault() {
        this.day = 1;
        this.advertisementSystem = getDefaultAdvertisementSystem(period);
    }

    public void setToDefaultWithoutAdvertisement() {
        this.day = 1;
        this.advertisementSystem = new AdvertisementSystemImpl(period);
    }


    public int getDay() {
        return day;
    }
}
