package com.codesync.uniticket.services;

import com.codesync.uniticket.entities.StatusEntity;
import com.codesync.uniticket.repositories.StatusRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusService {
    @Autowired
    StatusRepository statusRepository;

    @PreAuthorize("hasAuthority('ADMIN')")
    public StatusEntity saveStatus(StatusEntity status) { return statusRepository.save(status); }

    public List<StatusEntity> getStatuses() { return statusRepository.findAll(); }

    public Optional<StatusEntity> getStatusById(Long id) { return statusRepository.findById(id); }

    @PreAuthorize("hasAuthority('ADMIN')")
    public StatusEntity updateStatus(StatusEntity status) {
        StatusEntity existingStatus = statusRepository.findById(status.getId())
                .orElseThrow(() -> new EntityNotFoundException("Status with id " + status.getId() + " does not exist."));

        if (status.getStatus() != null) {
            existingStatus.setStatus(status.getStatus());
        }

        return statusRepository.save(existingStatus);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public boolean deleteStatus(Long id) throws Exception {
        try {
            statusRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
