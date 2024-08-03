package com.codesync.uniticket.controllers;

import com.codesync.uniticket.entities.DepartmentEntity;
import com.codesync.uniticket.services.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/departments")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

   @Operation(summary = "Get departments")
   @GetMapping("/")
   public ResponseEntity<List<DepartmentEntity>> getDepartments() {
       List<DepartmentEntity> departments = departmentService.getDepartments();
       return ResponseEntity.ok(departments);
   }
}
