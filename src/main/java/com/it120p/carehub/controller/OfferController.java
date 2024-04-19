package com.it120p.carehub.controller;

import com.it120p.carehub.exceptions.PermissionException;
import com.it120p.carehub.exceptions.StatusException;
import com.it120p.carehub.model.dto.OfferDTO;
import com.it120p.carehub.model.entity.*;
import com.it120p.carehub.service.OfferService;
import com.it120p.carehub.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/offer")
public class OfferController {

    @Autowired
    private RequestService requestService;

    @Autowired
    private OfferService offerService;


    @PostMapping
    public OfferDTO createServiceProviderOffer(
            Authentication authentication,
            @RequestParam("requestId") long requestId,
            @RequestParam("offerDetails") String offerDetails
    ) throws Exception {
        // Get User from Authentication
        User user = (User) authentication.getPrincipal();

        // Check if user is a service provider (Has User Service set)
        if (user.getUserServiceCare() == null) {
            throw new StatusException(
                    "User",
                    "Service Provider",
                    "Customer"
            );
        }

        // Check Request Status if still Opened
        Request request = requestService.getRequestById(requestId);
        if (request.getRequestStatus() != RequestStatus.OPEN)
            throw new StatusException(
                    "Request",
                    RequestStatus.OPEN.name(),
                    request.getRequestStatus().name()
            );

        // Create Offer
        Offer offer = Offer.builder()
                .offerDetails(offerDetails)
                .offerStatus(OfferStatus.PENDING)
                .request(request)
                .serviceProvider(user)
                .build();

        return OfferDTO.fromOffer(
                offerService.saveOffer(offer)
        );
    }

    @GetMapping
    public List<OfferDTO> getAllOwnedOffers(
            Authentication authentication
    ) {
        // Get User from Authentication
        User user = (User) authentication.getPrincipal();

        // Get all Offers by User
        return offerService.getOwnedOffers(user)
                .stream()
                .map(OfferDTO::fromOffer)
                .toList();
    }

    @GetMapping("/request")
    public List<OfferDTO> getAllOffersByRequestId(
            @RequestParam("requestId") long requestId
    ) {
        return offerService.getOffersByRequestId(requestId)
                .stream()
                .map(OfferDTO::fromOffer)
                .toList();
    }

    @PutMapping
    public OfferDTO updateServiceProviderOffer(
            Authentication authentication,
            @RequestParam("offerId") long offerId,
            @RequestParam("offerDetails") String offerDetails
    ) throws Exception {
        // Get User from Authentication
        User user = (User) authentication.getPrincipal();

        // Check Offer Status if still PENDING
        Offer offer = offerService.getOfferById(offerId);
        if (offer.getOfferStatus() != OfferStatus.PENDING)
            throw new StatusException(
                    "Offer",
                    OfferStatus.PENDING.name(),
                    offer.getOfferStatus().name()
            );

        // Update Offer
        offer.setOfferDetails(offerDetails);

        // Save Offer
        return OfferDTO.fromOffer(
                offerService.updateOwnedOffer(user, offer)
        );
    }

    @DeleteMapping
    public OfferDTO deleteServiceProviderOffer(
            Authentication authentication,
            @RequestParam("offerId") long offerId
    ) throws Exception {
        // Get User from Authentication
        User user = (User) authentication.getPrincipal();

        // Check Offer Status if still PENDING
        Offer offer = offerService.getOfferById(offerId);
        if (offer.getOfferStatus() != OfferStatus.PENDING)
            throw new StatusException(
                    "Offer",
                    OfferStatus.PENDING.name(),
                    offer.getOfferStatus().name()
            );

        // Remove Offer
        return OfferDTO.fromOffer(
                offerService.removeOwnedOffer(user, offerId)
        );
    }



    @PutMapping("/accept")
    public OfferDTO customerAcceptServiceProviderOffer(
            Authentication authentication,
            @RequestParam("offerId") long offerId
    ) throws Exception {

        // Get User from Authentication
        User user = (User) authentication.getPrincipal();

        // Get Offer and Request from offerId
        Offer offer = offerService.getOfferById(offerId);
        Request request = offer.getRequest();

        // Check if request customer is the same with user
        if (!request.getCustomer().equals(user))
            throw new PermissionException("User");

        // Change Status of Request and Offer
        request.setRequestStatus(RequestStatus.CLOSED);
        offer.setRequest(request);
        offer.setOfferStatus(OfferStatus.ACCEPTED);

        // Save Request and Offer
        return OfferDTO.fromOffer(
                offerService.saveOffer(offer)
        );
    }

    @PutMapping("/revert")
    public OfferDTO revertBackStatus(
            Authentication authentication,
            @RequestParam("offerId") long offerId
    ) throws Exception {

        // Get User from Authentication
        User user = (User) authentication.getPrincipal();

        // Get Offer and Request from offerId
        Offer offer = offerService.getOfferById(offerId);
        Request request = offer.getRequest();

        // Check if request customer is the same with user
        // or Check if offer serviceProvider is the same with user
        if (!request.getCustomer().equals(user) &&
            !offer.getServiceProvider().equals(user))
            throw new PermissionException("User");

        // Change Status of Request and Offer
        request.setRequestStatus(RequestStatus.OPEN);
        offer.setRequest(request);
        offer.setOfferStatus(OfferStatus.PENDING);

        // Save Request and Offer
        return OfferDTO.fromOffer(
                offerService.saveOffer(offer)
        );
    }
}