package es.uniovi.sdi63.sdi2223entrega163.controllers;

import es.uniovi.sdi63.sdi2223entrega163.entities.Offer;
import es.uniovi.sdi63.sdi2223entrega163.entities.Offer.OfferState;
import es.uniovi.sdi63.sdi2223entrega163.entities.User;
import es.uniovi.sdi63.sdi2223entrega163.services.OfferService;
import es.uniovi.sdi63.sdi2223entrega163.validators.OfferFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDateTime;

@Controller
public class OfferController {

    @Autowired
    private OfferService offerService;

    @Autowired
    private OfferFormValidator offerFormValidator;

    private User testUser = new User( "uo284163@uniovi.es", "Adrian", "Martinez" );

    @RequestMapping("/offer/new")
    public String createOfferView(Model model) {
        model.addAttribute("offer", new Offer());
        return "offers/createOffer";
    }
    @RequestMapping(value = "/offer/new", method = RequestMethod.POST)
    public String createOfferForm(Model model, @Validated @ModelAttribute Offer offer, BindingResult result) {
        if (offer.getSeller() == null)
            offer.setSeller( testUser );

        offerFormValidator.validate( offer, result );
        if (result.hasErrors()) {
            model.addAttribute( "offer", offer );
            return "offers/createOffer";
        }
        offer.setState( OfferState.AVAILABLE );
        offer.setDate( LocalDateTime.now() );
        offerService.addOffer( offer );
        return "redirect:/offer/my-offers";
    }

    @RequestMapping("/offer/my-offers")
    public String userOffersView(Model model) {
        model.addAttribute( "offerList", offerService.getAllOffersFrom( testUser ) );
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
        return "offers/details";
    }

}
