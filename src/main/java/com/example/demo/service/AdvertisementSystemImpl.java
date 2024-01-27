package com.example.demo.service;


import com.example.demo.model.Advertisement;
import com.example.demo.service.util.AdvertisementStatistic;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class AdvertisementSystemImpl implements AdvertisementSystem {

    private static final Logger logger = Logger.getLogger(AdvertisementSystemImpl.class.getName());
    public AdvertisementSystemImpl(int advertisePeriodLength) {
        if (advertisePeriodLength < 1) {
            throw new IllegalArgumentException("Period length must be higher than zero");
        }
        this.advertisePeriodLength = advertisePeriodLength;
    }

    private final int advertisePeriodLength;

    private final List<Advertisement> advertisementList = new ArrayList<>();
    @Override
    public void registerAdvertisement(Advertisement ad) {
        advertisementList.add(ad);
    }

    @Override
    public boolean showNextAdvertisement(int dayIndex) {
        logger.info("Searching new advertisement for show");
        if (advertisementList.isEmpty()) {
            logger.info("No advertisement found");
            return false;
        }

        // Creating a wrapper class for Advertisement with apperance statistics
        List<AdvertisementStatistic> advertisementStatistics = advertisementList.stream()
                .map(adv -> new AdvertisementStatistic(adv, adv.lastAppearence(dayIndex, advertisePeriodLength)))
                .sorted()
                .collect(Collectors.toList());

        // When un-shown advertisement found, show it
        for (AdvertisementStatistic advSt : advertisementStatistics) {
            if (advSt.getAppearance() == 0) {
                logger.info("Not shown advertisement found " + advSt.getAdvertisement().getTitle());
                advSt.getAdvertisement().showAdvertisement(dayIndex);
                return true;
            }
        }

        // Take the advertisement with lowest weight as reference
        AdvertisementStatistic firstAdvertisementStatistic = advertisementStatistics.get(0);
        firstAdvertisementStatistic.setPlanAppearance(firstAdvertisementStatistic.getAppearance());

        if (advertisementStatistics.size() == 1) {
            // When we have only one advertisement with free capacity, show it
            if (firstAdvertisementStatistic.hasAdvertisementFreeCapacity()) {
                logger.info("Single advertisement we have will be shown " + firstAdvertisementStatistic.getAdvertisement().getTitle());
                firstAdvertisementStatistic.getAdvertisement().showAdvertisement(dayIndex);
                return true;
            }
        } else {
            // Fill up the remaining stats with the plan figures
            for (int i = 1; i < advertisementStatistics.size(); i++) {
                int multiplicationBasedWeight = (int) Math.round(advertisementStatistics.get(i).getWeight() / firstAdvertisementStatistic.getWeight());
                advertisementStatistics.get(i).setPlanAppearance(firstAdvertisementStatistic.getPlanAppearance() * multiplicationBasedWeight);
            }

            // Find the first advertisement with the lowest percentage of plan
            AdvertisementStatistic advertisementMostLaggingBehind = getAdvertisementMostLaggingBehind(advertisementStatistics);
            // When it has free capacity, show it
            if (advertisementMostLaggingBehind.hasAdvertisementFreeCapacity()) {
                logger.info("Found next advertisement to show " + advertisementMostLaggingBehind.getAdvertisement().getTitle());
                advertisementMostLaggingBehind.getAdvertisement().showAdvertisement(dayIndex);
                return true;
            }
        }
        return false;
    }

    private static AdvertisementStatistic getAdvertisementMostLaggingBehind(List<AdvertisementStatistic> advertisementStatistics) {
        AdvertisementStatistic advertisementMostLaggingBehind = advertisementStatistics.get(0);
        for (int i = 1; i < advertisementStatistics.size(); i++) {
            if (advertisementStatistics.get(i).getPercentageOfPlan() < advertisementMostLaggingBehind.getPercentageOfPlan()) {
                advertisementMostLaggingBehind = advertisementStatistics.get(i);
            }
        }
        return advertisementMostLaggingBehind;
    }

    @Override
    public List<Advertisement> getAdvertisementList() {
        return this.advertisementList;
    }
}
