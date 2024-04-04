package com.it120p.carehub.service;

import com.it120p.carehub.model.entity.Offer;
import com.it120p.carehub.model.entity.Request;
import com.it120p.carehub.model.entity.User;
import com.it120p.carehub.repository.OfferRepository;
import com.it120p.carehub.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Offer removeOffer(long offerId) {
        Offer offer = offerRepository.findById(offerId).orElseThrow();
        offerRepository.delete(offer);
        return offer;
    }
}
