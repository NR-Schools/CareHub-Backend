package com.it120p.carehub.service;

import com.it120p.carehub.exceptions.MissingException;
import com.it120p.carehub.exceptions.PermissionException;
import com.it120p.carehub.model.entity.Request;
import com.it120p.carehub.model.entity.User;
import com.it120p.carehub.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    public Request saveRequest(Request request) {
        return requestRepository.save(request);
    }

    public List<Request> getOwnedRequests(User customer) {
        return requestRepository.findRequestsByUser(customer);
    }

    public Request removeOwnedRequest(User customer, long requestId) throws Exception {
        // Get Request
        Optional<Request> optionalRequest = requestRepository.findRequestById(requestId);
        if (optionalRequest.isEmpty()) throw new MissingException("Request");

        Request request = optionalRequest.get();

        // Check if owner
        if (request.getCustomer().getUserId() != customer.getUserId()) throw new PermissionException("Request");

        // Remove request
        requestRepository.delete(request);

        // Return request
        return request;
    }
}
