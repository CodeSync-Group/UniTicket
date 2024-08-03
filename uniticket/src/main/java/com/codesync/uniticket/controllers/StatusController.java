package com.codesync.uniticket.controllers;

import com.codesync.uniticket.entities.StatusEntity;
import com.codesync.uniticket.services.StatusService;
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
@RequestMapping("/api/statuses")
public class StatusController {
    @Autowired
    StatusService statusService;

    @Operation(summary = "Get statuses")
    @GetMapping("/")
    public ResponseEntity<List<StatusEntity>> getStatuses() {
        List<StatusEntity> statuses = statusService.getStatuses();
        return ResponseEntity.ok(statuses);
    }
}
