package com.md.onboarding.msClient;

import com.md.onboarding.model.dto.MailResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "mail-sender", url = "18.205.115.104:8080/mail-sender/v1")
public interface MailSenderClient {

    @PostMapping("/sendSimpleMessage")
    Map<String,String> mailSender(@RequestBody MailResponse mailResponse);

}