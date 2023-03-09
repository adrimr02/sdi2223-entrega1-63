package es.uniovi.sdi63.sdi2223entrega163.services;

import es.uniovi.sdi63.sdi2223entrega163.entities.Offer;
import es.uniovi.sdi63.sdi2223entrega163.entities.Offer.OfferState;
import es.uniovi.sdi63.sdi2223entrega163.entities.User;
import es.uniovi.sdi63.sdi2223entrega163.repositories.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OfferService {

    @Autowired
    private OfferRepository offerRepository;

    public void addOffer(Offer offer) {
        offerRepository.save( offer );
    }

    public List<Offer> getAllAvailableOffers() {
        return offerRepository.findAllAvailable( OfferState.AVAILABLE );
    }

    public List<Offer> getAllOffersFrom( User user ) {
        return offerRepository.findAllFor( user );
    }

    public void deleteOffer(String id) {
        offerRepository.deleteById( id );
    }

    public Offer getOfferById(String id) {
        return offerRepository.findById( id ).orElse( null );
    }

}
