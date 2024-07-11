package codesync.ticketsystem.controllers;

import codesync.ticketsystem.entities.DepartmentEntity;
import codesync.ticketsystem.entities.FacultyEntity;
import codesync.ticketsystem.entities.UserEntity;
import codesync.ticketsystem.services.DepartmentService;
import codesync.ticketsystem.services.FacultyService;
import codesync.ticketsystem.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserService userService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    FacultyService facultyService;

    @Operation(summary = "Update a user role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated correctly",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content) })
    @PutMapping("/userRole")
    public ResponseEntity<UserEntity> updateUserRole(@RequestBody UserEntity user) throws Exception {
        UserEntity newUser = userService.updateUserRole(user);
        return  ResponseEntity.ok(newUser);
    }

    @Operation(summary = "Update a department")
    @PutMapping("/departments")
    public ResponseEntity<DepartmentEntity> updateDepartment(@RequestBody DepartmentEntity department)  {
        DepartmentEntity updatedDepartment = departmentService.updateDepartment(department);
        return ResponseEntity.ok(updatedDepartment);
    }

    @Operation(summary = "Save a department")
    @PostMapping("/departments")
    public ResponseEntity<DepartmentEntity> saveDepartment(@RequestBody DepartmentEntity department) {
        DepartmentEntity newDepartment = departmentService.saveDepartment(department);
        return ResponseEntity.ok(newDepartment);
    }

    @Operation(summary = "Delete a department")
    @DeleteMapping("/departments/{id}")
    public ResponseEntity<Boolean> deleteDepartment(@PathVariable Long id) throws Exception {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Save a faculty")
    @PostMapping("/faculties")
    public ResponseEntity<FacultyEntity> saveFaculty(@RequestBody FacultyEntity faculty) {
        FacultyEntity savedFaculty = facultyService.saveFaculty(faculty);
        return ResponseEntity.ok(savedFaculty);
    }

    @Operation(summary = "Update a faculty")
    @PutMapping("/faculties")
    public ResponseEntity<FacultyEntity> updateFaculty(@RequestBody FacultyEntity faculty)  {
        FacultyEntity updatedFaculty = facultyService.updateFaculty(faculty);
        return ResponseEntity.ok(updatedFaculty);
    }

    @Operation(summary = "Delete a faculty")
    @DeleteMapping("/faculties/{id}")
    public ResponseEntity<Boolean> deleteFaculty(@PathVariable Long id) throws Exception {
        facultyService.deleteFaculty(id);
        return ResponseEntity.noContent().build();
    }
}
