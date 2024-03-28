package com.it120p.carehub.controller;

import com.it120p.carehub.model.entity.Request;
import com.it120p.carehub.model.entity.Status;
import com.it120p.carehub.model.entity.User;
import com.it120p.carehub.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class RequestController {

    @Autowired
    private RequestService requestService;

    @PostMapping("")
    public Request createCustomerRequest(
            Authentication authentication,
            @RequestParam("requestDetails") String requestDetails
    ) {
        // Get User from Authentication
        User user = (User) authentication.getPrincipal();

        // Create new Request
        Request newRequest = Request.builder()
                .requestDetails(requestDetails)
                .requestStatus(Status.PENDING)
                .customer(user)
                .build();

        // Save Request
        return requestService.saveRequest(newRequest);
    }

    @GetMapping("")
    public List<Request> getCustomerRequests(
            Authentication authentication
    ) {
        return List.of();
    }

    @PutMapping("")
    public Request updateCustomerRequest(
            Authentication authentication,
            @RequestParam("requestId") long requestId,
            @RequestParam("requestDetails") String requestDetails
    ) {
        return null;
    }

    @DeleteMapping("")
    public Request deleteCustomerRequest(
            Authentication authentication,
            @RequestParam("requestId") long requestId
    ) {
        return null;
    }
}
