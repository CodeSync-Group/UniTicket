package com.codesync.uniticket.services;

import com.codesync.uniticket.entities.DepartmentEntity;
import com.codesync.uniticket.repositories.DepartmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public DepartmentEntity saveDepartment(DepartmentEntity department) { return departmentRepository.save(department); }

    public List<DepartmentEntity> getDepartments() {
        return departmentRepository.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public DepartmentEntity updateDepartment(DepartmentEntity department) {
        DepartmentEntity existingDepartment = departmentRepository.findById(department.getId())
                .orElseThrow(() -> new EntityNotFoundException("Department with id " + department.getId() + " does not exist."));

        if (department.getDepartment() != null){
            existingDepartment.setDepartment(department.getDepartment());
        }
        return departmentRepository.save(existingDepartment);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public boolean deleteDepartment(Long id) throws Exception {
        try {
            departmentRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}




