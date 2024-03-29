package com.it120p.carehub.service;

import com.it120p.carehub.model.entity.Request;
import com.it120p.carehub.model.entity.User;
import com.it120p.carehub.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    public Request saveRequest(Request request) {
        return requestRepository.save(request);
    }

    public List<Request> getAllRequests() {
        return Streamable.of(requestRepository.findAll()).toList();
    }

    public List<Request> getRequestsByUser(User customer) {
        return requestRepository.findRequestsByUser(customer);
    }

    public Request getRequestFromUser(User customer, long requestId) {
        return requestRepository.findRequestByUserIdAndRequestId(customer, requestId).orElseThrow();
    }

    public Request removeRequestFromUser(User customer, long requestId) {
        Request request = requestRepository.findRequestByUserIdAndRequestId(customer, requestId).orElseThrow();
        requestRepository.delete(request);
        return request;
    }
}
