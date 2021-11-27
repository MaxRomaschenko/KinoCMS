package com.avada.edu.kinoCMS.controllers;

import com.avada.edu.kinoCMS.model.Mailing;
import com.avada.edu.kinoCMS.servicies.MailingService;
import com.avada.edu.kinoCMS.servicies.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller()
@RequestMapping("/mailing")
public class MailingController {

    @Value("${upload.path}")
    private String uploadPath;
    private final UserService userService;
    private final MailingService mailingService;


    public MailingController(UserService userService, MailingService mailingService) {
        this.userService = userService;
        this.mailingService = mailingService;
    }

    private String file(MultipartFile multipartFile) throws IOException {

        File uploadDir = new File(uploadPath);

        if(!uploadDir.exists()){
            uploadDir.mkdir();
        }

        String uuidFile = UUID.randomUUID().toString();
        String resultFileName = uuidFile + "." + multipartFile.getOriginalFilename();
        multipartFile.transferTo(new File(uploadPath + "/" + resultFileName));

        return resultFileName;
    }

    @GetMapping("/admin")
    public String getMailing(Model model){
        return "UI/mailing";
    }

    @PostMapping("/add")
    public String addMail(@ModelAttribute("mailing") Mailing mailing,
                          @RequestParam("file_name") MultipartFile file_name
    ) throws IOException {

        if (!file_name.isEmpty()){
            mailing.setFile_name(file(file_name));
        }

        mailingService.save(mailing);
        return "redirect:/mailing/admin";
    }
}
