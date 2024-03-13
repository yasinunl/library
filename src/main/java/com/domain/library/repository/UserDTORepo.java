package com.domain.library.repository;

import com.domain.library.dto.UserDTO;
import com.domain.library.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDTORepo extends JpaRepository<UserDTO, Integer> {
    Optional<UserDTO> findByEmail(String email);
}
