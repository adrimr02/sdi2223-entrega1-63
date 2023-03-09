package es.uniovi.sdi63.sdi2223entrega163.controllers;

import es.uniovi.sdi63.sdi2223entrega163.entities.Offer;
import es.uniovi.sdi63.sdi2223entrega163.entities.Offer.OfferState;
import es.uniovi.sdi63.sdi2223entrega163.services.OfferService;
import es.uniovi.sdi63.sdi2223entrega163.services.UsersService;
import es.uniovi.sdi63.sdi2223entrega163.util.FileUploadUtil;
import es.uniovi.sdi63.sdi2223entrega163.validators.OfferFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Controller
public class OfferController {

    @Autowired
    private OfferService offerService;

    @Autowired
    private OfferFormValidator offerFormValidator;
    @Autowired
    private UsersService usersService;

    @RequestMapping("/offer/new")
    public String createOfferView(Model model) {
        model.addAttribute("offer", new Offer());
        return "offers/createOffer";
    }
    @RequestMapping(value = "/offer/new", method = RequestMethod.POST)
    public String createOfferForm(@RequestParam(name="image", required = false) MultipartFile image, @Validated @ModelAttribute Offer offer, Model model, BindingResult result ) throws IOException {
        //TODO Sustituir findFirst por el usuario logueado
        offer.setSeller( usersService.findFirst() );

        offerFormValidator.validate( offer, result );
        if (result.hasErrors()) {
            model.addAttribute( "offer", offer );
            System.out.println(offer);
            return "offers/createOffer";
        }

        if (image != null) {
            String imgPath = "user-photos/" + offer.getSeller().getId();
            String originalName = image.getOriginalFilename();
            String imgName = UUID.randomUUID() + originalName.substring( originalName.lastIndexOf( '.' ) );
            System.out.println(imgName);
            FileUploadUtil.saveFile( imgPath, imgName, image );
            offer.setImgPath( imgPath + "/" + imgName );
        }

        offer.setState( OfferState.AVAILABLE );
        offer.setDate( LocalDateTime.now() );
        offerService.addOffer( offer );
        return "redirect:/offer/my-offers";
    }

    @RequestMapping("/offer/my-offers")
    public String userOffersView(Model model) {
        //TODO Sustituir findFirst por el usuario logueado
        model.addAttribute( "offerList", offerService.getAllOffersFrom( usersService.findFirst() ) );
        return "offers/list";
    }

    @RequestMapping("/offer/delete/{id}")
    public String deleteOffer(@PathVariable String id) {
        //TODO Comprobar que la oferta pertenece al usuario logueado
        offerService.deleteOffer(id);
        return "redirect:/offer/my-offers";
    }

    @RequestMapping("/offer/{id}")
    public String offerDetailsView(@PathVariable String id, Model model) {
        var offer = offerService.getOfferById(id);
        model.addAttribute( "offer", offer );
        System.out.println(offer);
        return "offers/details";
    }

}
