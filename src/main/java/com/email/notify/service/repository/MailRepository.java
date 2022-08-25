package com.email.notify.service.repository;

import com.email.notify.service.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MailRepository extends JpaRepository<Email,String> {
    @Query("select u from Email u where u.emailFrom = ?1")
    public List<Email> getDetailByEmail(String id);
}
