package com.example.demo.service.util;

import com.example.demo.model.Advertisement;

import java.util.Objects;

public class AdvertisementStatistic implements Comparable<AdvertisementStatistic>{

    public AdvertisementStatistic(Advertisement advertisement, int appearance) {
        this.advertisement = advertisement;
        this.appearance = appearance;
    }

    private Advertisement advertisement;

    private int appearance;

    private int planAppearance;

    private double percentageOfPlan;

    public Advertisement getAdvertisement() {
        return advertisement;
    }

    public double getWeight() {
        return advertisement.getWeight();
    }

    public int getAppearance() {
        return appearance;
    }


    public int getPlanAppearance() {
        return planAppearance;
    }

    public double getPercentageOfPlan() {
        return percentageOfPlan;
    }

    public void setPlanAppearance(int planAppearance) {
        this.planAppearance = planAppearance;
        if (planAppearance != 0) {
            this.percentageOfPlan = (double) this.appearance / this.planAppearance;
        }
    }

    public boolean hasAdvertisementFreeCapacity() {
        return this.appearance < this.advertisement.getMaxAppearance();
    }

    @Override
    public int compareTo(AdvertisementStatistic advertisementStatistic) {
        return Double.compare(this.advertisement.getWeight(), advertisementStatistic.advertisement.getWeight());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdvertisementStatistic that = (AdvertisementStatistic) o;
        return Objects.equals(advertisement, that.advertisement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(advertisement);
    }
}
