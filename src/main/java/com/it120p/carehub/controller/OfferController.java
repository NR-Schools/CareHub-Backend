package com.it120p.carehub.controller;

import com.it120p.carehub.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OfferController {

    @Autowired
    private OfferService offerService;
}
