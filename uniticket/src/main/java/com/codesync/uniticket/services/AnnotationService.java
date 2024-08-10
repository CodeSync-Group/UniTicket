package com.codesync.uniticket.services;

import com.codesync.uniticket.entities.AnnotationEntity;
import com.codesync.uniticket.entities.CategoryEntity;
import com.codesync.uniticket.repositories.AnnotationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnnotationService {
    @Autowired
    AnnotationRepository annotationRepository;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'HEADUNIT', 'HEADSHIP', 'ANALYST')")
    public AnnotationEntity saveAnnotation(AnnotationEntity annotation) { return annotationRepository.save(annotation); }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'HEADUNIT', 'HEADSHIP', 'ANALYST')")
    public List<AnnotationEntity> getAnnotations() { return annotationRepository.findAll();}

    @PreAuthorize("hasAnyAuthority('ADMIN', 'HEADUNIT', 'HEADSHIP', 'ANALYST')")
    public Optional<List<AnnotationEntity>> getAnnotationsByTicketId(Long id) { return annotationRepository.findByTicketId(id); }

    @PreAuthorize("hasAuthority('ADMIN')")
    public AnnotationEntity updateAnnotation(AnnotationEntity annotation) {
        AnnotationEntity existingAnnotation = annotationRepository.findById(annotation.getId())
                .orElseThrow(() -> new EntityNotFoundException("Annotation with id " + annotation.getId() + " does not exist."));

        if (annotation.getAnnotation() != null) {
            existingAnnotation.setAnnotation(annotation.getAnnotation());
        }

        if (annotation.getTicket() != null) {
            existingAnnotation.setTicket(annotation.getTicket());
        }

        return annotationRepository.save(existingAnnotation);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public boolean deleteAnnotation(Long id) throws Exception {
        try {
            annotationRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
