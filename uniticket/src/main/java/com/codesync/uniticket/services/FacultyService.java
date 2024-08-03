package com.codesync.uniticket.services;

import com.codesync.uniticket.entities.FacultyEntity;
import com.codesync.uniticket.repositories.FacultyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyService {
    @Autowired
    FacultyRepository facultyRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public FacultyEntity saveFaculty(FacultyEntity faculty) {
        return facultyRepository.save(faculty);
    }

    public List<FacultyEntity> getFaculties() { return facultyRepository.findAll();}

    @PreAuthorize("hasRole('ADMIN')")
    public FacultyEntity updateFaculty(FacultyEntity faculty) {
        FacultyEntity existingFaculty = facultyRepository.findById(faculty.getId())
                .orElseThrow(() -> new EntityNotFoundException("Faculty with id " + faculty.getId() + " does not exist."));

        if (faculty.getFaculty() != null){
            existingFaculty.setFaculty(faculty.getFaculty());
        }

        return facultyRepository.save(existingFaculty);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public boolean deleteFaculty(Long id) throws Exception {
        try {
            facultyRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
