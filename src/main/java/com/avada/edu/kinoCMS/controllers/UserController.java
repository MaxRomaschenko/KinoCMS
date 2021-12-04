package com.avada.edu.kinoCMS.controllers;

import com.avada.edu.kinoCMS.model.PictureGallery;
import com.avada.edu.kinoCMS.model.Stock;
import com.avada.edu.kinoCMS.model.User;
import com.avada.edu.kinoCMS.repo.UserRepo;
import com.avada.edu.kinoCMS.servicies.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Log4j2
@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String user(
            @ModelAttribute("user") User user,
            @ModelAttribute("date") String date,
            Model model
    ) {
        model.addAttribute("userList",userService.findAll());
        return "UI/users";
    }


    @PostMapping("/add/admin")
    public String add(@ModelAttribute("user") User user,
                      @ModelAttribute("date") String date
                      ) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        user.setBirthdayDate(LocalDate.parse(date,dateTimeFormatter));
        user.setReg_date(LocalDate.now());

        userService.saveUser(user);
        return "redirect:/users/admin";
    }

    @GetMapping("/{id}/edit/admin")
    public String edit(Model model, @PathVariable("id") Long id){
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("date",userService.findById(id).getBirthdayDate());
        return "UI/users_edit";
    }

    @PostMapping("/{id}/edit/admin")
    public String update(@PathVariable("id") Long id,
                         @ModelAttribute("user") User user,
                         @ModelAttribute("date") String date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        User user_by_id = userService.findById(id);
        user.setBirthdayDate(LocalDate.parse(date,dateTimeFormatter));
        user.setReg_date(LocalDate.now());
        user.setId(user_by_id.getId());
        userService.saveUser(user);
        return "redirect:/users/admin";
    }

    @PostMapping("/delete/{id}/admin")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/users/admin";
    }
}
