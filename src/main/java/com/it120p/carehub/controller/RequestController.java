package com.it120p.carehub.controller;

import com.it120p.carehub.exceptions.StatusException;
import com.it120p.carehub.model.entity.Request;
import com.it120p.carehub.model.entity.Status;
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

    @GetMapping("/all")
    public List<Request> getAllCustomerRequests() {
        return requestService.getAllRequests();
    }

    @GetMapping("/user")
    public List<Request> getCustomerRequestsById(
            Authentication authentication
    ) {
        // Get User from Authentication
        User user = (User) authentication.getPrincipal();

        // Get Request by Id
        return requestService.getRequestsByUser(user);
    }

    @PutMapping("")
    public Request updateCustomerRequest(
            Authentication authentication,
            @RequestParam("requestId") long requestId,
            @RequestParam("requestDetails") String requestDetails
    ) throws Exception {
        // Get User from Authentication
        User user = (User) authentication.getPrincipal();

        // Fetch existing Request
        Request existingRequest = requestService.getRequestFromUser(
                user,
                requestId
        );

        // Must still be pending
        if ( existingRequest.getRequestStatus() != Status.PENDING ) {
            throw new StatusException(
                    "Request",
                    Status.PENDING.name(),
                    existingRequest.getRequestStatus().name()
            );
        }

        // Update existing Request
        existingRequest.setRequestDetails(requestDetails);

        // Save Request
        return requestService.saveRequest(existingRequest);
    }

    @DeleteMapping("")
    public Request deleteCustomerRequest(
            Authentication authentication,
            @RequestParam("requestId") long requestId
    ) throws Exception {
        // Get User from Authentication
        User user = (User) authentication.getPrincipal();

        // Fetch existing Request
        Request existingRequest = requestService.getRequestFromUser(
                user,
                requestId
        );

        // Must still be pending
        if ( existingRequest.getRequestStatus() != Status.PENDING ) {
            throw new StatusException(
                    "Request",
                    Status.PENDING.name(),
                    existingRequest.getRequestStatus().name()
            );
        }

        // Remove Request
        return requestService.removeRequestFromUser(
                user,
                requestId
        );
    }
}
