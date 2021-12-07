package com.avada.edu.kinoCMS.controllers;

import com.avada.edu.kinoCMS.model.Film;
import com.avada.edu.kinoCMS.model.Mailing;
import com.avada.edu.kinoCMS.model.PictureGallery;
import com.avada.edu.kinoCMS.model.User;
import com.avada.edu.kinoCMS.servicies.MailingService;
import com.avada.edu.kinoCMS.servicies.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.criteria.From;
import java.io.File;
import java.io.IOException;
import java.util.List;
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

        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }


        String resultFileName = multipartFile.getOriginalFilename();
        multipartFile.transferTo(new File(uploadPath + "/" + resultFileName));

        return resultFileName;
    }

    @GetMapping("/admin")
    public String getMailing(@RequestParam(name = "users", defaultValue = "0") List<Long> users,
                             @ModelAttribute(name = "sms") String sms_message,
                             @ModelAttribute("mailing") Mailing mailing,
                             Model model) {
        if (!users.isEmpty()) {
            model.addAttribute("users", users);
        }
        model.addAttribute("html_files", mailingService.findTop5OrderById());
        return "UI/mailing";
    }

    @PostMapping("/add")
    public String addMail(@ModelAttribute("mailing") Mailing mailing,
                          @RequestParam("file_name") MultipartFile file_name
    ) throws IOException {

        if (!file_name.isEmpty()) {
            mailing.setHtml_file(file(file_name));
        }

        mailingService.save(mailing);
        return "redirect:/mailing/admin";
    }

    @GetMapping("/choose_user/admin")
    public String user(
            @ModelAttribute("user") User user,
            @ModelAttribute("date") String date,
            Model model
    ) {
        model.addAttribute("userList", userService.findAll());
        return "UI/chooseUser";
    }

    @PostMapping("/add/choose_user/admin")
    public String getUsers(
            @ModelAttribute("user") User user,
            @ModelAttribute("date") String date,
            @RequestParam("users") List<Long> users_id,
            RedirectAttributes redirectAttributes) {

        redirectAttributes.addAttribute("users", users_id);
        return "redirect:/mailing/admin";
    }

    @PostMapping("/sms")
    private String makeSmsMailing(
            @ModelAttribute("sms") String sms_message,
            @RequestParam("users") List<Long> users_id,
            @ModelAttribute(name = "radio") Boolean radio) {

        if (radio) {
            List<User> userList_from_db = userService.findAll();
            for (User user : userList_from_db) {
                System.out.println("Пользователь " + user.getName() + " Получил смс: " + sms_message);
            }
        } else if (!radio) {
            if (!users_id.isEmpty()) {
                for (Long user : users_id) {
                    if (user != 0) {
                        User user_from_db = userService.findById(user);
                        System.out.println("Пользователь " + user_from_db.getName() + " Получил смс: " + sms_message);
                    }
                }
            }
        }

        return "redirect:/mailing/admin";
    }

    @PostMapping("/email")
    private String makeEmailMailing(@RequestParam("file") MultipartFile multipartFile,
                                    @ModelAttribute("mailing") Mailing mailing,
                                    @RequestParam(name = "users") List<Long> users_id,
                                    @ModelAttribute(name = "radio") Boolean radio,
                                    @RequestParam(name = "htmlF", defaultValue = "0") Long id) throws IOException {
        if (!multipartFile.isEmpty()) {
            mailing.setHtml_file(file(multipartFile));
            Mailing mailing_from_db = mailingService.save(mailing);
            if (radio) {
                List<User> userList_from_db = userService.findAll();
                for (User user : userList_from_db) {
                    System.out.println("Пользователь " + user.getName() + " Получил email: " + mailing_from_db.getHtml_file());
                }
            } else if (!radio) {
                if (!users_id.isEmpty()) {
                    for (Long user : users_id) {
                        if (user != 0) {
                            User user_from_db = userService.findById(user);
                            System.out.println("Пользователь " + user_from_db.getName() + " Получил email: " + mailing_from_db.getHtml_file());
                        }
                    }
                }
            }
        } else if (id != 0) {
            Mailing mailing_from_db = mailingService.findById(id);
            if (radio) {
                List<User> userList_from_db = userService.findAll();
                for (User user : userList_from_db) {
                    System.out.println("Пользователь " + user.getName() + " Получил email: " + mailing_from_db.getHtml_file());
                }
            } else if (!radio) {
                if (!users_id.isEmpty()) {
                    for (Long user : users_id) {
                        if (user != 0) {
                            User user_from_db = userService.findById(user);
                            System.out.println("Пользователь " + user_from_db.getName() + " Получил email: " + mailing_from_db.getHtml_file());
                        }
                    }
                }
            }

        }

        return "redirect:/mailing/admin";
    }

    @PostMapping("/email/delete/{id}")
    public String deleteMailing(@PathVariable("id") Long id) {
        mailingService.deleteById(id);
        return "redirect:/mailing/admin";
    }


}
