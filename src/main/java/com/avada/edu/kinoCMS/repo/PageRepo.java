package com.avada.edu.kinoCMS.repo;

import com.avada.edu.kinoCMS.model.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PageRepo extends JpaRepository<Page,Long> {
    List<Page> findAll();
}
