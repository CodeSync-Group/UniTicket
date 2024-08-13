package com.codesync.uniticket.auth;

import com.codesync.uniticket.entities.ConfigurationEntity;
import com.codesync.uniticket.entities.ProfileEntity;
import com.codesync.uniticket.entities.UserEntity;
import com.codesync.uniticket.entities.RoleEntity;
import com.codesync.uniticket.jwt.JwtService;
import com.codesync.uniticket.repositories.ConfigurationRepository;
import com.codesync.uniticket.repositories.ProfileRepository;
import com.codesync.uniticket.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.regex.*;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final ConfigurationRepository configurationRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse register(RegisterRequest request) throws Exception {
        if (isAdmittedUsername(request.getUsername())) {
            if (isAdmittedPassword(request.getPassword())) {
                ConfigurationEntity configuration = ConfigurationEntity.builder()
                        .mail_notifications(true)
                        .push_notifications(true)
                        .build();

                ConfigurationEntity savedConfiguration = configurationRepository.save(configuration);

                ProfileEntity profile = ProfileEntity.builder()
                        .firstname(request.getFirstname())
                        .lastname(request.getLastname())
                        .secondSurname(request.getSecondSurname())
                        .email(request.getEmail())
                        .build();

                ProfileEntity savedProfile = profileRepository.save(profile);

                UserEntity user = UserEntity.builder()
                        .username(request.getUsername())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role(RoleEntity.EXTERNAL)
                        .profile(savedProfile)
                        .configuration(savedConfiguration)
                        .build();

                userRepository.save(user);

                return AuthResponse.builder()
                        .token(jwtService.getToken(user))
                        .build();
            } else {
                throw new Exception("Try with another password");
            }
        } else {
            throw new Exception("Try with another username");
        }
    }

    private boolean isAdmittedUsername(String username) {
        String regex = "^[a-zA-Z0-9.]+$";

        Pattern p = Pattern.compile(regex);

        if (username == null || username.equals("admin")) {
            return false;
        }

        Matcher m = p.matcher(username);

        return m.matches();
    }

    private boolean isAdmittedPassword(String password) {
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=.,])"
                + "(?=\\S+$).{8,20}$";

        Pattern p = Pattern.compile(regex);

        if (password == null) {
            return false;
        }

        Matcher m = p.matcher(password);

        return m.matches();
    }
}