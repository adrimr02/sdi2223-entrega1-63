package es.uniovi.sdi63.sdi2223entrega163.services;

import es.uniovi.sdi63.sdi2223entrega163.entities.Offer;
import es.uniovi.sdi63.sdi2223entrega163.entities.Offer.OfferState;
import es.uniovi.sdi63.sdi2223entrega163.entities.User;
import es.uniovi.sdi63.sdi2223entrega163.repositories.OfferRepository;
import es.uniovi.sdi63.sdi2223entrega163.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Autowired
    private OfferRepository offerRepository;

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

    @Transactional
    public void buyOffer(Offer offer, User buyer) {
        offer.buy( buyer );
        offerRepository.save( offer );
    }

}
