package com.codesync.uniticket.services;

import com.codesync.uniticket.entities.RoleEntity;
import com.codesync.uniticket.entities.UserEntity;
import com.codesync.uniticket.jwt.JwtAuthenticationFilter;
import com.codesync.uniticket.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;

    private final PasswordEncoder passwordEncoder;

    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> getUserByProfileId(Long id) {
        return userRepository.findByProfileId(id);
    }

    public Optional<UserEntity> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<UserEntity> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Boolean userExistByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public UserEntity updateUser(UserEntity user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("User is not authenticated");
        }

        UserEntity existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("User with id " + user.getId() + " does not exist."));

        String currentUsername = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));

        if (!currentUsername.equals(existingUser.getUsername()) && !isAdmin) {
            throw new SecurityException("Not authorized to update this user");
        }

        if (user.getUsername() != null) {
            existingUser.setUsername(user.getUsername());
        }

        if (user.getUnit() != null) {
            existingUser.setUnit(user.getUnit());
        }

        return userRepository.save(existingUser);
    }

    public UserEntity updatePassword(UserEntity user) {
        UserEntity existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("User with id " + user.getId() + " does not exist."));

        if (user.getPassword() != null) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return userRepository.save(existingUser);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public UserEntity updateUserRole(UserEntity user) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);

        UserEntity existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("User with id " + user.getId() + " does not exist."));

            if (user.getRole() != null) {
                if (!Objects.equals(user.getRole().toString(), "ADMIN")) {
                    RoleEntity role = RoleEntity.fromString(user.getRole().toString());
                    existingUser.setRole(role);
                    return userRepository.save(existingUser);
                }
            }

        throw new Exception("Try a different role.");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public boolean deleteUser(Long id) throws Exception {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
