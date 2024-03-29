package es.uniovi.sdi63.sdi2223entrega163.validators;

import es.uniovi.sdi63.sdi2223entrega163.entities.Offer;
import es.uniovi.sdi63.sdi2223entrega163.services.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class OfferFormValidator implements Validator {

    @Autowired
    private OfferService offerService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Offer.class.equals( clazz );
    }

    @Override
    public void validate(Object target, Errors errors) {
        Offer offer = (Offer) target;

        if ( offerService.getAllOffersFrom( offer.getSeller() ).stream().anyMatch(
                o -> o.getTitle().equals( offer.getTitle() ) ) ) {
            errors.rejectValue( "title", "error.offer.title.duplicate" );
        }
        if (offer.getTitle().isBlank()) {
            errors.rejectValue( "title", "error.offer.title.missing" );
        } else if (offer.getTitle().length() < 4) {
            errors.rejectValue( "title", "error.offer.title.short" );
        }
        if (offer.getPrice() <= 0) {
            errors.rejectValue( "price", "error.offer.price.negative" );
        }

    }
}
