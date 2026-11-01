package com.zjgsu.chr.user.repository;

import com.zjgsu.chr.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
