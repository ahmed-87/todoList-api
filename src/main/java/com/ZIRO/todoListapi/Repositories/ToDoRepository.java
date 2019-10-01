package com.ZIRO.todoListapi.Repositories;

import com.ZIRO.todoListapi.Entities.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Long> {
    public List<ToDo> findAllByCreatedByUserId(long userId);
}
