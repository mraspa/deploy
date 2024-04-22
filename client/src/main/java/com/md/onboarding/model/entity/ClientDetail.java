package com.md.onboarding.model.entity;

import com.md.onboarding.model.enumerate.JobType;
import com.md.onboarding.model.enumerate.MaritalStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="client_details")
public class ClientDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="birth_date")
    private LocalDate birthDate;

    @Column(name="marital_status")
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;

    @Column(name="job_type")
    @Enumerated(EnumType.STRING)
    private JobType jobType;

    private String cuil;

    private boolean enabled;

    @Column(name="is_pep")
    private boolean isPep;

    @ManyToOne
    @JoinColumn(name="address_id", nullable=false)
    private Address address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "identity_document_number")
    private IdentityDocument identityDocument;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_id")
    private Contact contact;

}
