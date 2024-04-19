package com.it120p.carehub.service;

import com.it120p.carehub.exceptions.MissingException;
import com.it120p.carehub.exceptions.PermissionException;
import com.it120p.carehub.model.entity.Offer;
import com.it120p.carehub.model.entity.Request;
import com.it120p.carehub.model.entity.User;
import com.it120p.carehub.repository.OfferRepository;
import com.it120p.carehub.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfferService {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private OfferRepository offerRepository;


    public Offer saveOffer(Offer offer) {
        return offerRepository.save(offer);
    }

    public Offer getOfferById(long offerId) {
        return offerRepository.findById(offerId).orElseThrow();
    }

    public List<Offer> getOwnedOffers(User serviceProvider) {
        return offerRepository.findOffersByServiceProvider(serviceProvider);
    }

    public List<Offer> getOffersByRequestId(long requestId) {
        // Get Request Object
        Request request = requestRepository.findRequestById(requestId).orElseThrow();

        // Get all offers constrained by the request
        return offerRepository.findOffersByRequestId(request);
    }

    public Offer updateOwnedOffer(User provider, Offer updatedOffer) throws Exception {
        // Check if owner
        if (updatedOffer.getServiceProvider().getUserId() != provider.getUserId()) throw new PermissionException("Offer");
        
        // Update offer
        offerRepository.save(updatedOffer);

        return updatedOffer;
    }

    public Offer removeOwnedOffer(User provider, long offerId) throws Exception {
        // Get Request
        Optional<Offer> optionalOffer = offerRepository.findById(offerId);
        if (optionalOffer.isEmpty()) throw new MissingException("Offer");

        Offer offer = optionalOffer.get();

        // Check if owner
        if (offer.getServiceProvider().getUserId() != provider.getUserId()) throw new PermissionException("Offer");

        // Remove offer
        offerRepository.delete(offer);

        // Return offer
        return offer;
    }
}
