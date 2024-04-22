package com.md.onboarding.JUtils;

import com.md.onboarding.model.dto.AddressRequest;
import com.md.onboarding.model.dto.ClientDetailRequest;
import com.md.onboarding.model.dto.ContactRequest;
import com.md.onboarding.model.dto.IdentityDocumentRequest;
import com.md.onboarding.model.entity.Address;
import com.md.onboarding.model.entity.ClientDetail;
import com.md.onboarding.model.entity.Contact;
import com.md.onboarding.model.entity.IdentityDocument;
import com.md.onboarding.model.enumerate.GenderType;
import com.md.onboarding.model.enumerate.IdentityDocumentCopyType;
import com.md.onboarding.model.enumerate.JobType;
import com.md.onboarding.model.enumerate.MaritalStatus;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Data {

    public static ClientDetail givenClientDetails() {
        return ClientDetail.builder()
                .firstName("Shiorin")
                .lastName("Novella")
                .maritalStatus(MaritalStatus.DIVORCED)
                .cuil("1122334455")
                .enabled(false)
                .isPep(false)
                .birthDate(LocalDate.of(1989, 2, 7))
                .build();
    }

    public static Address givenAddress() {
        return Address.builder()
                .province("Buenos Aires")
                .town("Palermo")
                .postalCode("B1706")
                .streetName("Niceto Vega")
                .streetNumber("4736")
                .floorNumber(1)
                .apartmentNumber(1)
                .build();
    }

    public static IdentityDocument givenIdentityDocument(){
        return IdentityDocument.builder().idNumber(12345L).tramitNumber(1234567L).genderType(GenderType.MALE).identityDocumentCopyType(IdentityDocumentCopyType.A).build();
    }

    public static Contact givenContact() {
        return Contact.builder()
                .mail("mail_onboarding@gmail.com")
                .phoneNumber("3456-5678910")
                .build();
    }

    public static ClientDetailRequest givenClientDetailRequest(){
        return new ClientDetailRequest("Shiori","Novella",
                LocalDate.of(1989, 2, 7), "MARRIED",
                "EMPLOYED","1122334455",true,true,givenAddressRequest(),givenIdRequest(),givenContactRequest());
    }

    public static AddressRequest givenAddressRequest(){
        return new AddressRequest("Buenos Aires","Palermo","B1706",
                "Niceto Vega", "4736",1,1);
    }

    public static AddressRequest givenEmptyAddressRequest(){
        return new AddressRequest("","","",
                " ", "",0,0);
    }

    public static IdentityDocumentRequest givenIdRequest(){
        return new IdentityDocumentRequest(12345L,12343545L,"MALE","A");
    }

    public static ContactRequest givenContactRequest() {
        return new ContactRequest("mail_onboarding@gmail.com", "3456-5678910");
    }

    public static List<String> getMaritalStatusList(){
        return Arrays.stream(MaritalStatus.values()).map(Enum::name).toList();
    }

    public static List<String> getJobTypeList(){
        return Arrays.stream(JobType.values()).map(Enum::name).toList();
    }

    public static List<String> getIdentityDocumentCopyList(){
        return Arrays.stream(IdentityDocumentCopyType.values()).map(Enum::name).toList();
    }

    public static List<String> getGenderTypeList(){
        return Arrays.stream(GenderType.values()).map(Enum::name).toList();
    }
}
