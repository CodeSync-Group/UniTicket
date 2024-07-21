package codesync.ticketsystem.controllers;

import codesync.ticketsystem.entities.FacultyEntity;
import codesync.ticketsystem.services.FacultyService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/faculties")
public class FacultyController {
    @Autowired
    FacultyService facultyService;

    @Operation(summary = "Get faculties")
    @GetMapping("/")
    public ResponseEntity<List<FacultyEntity>> getFaculties() {
        List<FacultyEntity> faculties = facultyService.getFaculties();
        return ResponseEntity.ok(faculties);
    }
}
