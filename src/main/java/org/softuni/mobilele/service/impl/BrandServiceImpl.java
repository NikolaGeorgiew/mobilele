package org.softuni.mobilele.service.impl;

import org.softuni.mobilele.model.dto.BrandDTO;
import org.softuni.mobilele.model.dto.ModelDTO;
import org.softuni.mobilele.model.entity.Model;
import org.softuni.mobilele.repository.BrandRepository;
import org.softuni.mobilele.repository.ModelRepository;
import org.softuni.mobilele.service.BrandService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;

    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }


    @Override
    public List<BrandDTO> getAllBrands() {

//        Map<String, BrandDTO> brands = new TreeMap<>();
//
//        for (Model model : modelRepository.findAll()) {
//            if (!brands.containsKey(model.getBrand().getName())) {
//                brands.put(model.getBrand().getName(),new BrandDTO(model.getBrand().getName(),new ArrayList<>()));
//            }
//            brands.get(model.getBrand().getName()).models().add(new ModelDTO(model.getId(),model.getName()));
//        }
//        return brands.values().stream().toList();


        return brandRepository.findAll().stream()
                .map(brand -> new BrandDTO(brand.getName(),
                        brand.getModels().stream()
                                .map(model -> new ModelDTO(model.getId(),model.getName()))
                                .sorted(Comparator.comparing(ModelDTO::name))
                                .collect(Collectors.toList()))).sorted(Comparator.comparing(BrandDTO::name))
                .collect(Collectors.toList());
    }
}
