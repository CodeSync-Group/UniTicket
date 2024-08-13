package com.codesync.uniticket.services;

import com.codesync.uniticket.entities.ConfigurationEntity;
import com.codesync.uniticket.repositories.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConfigurationService {
    @Autowired
    ConfigurationRepository configurationRepository;

    public ConfigurationEntity saveConfiguration(ConfigurationEntity configuration) {
        return configurationRepository.save(configuration);
    }

    public Optional<ConfigurationEntity> getConfigurationById(Long id) {
        return configurationRepository.findById(id);
    }
}
