package com.md.onboarding.service.impl;

import com.md.onboarding.exception.EntityNotFoundException;
import com.md.onboarding.model.entity.Address;
import com.md.onboarding.model.entity.ClientDetail;

import com.md.onboarding.model.entity.Contact;
import com.md.onboarding.model.entity.IdentityDocument;
import com.md.onboarding.model.enumerate.GenderType;
import com.md.onboarding.model.enumerate.IdentityDocumentCopyType;
import com.md.onboarding.model.enumerate.JobType;
import com.md.onboarding.model.enumerate.MaritalStatus;
import com.md.onboarding.repository.AddressRepository;
import com.md.onboarding.repository.ClientDetailRepository;
import com.md.onboarding.repository.ContactRepository;
import com.md.onboarding.repository.IdentityDocumentRepository;
import com.md.onboarding.service.ClientDetailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class ClientDetailServiceImpl implements ClientDetailService {

    private final ClientDetailRepository clientDetailRepository;

    private final AddressRepository addressRepository;

    private final IdentityDocumentRepository identityDocumentRepository;

    private final ContactRepository contactRepository;

    @Override
    public void create(ClientDetail clientDetail) {

        Address newClientAddress = addressRepository.save(clientDetail.getAddress());

        IdentityDocument newClientIdentityDocument = identityDocumentRepository.save(clientDetail.getIdentityDocument());

        Contact newClientContact = contactRepository.save(clientDetail.getContact());

        clientDetail.setAddress(newClientAddress);

        clientDetail.setIdentityDocument(newClientIdentityDocument);

        clientDetail.setContact(newClientContact);

        clientDetailRepository.save(clientDetail);
    }

    public boolean checkIdNumber(Long idNumber){
        return identityDocumentRepository.findByIdNumber(idNumber).isEmpty();
    }

    public IdentityDocument getIdentityDocumentByIdNumber(Long idNumber) throws EntityNotFoundException {
       return identityDocumentRepository.findByIdNumber(idNumber)
               .orElseThrow(() ->
                       new EntityNotFoundException("This id number is not related to any client..."));
    }

    @Override
    public boolean checkCuilNumber(Long cuil) {
        return clientDetailRepository.findByCuil(cuil).isEmpty();
    }

    @Override
    public boolean checkMail(String mail) {
        return contactRepository.findByMail(mail).isEmpty();
    }

    @Override
    public boolean checkPhoneNumber(String phoneNumber) {
        return contactRepository.findByPhoneNumber(phoneNumber).isEmpty();
    }

    @Override
    public List <String> getGenderTypeStrings(){
        return Arrays.stream(GenderType.values()).map(Enum::name).toList();
    }

    @Override
    public List <String> getIdentityDocumentCopyTypeStrings(){
        return Arrays.stream(IdentityDocumentCopyType.values()).map(Enum::name).toList();
    }

    @Override
    public List <String> getJobTypeStrings(){
        return Arrays.stream(JobType.values()).map(Enum::name).toList();
    }

    @Override
    public List<String> getMaritalStatusStrings(){
        return Arrays.stream(MaritalStatus.values()).map(Enum::name).toList();
    }

}
