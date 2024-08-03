package com.codesync.uniticket.services;

import com.codesync.uniticket.entities.UnitEntity;
import com.codesync.uniticket.repositories.UnitRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitService {
    @Autowired
    UnitRepository unitRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public UnitEntity saveUnit(UnitEntity unit) {
        return unitRepository.save(unit);
    }

    public List<UnitEntity> getUnits() {
        return unitRepository.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UnitEntity updateUnit(UnitEntity unit) {
        UnitEntity existingUnit = unitRepository.findById(unit.getId())
                .orElseThrow(() -> new EntityNotFoundException("Unit with id " + unit.getId() + " does not exist."));

        if (unit.getName() != null) {
            existingUnit.setName(unit.getName());
        }

        if (unit.getDescription() != null) {
            existingUnit.setDescription(unit.getDescription());
        }

        return unitRepository.save(existingUnit);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public boolean deleteUnit(Long id) throws Exception {
        try {
            unitRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
