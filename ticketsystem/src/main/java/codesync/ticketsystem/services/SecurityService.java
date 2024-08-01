package codesync.ticketsystem.services;

import codesync.ticketsystem.auth.AuthResponse;
import codesync.ticketsystem.dtos.PasswordChangeRequest;
import codesync.ticketsystem.entities.ProfileEntity;
import codesync.ticketsystem.entities.UserEntity;
import codesync.ticketsystem.jwt.JwtService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SecurityService {
    @Autowired
    MailService mailService;
    @Autowired
    ProfileService profileService;
    @Autowired
    UserService userService;
    @Autowired
    JwtService jwtService;

    @Value("${APP_URL}")
    private String APP_URL;

    @Value("${PORT}")
    private String PORT;

    public String passwordReset(String input) throws Exception {
        if (input.contains("@")) {
            ProfileEntity userProfile = profileService.getProfileByEmail(input)
                    .orElseThrow(() -> new EntityNotFoundException("User profile with email " + input + " does not exist."));

            UserEntity userEntity = userService.getUserByProfileId(userProfile.getId())
                    .orElseThrow(() -> new EntityNotFoundException("User with profile id " + userProfile.getId() + " does not exist."));

            String token = jwtService.getTokenForResetPassword(userEntity);

            String url = createUrl(APP_URL + PORT, token, input);

            mailService.resetPasswordMail(userProfile.getEmail(), userProfile.getFirstname(), userProfile.getLastname(), url);
        } else {
            UserEntity userEntity = userService.getUserByUsername(input)
                    .orElseThrow(() -> new EntityNotFoundException("User with username " + input + " does not exist."));

            ProfileEntity userProfile = profileService.getProfileById(userEntity.getProfile().getId())
                    .orElseThrow(() -> new EntityNotFoundException("User profile with id " + userEntity.getProfile().getId() + " does not exist."));

            String token = jwtService.getTokenForResetPassword(userEntity);

            String url = createUrl(APP_URL + PORT, token, userProfile.getEmail());

            mailService.resetPasswordMail(userProfile.getEmail(), userProfile.getFirstname(), userProfile.getLastname(), url);
        }

        return "Mail sent successfully";
    }

    public AuthResponse passwordChange(String token, String email, PasswordChangeRequest request) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        ProfileEntity existingProfile = profileService.getProfileByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Profile with email " + email + " does not exist."));

        UserEntity existingUser = userService.getUserByProfileId (existingProfile.getId())
                .orElseThrow(() -> new EntityNotFoundException("User with profile id " + existingProfile.getId() + " does not exist."));

        if (token == null || !jwtService.isTokenValid(token, existingUser)) {
            throw new SecurityException("User is not authenticated");
        }

        String currentUsername = jwtService.getUsernameFromToken(token);
        boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));

        if (!currentUsername.equals(existingUser.getUsername()) && !isAdmin) {
            throw new SecurityException("Not authorized to change this password");
        }

        if (!Objects.equals(request.getNewPassword(), request.getNewPasswordRewritten())) {
            throw new Exception("Password mismatch");
        }

        existingUser.setPassword(request.getNewPassword());

        UserEntity user = userService.updatePassword(existingUser);

        mailService.passwordChangedMail(existingProfile.getEmail(), existingProfile.getFirstname(), existingProfile.getLastname());

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }

    public String createUrl(String domain, String token, String email) {
        return domain + "/security/changePassword?token=" + token + "&email=" + email;
    }
}
