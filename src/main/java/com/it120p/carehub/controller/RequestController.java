package com.it120p.carehub.controller;

import com.it120p.carehub.model.entity.Request;
import com.it120p.carehub.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RequestController {

    @Autowired
    private RequestService requestService;
}
