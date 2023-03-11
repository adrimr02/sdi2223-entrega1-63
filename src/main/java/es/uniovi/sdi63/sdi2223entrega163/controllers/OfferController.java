package es.uniovi.sdi63.sdi2223entrega163.controllers;

import es.uniovi.sdi63.sdi2223entrega163.entities.Offer;
import es.uniovi.sdi63.sdi2223entrega163.entities.Offer.OfferState;
import es.uniovi.sdi63.sdi2223entrega163.services.OfferService;
import es.uniovi.sdi63.sdi2223entrega163.services.UsersService;
import es.uniovi.sdi63.sdi2223entrega163.util.FileUploadUtil;
import es.uniovi.sdi63.sdi2223entrega163.validators.OfferFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public String createOfferForm(@RequestParam(name="image", required = false) MultipartFile image, @Validated @ModelAttribute Offer offer, Principal principal, Model model, BindingResult result ) throws IOException {
        String email = principal.getName();
        var user = usersService.getUserByEmail( email );
        offer.setSeller( user );

        offerFormValidator.validate( offer, result );
        if (result.hasErrors()) {
            model.addAttribute( "offer", offer );
            System.out.println(offer);
            return "offers/createOffer";
        }

        if (image != null && !image.isEmpty()) {
            String imgPath = "user-photos/" + offer.getSeller().getId();
            String originalName = image.getOriginalFilename();
            String imgName;

            if (originalName != null)
                imgName = UUID.randomUUID() + originalName.substring( originalName.lastIndexOf( '.' ) );
            else
                imgName = UUID.randomUUID().toString();

            System.out.println(imgName);
            FileUploadUtil.saveFile( imgPath, imgName, image );
            offer.setImgPath( imgPath + "/" + imgName );
        } else {
            offer.setImgPath( "images/defaultImg.jpg" );
        }

        offerService.addOffer( offer );
        return "redirect:/offer/my-offers";
    }

    @RequestMapping("/offer/my-offers")
    public String userOffersView(Model model, Principal principal) {
        String email = principal.getName();
        var user = usersService.getUserByEmail( email );
        model.addAttribute( "offerList", offerService.getAllOffersFrom( user ) );
        return "offers/ownList";
    }

    @RequestMapping("/offer/delete/{id}")
    public String deleteOffer(@PathVariable String id, Principal principal) throws IOException {
        String email = principal.getName();
        var user = usersService.getUserByEmail( email );
        offerService.deleteOffer(id, user);
        return "redirect:/offer/my-offers";
    }

    @RequestMapping("/offers")
    public String offerListView(Model model, Pageable pageable, @RequestParam(name = "search", required = false) String query) {
        model.addAttribute( "offerList",    offerService.getAllOffers(pageable, query) );
        return "offers/list";
    }

}
