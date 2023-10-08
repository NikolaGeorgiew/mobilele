package org.softuni.mobilele.service.impl;

import org.softuni.mobilele.model.dto.BrandDTO;
import org.softuni.mobilele.model.dto.ModelDTO;
import org.softuni.mobilele.model.entity.Model;
import org.softuni.mobilele.repository.ModelRepository;
import org.softuni.mobilele.service.BrandService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BrandServiceImpl implements BrandService {
    private final ModelRepository modelRepository;

    public BrandServiceImpl(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }


    @Override
    public List<BrandDTO> getAllBrands() {

        Map<String, BrandDTO> brands = new TreeMap<>();

        for (Model model : modelRepository.findAll()) {
            if (!brands.containsKey(model.getBrand().getName())) {
                brands.put(model.getBrand().getName(),new BrandDTO(model.getBrand().getName(),new ArrayList<>()));
            }
            brands.get(model.getBrand().getName()).models().add(new ModelDTO(model.getId(),model.getName()));
        }
        return brands.values().stream().toList();
    }
}
