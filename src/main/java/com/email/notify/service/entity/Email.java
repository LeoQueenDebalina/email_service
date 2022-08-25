package com.email.notify.service.entity;

import com.email.notify.service.model.ContentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Email_INFO")
@Builder
public class Email {
    @Id
    @Column(name="clientId")
    private String clientId;
    @Column(name="address")
    private String address;
    @Column(name="emailFrom")
    private String emailFrom;
    @Column(name="emailTo")
    private String emailTo;
    @Column(name="body")
    private String body;
    @Column(name="contentType")
    private ContentType contentType;
    @Column(name="cc")
    private String cc;
    @Column(name="bcc")
    private  String bcc;
}
