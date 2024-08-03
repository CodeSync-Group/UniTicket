package com.codesync.uniticket.controllers;

import com.codesync.uniticket.entities.*;
import com.codesync.uniticket.services.*;
import io.swagger.v3.oas.annotations.Operation;
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
    @Autowired
    TopicService topicService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    NotificationService notificationService;
    @Autowired
    StatusService statusService;
    @Autowired
    LogService logService;

    @Operation(summary = "Update a user role")
    @PutMapping("/userRole")
    public ResponseEntity<UserEntity> updateUserRole(@RequestBody UserEntity user) throws Exception {
        UserEntity newUser = userService.updateUserRole(user);
        return  ResponseEntity.ok(newUser);
    }

    @Operation(summary = "Save a department")
    @PostMapping("/departments")
    public ResponseEntity<DepartmentEntity> saveDepartment(@RequestBody DepartmentEntity department) {
        DepartmentEntity newDepartment = departmentService.saveDepartment(department);
        return ResponseEntity.ok(newDepartment);
    }

    @Operation(summary = "Update a department")
    @PutMapping("/departments")
    public ResponseEntity<DepartmentEntity> updateDepartment(@RequestBody DepartmentEntity department)  {
        DepartmentEntity updatedDepartment = departmentService.updateDepartment(department);
        return ResponseEntity.ok(updatedDepartment);
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

    @Operation(summary = "Save a topic")
    @PostMapping("/topics")
    public ResponseEntity<TopicEntity> saveTopic(@RequestBody TopicEntity topic) {
        TopicEntity savedTopic = topicService.saveTopic(topic);
        return ResponseEntity.ok(savedTopic);
    }

    @Operation(summary = "Update a topic")
    @PutMapping("/topics")
    public ResponseEntity<TopicEntity> updateTopic(@RequestBody TopicEntity topic)  {
        TopicEntity updatedTopic = topicService.updateTopic(topic);
        return ResponseEntity.ok(updatedTopic);
    }

    @Operation(summary = "Delete a topic")
    @DeleteMapping("/topics/{id}")
    public ResponseEntity<Boolean> deleteTopic(@PathVariable Long id) throws Exception {
        topicService.deleteTopic(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update a category")
    @PutMapping("/categories")
    public ResponseEntity<CategoryEntity> updateCategory(@RequestBody CategoryEntity category)  {
        CategoryEntity updatedCategory = categoryService.updateCategory(category);
        return ResponseEntity.ok(updatedCategory);
    }

    @Operation(summary = "Delete a category")
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Boolean> deleteCategory(@PathVariable Long id) throws Exception {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update a notification")
    @PutMapping("/notifications")
    public ResponseEntity<NotificationEntity> updateNotification(@RequestBody NotificationEntity notification)  {
        NotificationEntity updatedNotification = notificationService.updateNotification(notification);
        return ResponseEntity.ok(updatedNotification);
    }

    @Operation(summary = "Delete a notification")
    @DeleteMapping("/notifications/{id}")
    public ResponseEntity<Boolean> deleteNotification(@PathVariable Long id) throws Exception {
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Save a status")
    @PostMapping("/statuses")
    public ResponseEntity<StatusEntity> saveStatus(@RequestBody StatusEntity status) {
        StatusEntity savedStatus = statusService.saveStatus(status);
        return ResponseEntity.ok(savedStatus);
    }

    @Operation(summary = "Update a status")
    @PutMapping("/statuses")
    public ResponseEntity<StatusEntity> updateStatus(@RequestBody StatusEntity status)  {
        StatusEntity updatedStatus = statusService.updateStatus(status);
        return ResponseEntity.ok(updatedStatus);
    }

    @Operation(summary = "Delete a status")
    @DeleteMapping("/statuses/{id}")
    public ResponseEntity<Boolean> deleteStatus(@PathVariable Long id) throws Exception {
        statusService.deleteStatus(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update a log")
    @PutMapping("/logs")
    public ResponseEntity<LogEntity> updateLog(@RequestBody LogEntity log)  {
        LogEntity updatedLog = logService.updateLog(log);
        return ResponseEntity.ok(updatedLog);
    }

    @Operation(summary = "Delete a log")
    @DeleteMapping("/logs/{id}")
    public ResponseEntity<Boolean> deleteLog(@PathVariable Long id) throws Exception {
        logService.deleteLog(id);
        return ResponseEntity.noContent().build();
    }
}
