package com.md.onboarding.model.entity;

import com.md.onboarding.model.enumerate.GenderType;
import com.md.onboarding.model.enumerate.IdentityDocumentCopyType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table (name = "identity_documents")
public class IdentityDocument {

    @Id
    @Column(name = "id_number")
    private Long idNumber;

    @Column(name = "tramit_number")
    private Long tramitNumber;

    @Column(name = "id_copy_type")
    @Enumerated(EnumType.STRING)
    private IdentityDocumentCopyType identityDocumentCopyType;

    @Column(name = "gender_type")
    @Enumerated(EnumType.STRING)
    private GenderType genderType;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        IdentityDocument that = (IdentityDocument) object;
        return Objects.equals(idNumber, that.idNumber) && Objects.equals(tramitNumber, that.tramitNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idNumber, tramitNumber);
    }
}
