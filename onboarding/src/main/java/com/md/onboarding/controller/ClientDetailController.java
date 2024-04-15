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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("onboarding/v1/client-details")
public class ClientDetailController implements ClientDetailApi {

    private final ObjectMapper mapper;

    private final ClientDetailService clientDetailService;

    private final MailConfirmationTokenService mailConfirmationTokenService;

    @PostMapping
    @Override
    public ResponseEntity<Void> create(@RequestBody ClientDetailRequest clientDetailRequest){

        clientDetailService.create(mapClientDetailRequest(clientDetailRequest));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/id-details/{idNumber}")
    @Override
    public ResponseEntity<IdentityDocument> getIdentityDocumentByIdNumber(@PathVariable("idNumber") Long idNumber) throws EntityNotFoundException {
        return new ResponseEntity<>(clientDetailService.getIdentityDocumentByIdNumber(idNumber),HttpStatus.OK);
    }

    @GetMapping("/check-id/{idNumber}")
    @Override
    public ResponseEntity<Boolean> checkIdNumber(@PathVariable("idNumber") Long idNumber){
        return new ResponseEntity<>(clientDetailService.checkIdNumber(idNumber),HttpStatus.OK);
    }

    @GetMapping("/check-cuil/{cuil}")
    @Override
    public ResponseEntity<Boolean> checkCuil(@PathVariable("cuil") Long cuil){
        return new ResponseEntity<>(clientDetailService.checkCuilNumber(cuil),HttpStatus.OK);
    }

    @GetMapping("/check-mail/{mail}")
    @Override
    public ResponseEntity<Boolean> checkMail(@PathVariable("mail") String mail) {
        return new ResponseEntity<>(clientDetailService.checkMail(mail),HttpStatus.OK);
    }

    @GetMapping("/check-phoneNumber/{phoneNumber}")
    @Override
    public ResponseEntity<Boolean> checkPhoneNumber(@PathVariable("phoneNumber") String phoneNumber) {
        return new ResponseEntity<>(clientDetailService.checkPhoneNumber(phoneNumber),HttpStatus.OK);
    }

    private ClientDetail mapClientDetailRequest(ClientDetailRequest clientDetailRequest){
        ClientDetail clientDetails = mapper.convertValue(clientDetailRequest, ClientDetail.class);

        clientDetails.setAddress(mapper
                .convertValue(clientDetailRequest.addressRequest(), Address.class));

        clientDetails.setIdentityDocument(mapper
                .convertValue(clientDetailRequest.identityDocumentRequest(), IdentityDocument.class));

        clientDetails.setContact(mapper
                .convertValue(clientDetailRequest.contactRequest(), Contact.class));

        return clientDetails;
    }

    @PostMapping("/sendMailConfirmationToken/{mailClient}")
    @Override
    public ResponseEntity<Boolean> sendMailConfirmationToken(@PathVariable("mailClient") String mailClient) throws Exception {
        return new ResponseEntity<>(mailConfirmationTokenService.sendMailConfirmationToken(mailClient), HttpStatus.OK);
    }

    @PostMapping("/validateMailConfirmationToken")
    @Override
    public ResponseEntity<Boolean> validateMailConfirmationToken(@RequestBody ValidateMailRequest validateMailRequest) throws Exception {
        return new ResponseEntity<>(mailConfirmationTokenService.validateMailConfirmationToken(validateMailRequest), HttpStatus.OK);
    }

    @PostMapping("/cancelMailConfirmationTokenValidation/{mailClient}")
    @Override
    public ResponseEntity<Void> cancelMailConfirmationTokenValidation(@PathVariable("mailClient") String mailClient) throws Exception {
        mailConfirmationTokenService.cancelMailConfirmationTokenValidation(mailClient);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/enums/marital-status")
    @Override
    public ResponseEntity<List<String>> getMaritalStatusStrings(){
        return new ResponseEntity<>(clientDetailService.getMaritalStatusStrings(),HttpStatus.OK);
    }

    @GetMapping("/enums/gender-types")
    @Override
    public ResponseEntity<List<String>> getGenderTypeStrings(){
        return new ResponseEntity<>(clientDetailService.getGenderTypeStrings(),HttpStatus.OK);
    }

    @GetMapping("/enums/identity-document-copies")
    public ResponseEntity<List<String>> getIdentityDocumentCopyStrings(){
        return new ResponseEntity<>(clientDetailService.getIdentityDocumentCopyTypeStrings(),HttpStatus.OK);
    }

    @GetMapping("/enums/job-types")
    public ResponseEntity<List<String>> getJobTypeStrings(){
        return new ResponseEntity<>(clientDetailService.getJobTypeStrings(),HttpStatus.OK);
    }

}
