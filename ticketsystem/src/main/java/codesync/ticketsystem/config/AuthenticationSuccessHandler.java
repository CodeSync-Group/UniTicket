package codesync.ticketsystem.config;

import codesync.ticketsystem.auth.AuthResponse;
import codesync.ticketsystem.auth.AuthService;
import codesync.ticketsystem.auth.RegisterRequest;
import codesync.ticketsystem.entities.ProfileEntity;
import codesync.ticketsystem.entities.UserEntity;
import codesync.ticketsystem.jwt.JwtService;
import codesync.ticketsystem.services.ProfileService;
import codesync.ticketsystem.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.IOException;
import java.text.Normalizer;
import java.util.Map;

@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private UserService userService;
    @Autowired
    @Lazy
    private AuthService authService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        System.out.println(authentication.getPrincipal());

        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oauthUser.getAttributes();

        String userEmail = (String) attributes.get("email");

        //if some user already has a profile
        if (profileService.getProfileByEmail(userEmail).isPresent()) {
            ProfileEntity userProfile = profileService.getProfileByEmail(userEmail)
                    .orElseThrow(() -> new EntityNotFoundException("User profile with email " + userEmail + " does not exist."));

            UserEntity userEntity = userService.getUserByProfileId(userProfile.getId())
                    .orElseThrow(() -> new EntityNotFoundException("User with profile id " + userProfile.getId() + " does not exist."));

            String token = jwtService.getToken(userEntity);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(token);

            response.getWriter().write(jsonResponse);
            response.getWriter().flush();

            //System.out.println("Handler token: " + token);

            response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        } else {
            String name = (String) attributes.get("name");

            String[] nameWords = name.split(" ");

            String firstname = null, lastname = null, secondSurname = null;

            if (nameWords[0] != null) {
                firstname = normalize(nameWords[0]);
            }
            if (nameWords.length > 1 && nameWords[1] != null) {
                lastname = normalize(nameWords[1]);
            }
            if (nameWords.length > 2 && nameWords[2] != null) {
                secondSurname = normalize(nameWords[2]);
            }

            String password = RandomStringUtils.randomAlphanumeric(8).toLowerCase();

            String baseUsername = (firstname + "." + lastname).toLowerCase();

            if (baseUsername.length() > 20) {
                baseUsername = baseUsername.substring(0, 20);
            }

            String username = baseUsername;

            while (userService.userExistByUsername(username)) {
                username = username + (int) (Math.random() * 1000);
            }

            RegisterRequest registerRequest = RegisterRequest.builder()
                    .firstname(firstname)
                    .lastname(lastname)
                    .secondSurname(secondSurname)
                    .username(username)
                    .password(password)
                    .email(userEmail)
                    .build();

            System.out.println(registerRequest);

            AuthResponse authResponse = authService.register(registerRequest);
            String token = authResponse.getToken();

            if (token != null) {
                String finalUsername = username;

                UserEntity userEntity = userService.getUserByUsername(username)
                        .orElseThrow(() -> new EntityNotFoundException("User with username " + finalUsername + " does not exist."));

                ProfileEntity profileEntity = profileService.getProfileById(userEntity.getProfile().getId())
                        .orElseThrow(() -> new EntityNotFoundException("User profile with id " + userEntity.getProfile().getId() + " does not exist."));

                String pictureUrl = (String) attributes.get("picture");

                byte[] picture = null;

                try {
                    picture = profileService.handleImageDownload(pictureUrl);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                if (picture != null) {
                    profileEntity.setPicture(picture);
                    profileService.updateProfile(profileEntity);
                }
            }

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(token);

            response.getWriter().write(jsonResponse);
            response.getWriter().flush();

            System.out.println("Handler token: " + token);

            response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }

    private static String normalize(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
}
