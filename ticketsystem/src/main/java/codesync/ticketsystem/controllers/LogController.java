package codesync.ticketsystem.controllers;

import codesync.ticketsystem.entities.LogEntity;
import codesync.ticketsystem.services.LogService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/logs")
public class LogController {
    @Autowired
    LogService logService;

    @Operation(summary = "Get logs")
    @GetMapping("/")
    public ResponseEntity<List<LogEntity>> getLogs() {
        List<LogEntity> logs = logService.getLogs();
        return ResponseEntity.ok(logs);
    }
}
