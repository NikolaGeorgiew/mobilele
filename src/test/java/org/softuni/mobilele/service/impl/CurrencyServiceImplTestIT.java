package org.softuni.mobilele.service.impl;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.softuni.mobilele.model.dto.ExchangeRatesDTO;
import org.softuni.mobilele.model.entity.ExchangeRate;
import org.softuni.mobilele.repository.ExchangeRateRepository;
import org.softuni.mobilele.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;


@SpringBootTest
class CurrencyServiceImplTestIT {

   @Autowired
    private CurrencyService currencyServiceToTest;

   @Autowired
   private ExchangeRateRepository exchangeRateRepository;

   @BeforeEach
   void setUp() {
       exchangeRateRepository.deleteAll();
   }

   @AfterEach
   void tearDown() {
       exchangeRateRepository.deleteAll();
   }
   @ParameterizedTest(name = "Conversion BGN/USD exRate {0}, expected {1}")
   @MethodSource("test_Data_BGN_to_USD")
   void test_BGN_to_USD(Double exchangeRate, Double expectedValue) {
       // "base": "USD",
       // "rates": {
       //   "BGN": 1.840515,
       //   "EUR": 0.937668
       //  }

       //exp 0.54
       var textExRate =
               new ExchangeRatesDTO("USD", Map.of("BGN", BigDecimal.valueOf(exchangeRate)));

       currencyServiceToTest.refreshRates(textExRate);

       Optional<ExchangeRate> exRateUSD_BGN = exchangeRateRepository.findById("USD");

       Assertions.assertTrue(exRateUSD_BGN.isPresent());
       Assertions.assertEquals(
               BigDecimal.valueOf(expectedValue).setScale(2, RoundingMode.DOWN)
               ,exRateUSD_BGN.map(ExchangeRate::getRate).orElseThrow());
   }

    @ParameterizedTest(name = "Conversion BGN/EUR exRateBGN {0}, exRateEUR {1}, expected {2}")
    @MethodSource("test_Data_BGN_to_EUR")
    void test_BGN_to_EUR(Double exchangeRateBGN,
                         Double expectedRateEUR,
                         Double expectedValue) {
        // "base": "USD",
        // "rates": {
        //   "BGN": 1.840515,
        //   "EUR": 0.937668
        //  }

        //exp 0.54
        var textExRate =
                new ExchangeRatesDTO("USD"
                        , Map.of("BGN", BigDecimal.valueOf(exchangeRateBGN),
                                "EUR",BigDecimal.valueOf(expectedRateEUR)));

        currencyServiceToTest.refreshRates(textExRate);

        Optional<ExchangeRate> exRateUSD_BGN = exchangeRateRepository.findById("EUR");

        Assertions.assertTrue(exRateUSD_BGN.isPresent());
        Assertions.assertEquals(
                BigDecimal.valueOf(expectedValue).setScale(2, RoundingMode.DOWN)
                ,exRateUSD_BGN.map(ExchangeRate::getRate).orElseThrow());
    }
    private static Stream<Arguments> test_Data_BGN_to_EUR() {
        return Stream.of(
                Arguments.of(1.840515, 0.937668, 0.51),
                Arguments.of(1.777515, 0.544454, 0.31),
                Arguments.of(1.0, 1.0, 1.0)
        );
    }

   private static Stream<Arguments> test_Data_BGN_to_USD() {
       return Stream.of(
               Arguments.of(1.840515, 0.54),
               Arguments.of(1.752345, 0.57),
               Arguments.of(1.333333, 0.75),
               Arguments.of(1.0, 1.0)
       );
   }
}