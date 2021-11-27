package com.avada.edu.kinoCMS.servicies;

import com.avada.edu.kinoCMS.model.User;
import com.avada.edu.kinoCMS.repo.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User findById(Long id){
        return userRepo.getOne(id);
    }

    public List<User> findAll(){
        return userRepo.findAll();
    }

    public User saveUser(User user){
        return userRepo.save(user);
    }
    public void deleteById(Long id){
        userRepo.deleteById(id);
    }
}
