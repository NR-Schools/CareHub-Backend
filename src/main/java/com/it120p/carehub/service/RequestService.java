package com.it120p.carehub.service;

import com.it120p.carehub.model.entity.Request;
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

    public List<Request> getRequestsByUserId(long userId) {
        return requestRepository.findRequestByUserId(userId);
    }
}
