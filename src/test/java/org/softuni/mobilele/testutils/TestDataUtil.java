package org.softuni.mobilele.testutils;

import org.softuni.mobilele.model.entity.*;
import org.softuni.mobilele.model.enums.EngineEnum;
import org.softuni.mobilele.model.enums.TransmissionEnum;
import org.softuni.mobilele.model.enums.UserRoleEnum;
import org.softuni.mobilele.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Component
public class TestDataUtil {

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private BrandRepository brandRepository;


    public void createExchangeRate(String currency, BigDecimal rate) {
        exchangeRateRepository.save(new ExchangeRate().setCurrency(currency).setRate(rate));
    }

    public void cleanUp() {
        exchangeRateRepository.deleteAll();
        offerRepository.deleteAll();
        brandRepository.deleteAll();
    }

    public Offer createTestOffer(UserEntity owner) {

        Brand brand = brandRepository.save(new Brand()
                .setName("Test Brand")
                .setModels(List.of(
                        new Model().setName("Test Model"),
                        new Model().setName("Test Model 1")
                )));

        Offer offer = new Offer()
                .setModel(brand.getModels().get(0))
                .setImageUrl("https://google.com")
                .setPrice(BigDecimal.valueOf(1000))
                .setYear(2020)
                .setUuid(UUID.randomUUID())
                .setDescription("Test Description")
                .setEngine(EngineEnum.GASOLINE)
                .setMileage(10000)
                .setTransmission(TransmissionEnum.MANUAL)
                .setSeller(owner);

        return offerRepository.save(offer);
    }
}
