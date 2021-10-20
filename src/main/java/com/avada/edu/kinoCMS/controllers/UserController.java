package com.avada.edu.kinoCMS.controllers;

import com.avada.edu.kinoCMS.model.User;
import com.avada.edu.kinoCMS.repo.UserRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Log4j2
@Controller
public class UserController {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/user")
    public String user(
            @RequestParam(name = "name",required = false,defaultValue = "world")
            String name,
            Map<String,Object> model
    ) {
        model.put("name",name);
        return "main";
    }

    @GetMapping("/")
    public String main(Map<String,Object> model){
        Iterable<User> users = userRepo.findAll();
        model.put("users",users);
        return "index";
    }

    @PostMapping("/")
    public String add(@RequestParam String name,
                      @RequestParam String secondName,
                      Map<String,Object> model,
                      @RequestParam("file")MultipartFile file
    ) throws IOException {
        User user = new User(name,secondName);

        userRepo.save(user);

        Iterable<User> users = userRepo.findAll();
        model.put("users",users);
        return "main";
    }
}
