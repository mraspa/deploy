package com.md.login.msClient;

import com.md.login.model.dto.EmailDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;


@FeignClient(name = "mail-sender", url = "52.91.213.17:8080/mail-sender/v1")
public interface MailSenderClient {
    @PostMapping("/sendSimpleMessage")
    Map<String,String> emailSender(@RequestBody EmailDto emailDto);
}


