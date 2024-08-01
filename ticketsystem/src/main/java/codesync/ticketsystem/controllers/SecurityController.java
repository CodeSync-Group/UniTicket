package codesync.ticketsystem.controllers;

import codesync.ticketsystem.auth.AuthResponse;
import codesync.ticketsystem.dtos.PasswordChangeRequest;
import codesync.ticketsystem.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/security")
public class SecurityController {
    @Autowired
    SecurityService securityService;

    @PostMapping("/changePassword")
    public ResponseEntity<AuthResponse> changePassword(@RequestParam String token, @RequestParam String email, @RequestBody PasswordChangeRequest request) throws Exception {
        AuthResponse response = securityService.passwordChange(token, email, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestParam String input) throws Exception {
        String status = securityService.passwordReset(input);
        return ResponseEntity.ok(status);
    }
}
