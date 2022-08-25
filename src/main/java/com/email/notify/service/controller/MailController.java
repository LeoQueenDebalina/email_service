package com.email.notify.service.controller;

import com.email.notify.service.entity.Email;
import com.email.notify.service.model.MailRequest;
import com.email.notify.service.model.MailResponse;
import com.email.notify.service.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/Api/v3")
public class MailController {
    @Autowired
    private MailService mailService;

    @PostMapping("/sendEmail")
    public MailResponse sendEmail(@Valid @RequestBody MailRequest mailRequest) throws Exception {
        return this.mailService.sendEmail(mailRequest);
    }

    @GetMapping("/getDetails/{emailFrom}")
    public List<Email> getDetailsById(@PathVariable String emailFrom) {
        return this.mailService.getDetailsById(emailFrom);
    }
}
