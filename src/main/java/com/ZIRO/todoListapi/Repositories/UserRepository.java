package com.ZIRO.todoListapi.Repositories;

import com.ZIRO.todoListapi.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findOneByUserId(Long userId);
}
