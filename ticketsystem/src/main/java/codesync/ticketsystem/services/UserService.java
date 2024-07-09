package codesync.ticketsystem.services;

import codesync.ticketsystem.entities.RoleEntity;
import codesync.ticketsystem.entities.UserEntity;
import codesync.ticketsystem.jwt.JwtAuthenticationFilter;
import codesync.ticketsystem.jwt.JwtService;
import codesync.ticketsystem.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    public UserEntity updateUser(UserEntity user) {
        UserEntity existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("User with id " + user.getId() + " does not exist."));

        if (user.getPassword() != null) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        if (user.getUsername() != null) {
            existingUser.setUsername(user.getUsername());
        }

        if (user.getUnit() != null) {
            existingUser.setUnit(user.getUnit());
        }

        return userRepository.save(existingUser);
    }

    public UserEntity updateUserRole(UserEntity user) throws Exception {
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

    public boolean deleteUser(Long id) throws Exception {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
