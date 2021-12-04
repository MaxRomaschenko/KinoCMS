package com.avada.edu.kinoCMS.servicies;

import com.avada.edu.kinoCMS.model.Mailing;
import com.avada.edu.kinoCMS.repo.MailingRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailingService {

    private final MailingRepo mailingRepo;

    public MailingService(MailingRepo mailingRepo) {
        this.mailingRepo = mailingRepo;
    }

    public List<Mailing> findAll(){
        return mailingRepo.findAll();
    }

    public Mailing save(Mailing mailing){
        return mailingRepo.save(mailing);
    }

    public void deleteById(Long id){
        mailingRepo.deleteById(id);
    }
}
