package org.softuni.mobilele.web;

import jakarta.validation.Valid;
import org.softuni.mobilele.model.dto.CreateOfferDTO;
import org.softuni.mobilele.model.dto.OfferDetailDTO;
import org.softuni.mobilele.model.enums.EngineEnum;
import org.softuni.mobilele.service.BrandService;
import org.softuni.mobilele.service.OfferService;
import org.softuni.mobilele.service.exception.ObjectNotFoundException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequestMapping("/offer")
public class OfferController {

    private final OfferService offerService;
    private final BrandService brandService;

    public OfferController(OfferService offerService, BrandService brandService){
        this.offerService = offerService;
        this.brandService = brandService;
    }


    //изпращане на енъмите към фронт-енда
    @ModelAttribute("engines")
    public EngineEnum[] engines(){
        return EngineEnum.values();
    }

    @GetMapping("/add")
    public String add(Model model) {

        if (!model.containsAttribute("createOfferDTO")){
            model.addAttribute("createOfferDTO", CreateOfferDTO.empty());
        }

        model.addAttribute("brands", brandService.getAllBrands());

        return "offer-add";
    }

    @PostMapping("/add")
    public String add(@Valid CreateOfferDTO createOfferDTO,
                      BindingResult bindingResult,
                      RedirectAttributes rAtt,
                      @AuthenticationPrincipal UserDetails seller) {

        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("createOfferDTO", createOfferDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.createOfferDTO", bindingResult);
            return "redirect:/offer/add";
        }



         UUID newOfferUUID = offerService.createOffer(createOfferDTO,seller);
        return "redirect:/offer/" + newOfferUUID;
    }

    @GetMapping("/{uuid}")
    public String details(@PathVariable("uuid") UUID uuid, Model model) {

       OfferDetailDTO offerDetailDTO = offerService
                .getOfferDetail(uuid)
                .orElseThrow(() -> new ObjectNotFoundException("Object with uuid " + uuid + " was not found!"));

       model.addAttribute("offer", offerDetailDTO);
        return "details";
    }

    @DeleteMapping("/{uuid}")
    public String delete(@PathVariable("uuid") UUID uuid) {
        offerService.deleteOffer(uuid);
        return "redirect:/offers/all";
    }
}
