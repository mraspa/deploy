package com.md.onboarding.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String province;

    private String town;

    @Column(name="postal_code")
    private String postalCode;

    @Column(name="street_name")
    private String streetName;

    @Column(name="street_number")
    private String streetNumber;

    @Column(name="floor_number")
    private Integer floorNumber;

    @Column(name="apartment_number")
    private Integer apartmentNumber;

    @OneToMany(mappedBy="address", cascade = CascadeType.ALL)
    private List<ClientDetail> clientDetails;

}
