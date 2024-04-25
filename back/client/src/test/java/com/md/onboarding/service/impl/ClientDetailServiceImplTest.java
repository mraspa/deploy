package com.md.onboarding.service.impl;

import com.md.onboarding.exception.EntityNotFoundException;
import com.md.onboarding.model.entity.Address;
import com.md.onboarding.model.entity.ClientDetail;
import com.md.onboarding.model.entity.Contact;
import com.md.onboarding.model.entity.IdentityDocument;
import com.md.onboarding.model.enumerate.MaritalStatus;
import com.md.onboarding.repository.AddressRepository;
import com.md.onboarding.repository.ClientDetailRepository;
import com.md.onboarding.repository.ContactRepository;
import com.md.onboarding.repository.IdentityDocumentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.md.onboarding.JUtils.Data.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientDetailServiceImplTest {

    @Mock
    private ClientDetailRepository clientDetailRepository;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private IdentityDocumentRepository identityDocumentRepository;
    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ClientDetailServiceImpl clientDetailService;

    @Test
    void validRequestShouldCreateClientDetail(){

        ClientDetail clientDetails = givenClientDetails();
        Address address = givenAddress();
        IdentityDocument identityDocument = givenIdentityDocument();
        Contact contact = givenContact();

        clientDetails.setAddress(address);
        clientDetails.setContact(contact);
        clientDetails.setIdentityDocument(identityDocument);
        clientDetailService.create(clientDetails);

        verify(clientDetailRepository, times(1))
                .save(argThat(details ->
                        clientDetails.getMaritalStatus().equals(MaritalStatus.DIVORCED)));

        verify(addressRepository, times(1))
                .save(argThat(a -> a.getTown().equals("Palermo")));

        verify(identityDocumentRepository,times(1))
                .save(argThat(a -> a.getIdNumber().equals(12345L)));

        verify(contactRepository, times(1))
                .save(argThat(a -> a.getMail().equals("mail_onboarding@gmail.com")));

        verify(contactRepository, times(1))
                .save(argThat(a -> a.getPhoneNumber().equals("3456-5678910")));
    }

    @Test
    void uniqueIdNumberShouldReturnTrue(){
        when(identityDocumentRepository.findByIdNumber(1234L)).thenReturn(Optional.empty());

        assertTrue(clientDetailService.checkIdNumber(1234L));
    }

    @Test
    void duplicateIdNumberShouldReturnFalse(){
        when(identityDocumentRepository.findByIdNumber(1234L))
                .thenReturn(Optional.ofNullable(givenIdentityDocument()));

        assertFalse(clientDetailService.checkIdNumber(1234L));
        verify(identityDocumentRepository,times(1)).findByIdNumber(1234L);
    }

    @Test
    void existentIdNumberShouldReturnClientDetail() throws EntityNotFoundException {
        when(identityDocumentRepository.findByIdNumber(1234L))
                .thenReturn(Optional.ofNullable(givenIdentityDocument()));

        assertEquals(givenIdentityDocument(),clientDetailService.getIdentityDocumentByIdNumber(1234L));
    }

    @Test
    void nonExistentIdNumberThrowsEntityNotFoundException() throws EntityNotFoundException{
        when(identityDocumentRepository.findByIdNumber(1234L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,() -> clientDetailService.getIdentityDocumentByIdNumber(1234L));
    }

    @Test
    void uniqueEmailShouldReturnFalse() {
        when(contactRepository.findByMail("mail_onboarding@gmail.com"))
                .thenReturn(Optional.ofNullable(givenContact()));

        assertFalse(clientDetailService.checkMail("mail_onboarding@gmail.com"));
        verify(contactRepository, times(1)).findByMail("mail_onboarding@gmail.com");
    }

    @Test
    void uniqueEmailShouldReturnTrue() {
        when(contactRepository.findByMail("mail_onboarding@gmail.com")).thenReturn(Optional.empty());

        assertTrue(clientDetailService.checkMail("mail_onboarding@gmail.com"));
        verify(contactRepository, times(1)).findByMail("mail_onboarding@gmail.com");
    }

    @Test
    void uniquePhoneNumberShouldReturnFalse() {
        when(contactRepository.findByPhoneNumber("3456-5678910"))
                .thenReturn(Optional.ofNullable(givenContact()));

        assertFalse(clientDetailService.checkPhoneNumber("3456-5678910"));
        verify(contactRepository, times(1)).findByPhoneNumber("3456-5678910");
    }

    @Test
    void uniquePhoneNumberShouldReturnTrue() {
        when(contactRepository.findByPhoneNumber("3456-5678910")).thenReturn(Optional.empty());

        assertTrue(clientDetailService.checkPhoneNumber("3456-5678910"));
        verify(contactRepository, times(1)).findByPhoneNumber("3456-5678910");
    }

    @Test
    void uniqueCuilNumberShouldReturnFalse() {
        when(clientDetailRepository.findByCuil("1122334455"))
                .thenReturn(Optional.ofNullable(givenClientDetails()));

        assertFalse(clientDetailService.checkCuilNumber("1122334455"));
        verify(clientDetailRepository, times(1)).findByCuil("1122334455");
    }

    @Test
    void uniqueCuilNumberShouldReturnTrue() {
        when(clientDetailRepository.findByCuil("1122334455")).thenReturn(Optional.empty());

        assertTrue(clientDetailService.checkCuilNumber("1122334455"));
        verify(clientDetailRepository, times(1)).findByCuil("1122334455");
    }

    @Test
    void getMaritalStatusEnumsReturnsList(){
        assertTrue(clientDetailService.getMaritalStatusStrings().size()>0);
    }

    @Test
    void getGenderTypeEnumsReturnsList(){
        assertTrue(clientDetailService.getGenderTypeStrings().size()>0);
    }

    @Test
    void getIdentityDocumentCopyEnumsReturnsList(){
        assertTrue(clientDetailService.getIdentityDocumentCopyTypeStrings().size()>0);
    }

    @Test
    void getJobTypesEnumsReturnsList(){
        assertTrue(clientDetailService.getJobTypeStrings().size()>0);
    }

}