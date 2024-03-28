package com.it120p.carehub.service;

import com.it120p.carehub.model.entity.Request;
import com.it120p.carehub.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    public Request saveRequest(Request request) {
        return requestRepository.save(request);
    }
}
