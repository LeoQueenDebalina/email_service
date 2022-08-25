package com.email.notify.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class MailRequest {
    @NotBlank(message = "address cannot be blank")
    private String address;
    private String attachmentUrl;
    @Valid
    private MessageRequest messageRequest;
    private String[] cc;
    private String[] bcc;
}
