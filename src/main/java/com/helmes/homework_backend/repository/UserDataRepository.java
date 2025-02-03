package com.helmes.homework_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.helmes.homework_backend.entity.UserData;

public interface UserDataRepository extends JpaRepository<UserData, Long> {
}
