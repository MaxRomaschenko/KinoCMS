package com.avada.edu.kinoCMS.repo;

import com.avada.edu.kinoCMS.model.Hall;
import com.avada.edu.kinoCMS.model.Mailing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MailingRepo extends JpaRepository<Mailing,Long> {
    List<Mailing> findAll();
}
