package com.md.onboarding.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.md.onboarding.documentation.ClientDetailApi;
import com.md.onboarding.exception.EntityNotFoundException;
import com.md.onboarding.model.dto.ClientDetailRequest;
import com.md.onboarding.model.dto.ValidateMailRequest;
import com.md.onboarding.model.entity.Address;
import com.md.onboarding.model.entity.ClientDetail;
import com.md.onboarding.model.entity.Contact;
import com.md.onboarding.model.entity.IdentityDocument;
import com.md.onboarding.service.ClientDetailService;
import com.md.onboarding.service.MailConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("clients/v1/details")
public class ClientDetailController implements ClientDetailApi {

    private final ClientDetailService clientDetailService;

    @GetMapping("/{idNumber}")
    @Override
    public ResponseEntity<IdentityDocument> getIdentityDocumentByIdNumber(@PathVariable("idNumber") Long idNumber) throws EntityNotFoundException {
        return new ResponseEntity<>(clientDetailService.getIdentityDocumentByIdNumber(idNumber),HttpStatus.OK);
    }

}
