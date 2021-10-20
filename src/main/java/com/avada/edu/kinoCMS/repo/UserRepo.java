package com.avada.edu.kinoCMS.repo;

import com.avada.edu.kinoCMS.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends CrudRepository<User,Long> {
    List<User> findAll();
}
