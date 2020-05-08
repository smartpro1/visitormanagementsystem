package com.visitormanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.visitormanagement.models.TagManager;

@Repository
public interface TagManagerRepository extends JpaRepository<TagManager, Integer>{

	TagManager getById(int i);

}
