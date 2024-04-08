package com.it120p.carehub.service;

import com.it120p.carehub.model.entity.Resource;
import com.it120p.carehub.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    private String resourceIdGenerator(String type) {
        // Format: <date>_<type>_<RNG>
        boolean isIdUnique = false;
        String resourceId = "";

        while (!isIdUnique) {
            // Get Current Date
            String formattedDate = LocalDate.now().format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd")
            );

            // Generate Random String
            StringBuilder sb = new StringBuilder();
            SecureRandom random = new SecureRandom();
            for (int i = 0; i < 10; i++) {
                int digit = random.nextInt(10);
                sb.append(digit);
            }
            String randomString = sb.toString();

            // Construct resourceId
            resourceId = String.format("%s_%s_%s", formattedDate, type, randomString);

            // Check if Id already exists
            if ( resourceRepository.findById(resourceId).isEmpty() ) {
                isIdUnique = true;
            }
        }

        return resourceId;
    }

    private Resource setResource(String type, MultipartFile file) throws Exception {
        // Create Resource
        return resourceRepository.save(
                Resource.builder()
                        .resourceId( resourceIdGenerator(type) )
                        .resourceType( type )
                        .contentType( file.getContentType() )
                        .resourceBytes( file.getBytes() )
                        .build()
        );
    }

    public Resource setUserResource(MultipartFile file) throws Exception {
        return setResource("user", file);
    }

    public Resource getResource(String resourceId, String type) {
        return resourceRepository.getResourceByIdAndType(
                resourceId,
                type
        ).orElseThrow();
    }
}
