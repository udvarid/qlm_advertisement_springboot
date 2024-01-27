package com.example.demo.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;


class AdvertisementImplTest {

    @Test
    void initiatingAdvertisementObject_withInvalidParameters_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new AdvertisementImpl(-1, 0.5, "Advertisement"));
        assertThrows(IllegalArgumentException.class, () -> new AdvertisementImpl(5, 1.2, "Advertisement"));
        assertThrows(IllegalArgumentException.class, () -> new AdvertisementImpl(5, -0.2, "Advertisement"));
    }

    @Test
    void lastAppearance_validInput_returnsCorrectValue() {
        Advertisement advertisement = new AdvertisementImpl(10, 0.5, "Advertisement");
        advertisement.showAdvertisement(2);
        advertisement.showAdvertisement(2);
        advertisement.showAdvertisement(3);
        advertisement.showAdvertisement(4);
        advertisement.showAdvertisement(5);
        advertisement.showAdvertisement(5);

        int dayIndex = 5;
        int numberOfDays = 3;

        int result = advertisement.lastAppearence(dayIndex, numberOfDays);

        Assertions.assertEquals(4, result);
    }

    @Test
    void lastAppearance_validInput2_returnsCorrectValue() {
        Advertisement advertisement = new AdvertisementImpl(10, 0.5, "Advertisement");
        advertisement.showAdvertisement(2);
        advertisement.showAdvertisement(2);
        advertisement.showAdvertisement(3);
        advertisement.showAdvertisement(4);
        advertisement.showAdvertisement(5);
        advertisement.showAdvertisement(5);

        int dayIndex = 7;
        int numberOfDays = 3;

        int result = advertisement.lastAppearence(dayIndex, numberOfDays);

        Assertions.assertEquals(2, result);
    }

    @Test
    void showAdvertisement_invalidInput_throwsIllegalArgumentException() {
        Advertisement advertisement = new AdvertisementImpl(10, 0.5, "Advertisement");
        assertThrows(IllegalArgumentException.class, () -> advertisement.showAdvertisement(0));
        assertThrows(IllegalArgumentException.class, () -> advertisement.showAdvertisement(-1));
    }

    @Test
    void lastAppearence_invalidParams_throwsIllegalArgumentException() {
        Advertisement advertisement = new AdvertisementImpl(10, 0.5, "Advertisement");

        assertThrows(IllegalArgumentException.class, () -> advertisement.lastAppearence(-1, 3));
        assertThrows(IllegalArgumentException.class, () -> advertisement.lastAppearence(5, -1));
    }

}