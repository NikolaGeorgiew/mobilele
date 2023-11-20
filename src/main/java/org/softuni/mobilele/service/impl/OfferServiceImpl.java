package org.softuni.mobilele.service.impl;

import jakarta.transaction.Transactional;
import org.softuni.mobilele.model.dto.CreateOfferDTO;
import org.softuni.mobilele.model.dto.OfferDetailDTO;
import org.softuni.mobilele.model.dto.OfferSummaryDTO;
import org.softuni.mobilele.model.entity.Model;
import org.softuni.mobilele.model.entity.Offer;
import org.softuni.mobilele.model.entity.UserEntity;
import org.softuni.mobilele.repository.ModelRepository;
import org.softuni.mobilele.repository.OfferRepository;
import org.softuni.mobilele.repository.UserRepository;
import org.softuni.mobilele.service.MonitoringService;
import org.softuni.mobilele.service.OfferService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final ModelRepository modelRepository;

    private final MonitoringService monitoringService;
    private final UserRepository userRepository;

    public OfferServiceImpl(
            OfferRepository offerRepository,
            ModelRepository modelRepository,
            MonitoringService monitoringService,
            UserRepository userRepository) {

        this.offerRepository = offerRepository;
        this.modelRepository = modelRepository;
        this.monitoringService = monitoringService;
        this.userRepository = userRepository;
    }
    @Override
    public UUID createOffer(CreateOfferDTO createOfferDTO, UserDetails seller) {

        Offer newOffer = map(createOfferDTO);

        Model model = modelRepository.findById(createOfferDTO.modelId()).orElseThrow(() ->
                new IllegalArgumentException("Model with id " + createOfferDTO.modelId() + " not found!"));

        UserEntity sellerEntity = userRepository.findByEmail(seller.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User with email " + seller.getUsername() + " not found !"));

        newOffer.setModel(model);
        newOffer.setSeller(sellerEntity);

         newOffer = offerRepository.save(newOffer);

         return newOffer.getUuid();
    }

    @Override
    public Page<OfferSummaryDTO> getAllOffers(Pageable pageable) {
        monitoringService.logOfferSearch();

        return offerRepository
                .findAll(pageable)
                .map(OfferServiceImpl::mapAsSummary);
    }

    @Override
    public Optional<OfferDetailDTO> getOfferDetail(UUID offerUUID) {
        return offerRepository
                .findByUuid(offerUUID)
                .map(OfferServiceImpl::mapAsDetails);
    }

    @Override
    @Transactional
    public void deleteOffer(UUID offerUUID) {
        offerRepository.deleteByUuid(offerUUID);
    }

    private static OfferDetailDTO mapAsDetails(Offer offer) {
        //TODO reuse
        return new OfferDetailDTO(
                offer.getUuid().toString(),
                offer.getModel().getBrand().getName(),
                offer.getModel().getName(),
                offer.getYear(),
                offer.getMileage(),
                offer.getPrice(),
                offer.getEngine(),
                offer.getTransmission(),
                offer.getImageUrl()
        );
    }

    private static OfferSummaryDTO mapAsSummary(Offer offer) {
        return new OfferSummaryDTO(
                offer.getUuid().toString(),
                offer.getModel().getBrand().getName(),
                offer.getModel().getName(),
                offer.getYear(),
                offer.getMileage(),
                offer.getPrice(),
                offer.getEngine(),
                offer.getTransmission(),
                offer.getImageUrl()
        );
    }

    //Creating Offer
    private Offer map(CreateOfferDTO createOfferDTO) {

        return new Offer()
                .setUuid(UUID.randomUUID())
                .setDescription(createOfferDTO.description())
                .setEngine(createOfferDTO.engine())
                .setTransmission(createOfferDTO.transmission())
                .setImageUrl(createOfferDTO.imageUrl())
                .setMileage(createOfferDTO.mileage())
                .setPrice(BigDecimal.valueOf(createOfferDTO.price()))
                .setYear(createOfferDTO.year());
    }
}
