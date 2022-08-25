package com.email.notify.service;

import com.email.notify.service.entity.Email;
import com.email.notify.service.model.ContentType;
import com.email.notify.service.model.MailRequest;
import com.email.notify.service.model.MailResponse;
import com.email.notify.service.model.MessageRequest;
import com.email.notify.service.repository.MailRepository;
import com.email.notify.service.service.MailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class EmailServiceApplicationTests {

	@Test
	void contextLoads() {
	}
	@Autowired
	private MailRepository mailRepository;
	@Autowired
	private MailService mailService;

	@Test
	void myRepositoryTestFindById(){
		List<Email> expectedResult = mailRepository.findAll();
	   String id = "dianadebalina18@gmail.com";
		assertEquals(expectedResult, this.mailRepository.getDetailByEmail("dianadebalina18@gmail.com"));
	}
	@Test
	void myServicsTestFindById(){
		List<Email> expectedResult = mailRepository.findAll();
	   String id = "dianadebalina18@gmail.com";
		assertEquals(expectedResult, this.mailService.getDetailsById("dianadebalina18@gmail.com"));
	}
	@Test
	void sendMailTest() throws Exception {
		MailResponse expectedResult = new MailResponse(false, "Mail Send");
		MailRequest mailRequest = new MailRequest("Kolkata","/home/cbnits-29/Downloads/images.png",new MessageRequest("dianadebalina18@gmail.com","gopinathbhowmick425@gmail.com","<h1>Hi There This is a Test Email Body</h1>","Spring Boot", ContentType.HTML), new String[]{"ayashasiddika00@gmail.com"},new String[]{"gs624874@gmail.com"});
		assertEquals(expectedResult, this.mailService.sendEmail(mailRequest));
	}
}
