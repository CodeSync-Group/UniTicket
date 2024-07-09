package codesync.ticketsystem.services;

import codesync.ticketsystem.entities.UserEntity;
import codesync.ticketsystem.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    public UserEntity updateUser(UserEntity user) {
        UserEntity existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("User with id " + user.getId() + "does not exist."));

        if (user.getPassword() != null) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        if (user.getUsername() != null) {
            existingUser.setUsername(user.getUsername());
        }

        if (user.getProfile() != null) {
            existingUser.setProfile(user.getProfile());
        }

        if (user.getUnit() != null) {
            existingUser.setUnit(user.getUnit());
        }

        if (user.getRole() != null) {
            existingUser.setRole(user.getRole());
        }

        return userRepository.save(existingUser);
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
