package com.example.demo.service;


import com.example.demo.model.Advertisement;
import com.example.demo.model.AdvertisementImpl;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class AdvertisementSystemImplTest {

    @Test
    void initiatingNewAdvertisementSystem_whenAddingAdvertisement_ProperListWhereGivenBack() {
        AdvertisementSystemImpl advertisementSystem = new AdvertisementSystemImpl(3);
        Advertisement advertisement = new AdvertisementImpl(10, 0.5, "Advertisement");
        advertisementSystem.registerAdvertisement(advertisement);

        assertEquals(1, advertisementSystem.getAdvertisementList().size());
        assertEquals(advertisement.getTitle(), advertisementSystem.getAdvertisementList().get(0).getTitle());

        Advertisement advertisement2 = new AdvertisementImpl(8, 0.1, "Advertisement2");
        advertisementSystem.registerAdvertisement(advertisement2);

        assertEquals(2, advertisementSystem.getAdvertisementList().size());
        assertEquals(advertisement2.getTitle(), advertisementSystem.getAdvertisementList().get(1).getTitle());
    }

    @Test
    void addedFiveAdvertisement_whenFiveShowAsked_AllAdvertisementIsShowed() {
        AdvertisementSystem advertisementSystem = getDefaultAdvertisementSystem();
        IntStream.range(0, 5).forEach(dayIndex -> advertisementSystem.showNextAdvertisement(1));
        long advertisementShowedOnce = advertisementSystem.getAdvertisementList().stream()
                .map(adv -> adv.lastAppearence(1, 1))
                .filter(lastAppearence -> lastAppearence == 1)
                .count();
        assertEquals(5, advertisementShowedOnce);
    }

    @Test
    void addedFiveAdvertisement_afterAllAreAlreadyShowed_nextShowedInProperOrder() {
        AdvertisementSystem advertisementSystem = getDefaultAdvertisementSystem();
        IntStream.range(0, 5).forEach(dayIndex -> advertisementSystem.showNextAdvertisement(1));
        long advertisementShowedOnce = advertisementSystem.getAdvertisementList().stream()
                .map(adv -> adv.lastAppearence(1, 1))
                .filter(lastAppearance -> lastAppearance == 1)
                .count();
        assertEquals(5, advertisementShowedOnce);

        Advertisement advertisement = findAdvertisement("Advertisement1", advertisementSystem);
        assertEquals(1, advertisement.lastAppearence(1, 1));
        boolean result = advertisementSystem.showNextAdvertisement(1);
        assertTrue(result);
        assertEquals(2, advertisement.lastAppearence(1, 1));

        advertisement = findAdvertisement("Advertisement2", advertisementSystem);
        assertEquals(1, advertisement.lastAppearence(1, 1));
        result = advertisementSystem.showNextAdvertisement(1);
        assertTrue(result);
        assertEquals(2, advertisement.lastAppearence(1, 1));

        advertisement = findAdvertisement("Advertisement3", advertisementSystem);
        assertEquals(1, advertisement.lastAppearence(1, 1));
        result = advertisementSystem.showNextAdvertisement(1);
        assertTrue(result);
        assertEquals(2, advertisement.lastAppearence(1, 1));

        advertisement = findAdvertisement("Advertisement1", advertisementSystem);
        assertEquals(2, advertisement.lastAppearence(1, 1));
        result = advertisementSystem.showNextAdvertisement(1);
        assertTrue(result);
        assertEquals(3, advertisement.lastAppearence(1, 1));

        advertisement = findAdvertisement("Advertisement4", advertisementSystem);
        assertEquals(1, advertisement.lastAppearence(1, 1));
        result = advertisementSystem.showNextAdvertisement(1);
        assertTrue(result);
        assertEquals(2, advertisement.lastAppearence(1, 1));

        advertisement = findAdvertisement("Advertisement2", advertisementSystem);
        assertEquals(2, advertisement.lastAppearence(1, 1));
        result = advertisementSystem.showNextAdvertisement(1);
        assertTrue(result);
        assertEquals(3, advertisement.lastAppearence(1, 1));

        advertisement = findAdvertisement("Advertisement1", advertisementSystem);
        assertEquals(3, advertisement.lastAppearence(1, 1));
        result = advertisementSystem.showNextAdvertisement(1);
        assertTrue(result);
        assertEquals(4, advertisement.lastAppearence(1, 1));

        advertisement = findAdvertisement("Advertisement3", advertisementSystem);
        assertEquals(2, advertisement.lastAppearence(1, 1));
        result = advertisementSystem.showNextAdvertisement(1);
        assertTrue(result);
        assertEquals(3, advertisement.lastAppearence(1, 1));

        advertisement = findAdvertisement("Advertisement2", advertisementSystem);
        assertEquals(3, advertisement.lastAppearence(1, 1));
        result = advertisementSystem.showNextAdvertisement(1);
        assertTrue(result);
        assertEquals(4, advertisement.lastAppearence(1, 1));

        advertisement = findAdvertisement("Advertisement1", advertisementSystem);
        assertEquals(4, advertisement.lastAppearence(1, 1));
        result = advertisementSystem.showNextAdvertisement(1);
        assertTrue(result);
        assertEquals(5, advertisement.lastAppearence(1, 1));

        advertisement = findAdvertisement("Advertisement5", advertisementSystem);
        assertEquals(1, advertisement.lastAppearence(1, 1));
        result = advertisementSystem.showNextAdvertisement(1);
        assertTrue(result);
        assertEquals(2, advertisement.lastAppearence(1, 1));
    }

    @Test
    void addedThreeAdvertisementWithlimitedMaxAppearance_afterAllAreAlreadyShowed_oneWillBlock() {
        AdvertisementSystem advertisementSystem = getAdvertisementSystemWithLimitedMaxApperance();
        IntStream.range(0, 3).forEach(dayIndex -> advertisementSystem.showNextAdvertisement(1));
        long advertisementShowedOnce = advertisementSystem.getAdvertisementList().stream()
                .map(adv -> adv.lastAppearence(1, 1))
                .filter(lastAppearance -> lastAppearance == 1)
                .count();
        assertEquals(3, advertisementShowedOnce);

        Advertisement advertisement = findAdvertisement("Advertisement1", advertisementSystem);
        assertEquals(1, advertisement.lastAppearence(1, 1));
        boolean result = advertisementSystem.showNextAdvertisement(1);
        assertTrue(result);
        assertEquals(2, advertisement.lastAppearence(1, 1));

        advertisement = findAdvertisement("Advertisement2", advertisementSystem);
        assertEquals(1, advertisement.lastAppearence(1, 1));
        result = advertisementSystem.showNextAdvertisement(1);
        assertFalse(result);
        assertEquals(1, advertisement.lastAppearence(1, 1));
    }

    @Test
    void addedThreeAdvertisement_afterDaysPassed_showWillContinueFromBeginning() {
        AdvertisementSystem advertisementSystem = getDefaultAdvertisementSystemWithThreeAdvertisement();
        IntStream.range(0, 3).forEach(dayIndex -> advertisementSystem.showNextAdvertisement(1));
        long advertisementShowedOnce = advertisementSystem.getAdvertisementList().stream()
                .map(adv -> adv.lastAppearence(1, 1))
                .filter(lastAppearance -> lastAppearance == 1)
                .count();
        assertEquals(3, advertisementShowedOnce);

        long advertisementShowedOnceThreeDaysLater = advertisementSystem.getAdvertisementList().stream()
                .map(adv -> adv.lastAppearence(3, 3))
                .filter(lastAppearance -> lastAppearance == 1)
                .count();
        assertEquals(3, advertisementShowedOnceThreeDaysLater);

        long advertisementShowedOnceFourDaysLater = advertisementSystem.getAdvertisementList().stream()
                .map(adv -> adv.lastAppearence(4, 3))
                .filter(lastAppearance -> lastAppearance == 1)
                .count();
        assertEquals(0, advertisementShowedOnceFourDaysLater);

        Advertisement advertisement = findAdvertisement("Advertisement3", advertisementSystem);
        assertEquals(0, advertisement.lastAppearence(4, 3));
        boolean result = advertisementSystem.showNextAdvertisement(4);
        assertTrue(result);
        assertEquals(1, advertisement.lastAppearence(4, 3));
    }

    private Advertisement findAdvertisement(String advertisement, AdvertisementSystem advertisementSystem) {
        return advertisementSystem.getAdvertisementList().stream()
              .filter(adv -> adv.getTitle().equals(advertisement))
              .findFirst()
              .orElse(null);
    }

    private AdvertisementSystem getDefaultAdvertisementSystem() {
        AdvertisementSystemImpl advertisementSystem = new AdvertisementSystemImpl(3);
        Advertisement advertisement1 = new AdvertisementImpl(10, 0.5, "Advertisement1");
        Advertisement advertisement2 = new AdvertisementImpl(10, 0.4, "Advertisement2");
        Advertisement advertisement3 = new AdvertisementImpl(10, 0.3, "Advertisement3");
        Advertisement advertisement4 = new AdvertisementImpl(10, 0.2, "Advertisement4");
        Advertisement advertisement5 = new AdvertisementImpl(10, 0.1, "Advertisement5");
        advertisementSystem.registerAdvertisement(advertisement1);
        advertisementSystem.registerAdvertisement(advertisement2);
        advertisementSystem.registerAdvertisement(advertisement3);
        advertisementSystem.registerAdvertisement(advertisement4);
        advertisementSystem.registerAdvertisement(advertisement5);
        return advertisementSystem;
    }

    private AdvertisementSystem getDefaultAdvertisementSystemWithThreeAdvertisement() {
        AdvertisementSystemImpl advertisementSystem = new AdvertisementSystemImpl(3);
        Advertisement advertisement1 = new AdvertisementImpl(10, 0.5, "Advertisement1");
        Advertisement advertisement2 = new AdvertisementImpl(10, 0.4, "Advertisement2");
        Advertisement advertisement3 = new AdvertisementImpl(10, 0.3, "Advertisement3");
        advertisementSystem.registerAdvertisement(advertisement1);
        advertisementSystem.registerAdvertisement(advertisement2);
        advertisementSystem.registerAdvertisement(advertisement3);
        return advertisementSystem;
    }

    private AdvertisementSystem getAdvertisementSystemWithLimitedMaxApperance() {
        AdvertisementSystemImpl advertisementSystem = new AdvertisementSystemImpl(3);
        Advertisement advertisement1 = new AdvertisementImpl(2, 0.3, "Advertisement1");
        Advertisement advertisement2 = new AdvertisementImpl(1, 0.2, "Advertisement2");
        Advertisement advertisement3 = new AdvertisementImpl(2, 0.1, "Advertisement3");
        advertisementSystem.registerAdvertisement(advertisement1);
        advertisementSystem.registerAdvertisement(advertisement2);
        advertisementSystem.registerAdvertisement(advertisement3);
        return advertisementSystem;
    }
}