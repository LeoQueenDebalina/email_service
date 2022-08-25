package com.email.notify.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageRequest {

    @NotBlank(message = "Field cannot be null")
    @Email(message = "invalid Email format")
    private String emailFrom;
    @NotBlank(message = "Field cannot be null")
    @Email(message = "invalid Email format")
    private String emailTo;
    @NotBlank(message = "body cannot be null")
    private String body;
    private String subject;
    private ContentType contentType;

}
