package com.it120p.carehub.controller;

import com.it120p.carehub.model.entity.Resource;
import com.it120p.carehub.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @GetMapping("/user")
    public ResponseEntity<byte[]> getUserResource(
            @RequestParam("resourceId") String resourceId
    ) {
        // Get Resource
        Resource resource = resourceService.getResource(
                resourceId,
                "user"
        );

        // Try to get resource bytes
        byte[] resourceBytes = resource.getResourceBytes();
        if (resourceBytes == null) return ResponseEntity.notFound().build();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", resource.getContentType());
        return new ResponseEntity<>(
                resourceBytes,
                headers,
                200
        );
    }
}