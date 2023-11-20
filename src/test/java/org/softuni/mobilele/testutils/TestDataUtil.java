package org.softuni.mobilele.testutils;

import org.softuni.mobilele.model.entity.ExchangeRate;
import org.softuni.mobilele.model.entity.Offer;
import org.softuni.mobilele.model.entity.UserEntity;
import org.softuni.mobilele.model.enums.UserRoleEnum;
import org.softuni.mobilele.repository.ExchangeRateRepository;
import org.softuni.mobilele.repository.OfferRepository;
import org.softuni.mobilele.repository.UserRepository;
import org.softuni.mobilele.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class TestDataUtil {

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    public void createExchangeRate(String currency, BigDecimal rate) {
        exchangeRateRepository.save(new ExchangeRate().setCurrency(currency).setRate(rate));
    }
    public void cleanAllTestData() {
        exchangeRateRepository.deleteAll();
    }
//TODO:
//    public String  createTestOffer(UserEntity owner) {
//
//    }
}
