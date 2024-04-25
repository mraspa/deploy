package com.md.onboarding.service;

import com.md.onboarding.exception.EntityNotFoundException;
import com.md.onboarding.model.entity.ClientDetail;
import com.md.onboarding.model.entity.IdentityDocument;

import java.util.List;

public interface ClientDetailService {

    void create(ClientDetail clientDetail);

    IdentityDocument getIdentityDocumentByIdNumber(Long idNumber) throws EntityNotFoundException;

    boolean checkIdNumber(Long idNumber);

    boolean checkCuilNumber(String cuil);

    boolean checkMail(String mail);

    boolean checkPhoneNumber(String phoneNumber);

    List <String> getGenderTypeStrings();

    List <String> getIdentityDocumentCopyTypeStrings();

    List <String> getJobTypeStrings();

    List<String> getMaritalStatusStrings();

}
