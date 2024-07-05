package codesync.ticketsystem.Auth;

import codesync.ticketsystem.entities.ProfileEntity;
import codesync.ticketsystem.entities.UserEntity;
import codesync.ticketsystem.entities.RoleEntity;
import codesync.ticketsystem.jwt.JwtService;
import codesync.ticketsystem.repositories.ProfileRepository;
import codesync.ticketsystem.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
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

    public AuthResponse register(RegisterRequest request) {
        UserEntity user = UserEntity.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(RoleEntity.EXTERNAL)
                .build();

        userRepository.save(user);

        ProfileEntity profile = ProfileEntity.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .secondSurname(request.getSecondSurname())
                .email(request.getEmail())
                .build();

        profileRepository.save(profile);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }
}