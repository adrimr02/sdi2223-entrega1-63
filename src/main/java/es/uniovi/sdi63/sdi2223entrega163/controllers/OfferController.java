package es.uniovi.sdi63.sdi2223entrega163.controllers;

import es.uniovi.sdi63.sdi2223entrega163.entities.Offer;
import es.uniovi.sdi63.sdi2223entrega163.entities.Offer.OfferState;
import es.uniovi.sdi63.sdi2223entrega163.services.OfferService;
import es.uniovi.sdi63.sdi2223entrega163.services.UsersService;
import es.uniovi.sdi63.sdi2223entrega163.util.FileUploadUtil;
import es.uniovi.sdi63.sdi2223entrega163.util.Round;
import es.uniovi.sdi63.sdi2223entrega163.validators.OfferFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.security.Principal;
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
    public String createOfferForm(@RequestParam(name="image", required = false)
                                      MultipartFile image,
                                  @Validated @ModelAttribute Offer offer,
                                  Principal principal, Model model,
                                  BindingResult result ) throws IOException {
        System.out.println(offer.getPrice());
        String email = principal.getName();
        var user = usersService.getUserByEmail( email );
        offer.setSeller( user );

        offerFormValidator.validate( offer, result );
        if (result.hasErrors()) {
            model.addAttribute( "offer", offer );
            return "offers/createOffer";
        }

        offerService.addOffer( offer, image );
        return "redirect:/offer/my-offers";
    }

    @RequestMapping("/offer/my-offers")
    public String userOffersView(Model model, Principal principal) {
        String email = principal.getName();
        var user = usersService.getUserByEmail( email );
        model.addAttribute( "offerList",
                offerService.getAllOffersFrom( user ) );
        return "offers/ownList";
    }

    @RequestMapping("/offer/delete/{id}")
    public String deleteOffer(@PathVariable long id)
            throws IOException {
        offerService.deleteOffer( id );
        return "redirect:/offer/my-offers";
    }

    @RequestMapping("/offer/buy/{id}")
    public String offerListView(@PathVariable long id, HttpSession session, Principal principal) {
        var errors = offerService.buyOffer( id );

        if (errors != null) {
            return switch (errors) {
                case USER_NOT_ALLOWED -> "redirect:/home?error=1";
                case OFFER_DOES_NOT_EXISTS -> "redirect:/home?error=2";
                case OFFER_NOT_AVAILABLE -> "redirect:/home?error=3";
                case OWN_OFFER -> "redirect:/home?error=4";
                case NOT_ENOUGH_MONEY -> "redirect:/home?error=5";
            };
        }
        var user = usersService.getUserByEmail( principal.getName() );
        session.setAttribute( "wallet", Round.twoCents( user.getWallet() ));
        return "redirect:/home";
    }

    @RequestMapping("/offer/bought")
    public String boughtOffersView(Model model, Principal principal) {
        String email = principal.getName();
        var user = usersService.getUserByEmail( email );
        model.addAttribute( "offerList",
                offerService.getAllOffersBoughtBy( user ) );
        return "offers/boughtList";
    }

}
