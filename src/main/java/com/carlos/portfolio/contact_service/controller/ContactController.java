package com.carlos.portfolio.contact_service.controller;

import com.carlos.portfolio.contact_service.dto.ContactRequest;
import com.carlos.portfolio.contact_service.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = "*")
public class ContactController {

    private final EmailService emailService;

    public ContactController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<Void> sendMessage(
            @Valid @RequestBody ContactRequest request
    ) {
        emailService.sendContactEmail(
                request.getEmail(),
                request.getName(),
                request.getMessage()
        );

        return ResponseEntity.ok().build();
    }
}
