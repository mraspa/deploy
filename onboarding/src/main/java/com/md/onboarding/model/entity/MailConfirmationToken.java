package com.md.onboarding.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mail_confirmation_tokens")
public class MailConfirmationToken {

    @Id
    @Column(name = "mail_client")
    private String mailClient;

    private int token;

    private int attempts;

    @Column(name = "time_stamp")
    private LocalDateTime timeStamp;

    @Column(columnDefinition = "BOOLEAN")
    private Boolean validated;

}

