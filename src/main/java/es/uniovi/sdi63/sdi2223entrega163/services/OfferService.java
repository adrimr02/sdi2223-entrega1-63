package es.uniovi.sdi63.sdi2223entrega163.services;

import es.uniovi.sdi63.sdi2223entrega163.entities.Offer;
import es.uniovi.sdi63.sdi2223entrega163.entities.Offer.OfferState;
import es.uniovi.sdi63.sdi2223entrega163.entities.User;
import es.uniovi.sdi63.sdi2223entrega163.repositories.OfferRepository;
import es.uniovi.sdi63.sdi2223entrega163.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException.MethodNotAllowed;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OfferService {

    public enum OfferServiceErrors {
        USER_NOT_ALLOWED,
        OFFER_DOES_NOT_EXISTS,
        OFFER_NOT_AVAILABLE,
        OWN_OFFER,
        NOT_ENOUGH_MONEY

    }

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private UsersService usersService;

    public void addOffer(Offer offer) {
        offer.setState( OfferState.AVAILABLE );
        offer.setDate( LocalDateTime.now() );
        offerRepository.save( offer );
    }

    public Page<Offer> getAllOffers(Pageable pageable, String query) {

        if (query != null && !query.isBlank()) {
            query = "%" + query + "%";
            return offerRepository.searchByTitle( pageable, query );
        } else {
            offerRepository.findAll(pageable).getContent().forEach( System.out::println );
            return offerRepository.findAll(pageable);
        }
    }

    public List<Offer> getAllOffersFrom( User user ) {
        return offerRepository.findAllFor( user );
    }

    public void deleteOffer(String id, User user) throws IOException {
        var offer = offerRepository.findById( id );
        if (offer.isPresent()) {
            if (user.equals( offer.get().getSeller() )) {
                if (offer.get().getImgPath().contains( "user-photos" ))
                    FileUploadUtil.deleteFile( offer.get().getImgPath() );

                offerRepository.deleteById( id );
            }
        }
    }

    public Offer getOfferById(String id) {
        return offerRepository.findById( id ).orElse( null );
    }

    public OfferServiceErrors buyOffer(String id) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        var user = usersService.getUserByEmail( email );
        if (user == null) return OfferServiceErrors.USER_NOT_ALLOWED;

        var offer = offerRepository.findById( id );
        if (offer.isEmpty()) return OfferServiceErrors.OFFER_DOES_NOT_EXISTS;

        return buyOffer( offer.get(), user );
    }

    @Transactional
    public OfferServiceErrors buyOffer(Offer offer, User buyer) throws IllegalStateException {
        if (buyer.equals( offer.getSeller() ))
            return OfferServiceErrors.OWN_OFFER;

        if (offer.getState() != OfferState.AVAILABLE)
            return OfferServiceErrors.OFFER_NOT_AVAILABLE;

        if (buyer.getWallet() < offer.getPrice())
            return OfferServiceErrors.NOT_ENOUGH_MONEY;

        offer.buy( buyer );
        offerRepository.save( offer );
        return null;
    }

}
