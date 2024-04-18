package com.it120p.carehub.controller;

import com.it120p.carehub.exceptions.StatusException;
import com.it120p.carehub.model.dto.RequestDTO;
import com.it120p.carehub.model.entity.Request;
import com.it120p.carehub.model.entity.OfferStatus;
import com.it120p.carehub.model.entity.RequestStatus;
import com.it120p.carehub.model.entity.User;
import com.it120p.carehub.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/request")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @PostMapping
    public RequestDTO createCustomerRequest(
            Authentication authentication,
            @RequestParam("requestDetails") String requestDetails
    ) throws Exception {
        // Get User from Authentication
        User user = (User) authentication.getPrincipal();

        // Check if user is a customer (No User Service set)
        if (user.getUserServiceCare() != null) {
            throw new StatusException(
                    "User",
                    "Customer",
                    "Service Provider"
            );
        }

        // Create new Request
        Request newRequest = Request.builder()
                .requestDetails(requestDetails)
                .requestStatus(RequestStatus.OPEN)
                .customer(user)
                .build();

        // Save Request
        return RequestDTO.fromRequest(requestService.saveRequest(newRequest));
    }

    @GetMapping
    public List<RequestDTO> getSelfCustomerRequests(
            Authentication authentication
    ) {
        // Get User from Authentication
        User user = (User) authentication.getPrincipal();

        // Get Request by Id
        return requestService.getOwnedRequests(user)
                .stream()
                .map(RequestDTO::fromRequest)
                .toList();
    }

    @GetMapping("/all")
    public List<RequestDTO> getAllCustomerRequests(
                Authentication authentication
        ) {
        return requestService.getAllRequests()
                .stream()
                .map(RequestDTO::fromRequest)
                .toList();
    }

    @DeleteMapping
    public RequestDTO deleteCustomerRequest(
            Authentication authentication,
            @RequestParam("requestId") long requestId
    ) throws Exception {

        // Get User from Authentication
        User user = (User) authentication.getPrincipal();

        // Remove Request
        return RequestDTO.fromRequest(
                requestService.removeOwnedRequest(
                        user,
                        requestId
                )
        );
    }
}
