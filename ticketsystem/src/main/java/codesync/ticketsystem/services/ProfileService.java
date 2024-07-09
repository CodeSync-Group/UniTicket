package codesync.ticketsystem.services;

import codesync.ticketsystem.entities.ProfileEntity;
import codesync.ticketsystem.repositories.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {
    @Autowired
    ProfileRepository profileRepository;

    public List<ProfileEntity> getProfiles() {
        return profileRepository.findAll();
    }

    public ProfileEntity updateProfile(ProfileEntity profile) {
        ProfileEntity existingProfile = profileRepository.findById(profile.getId())
                .orElseThrow(() -> new EntityNotFoundException("Profile with id " + profile.getId() + "does not exist."));

        if (profile.getFirstname() != null) {
            existingProfile.setFirstname(profile.getFirstname());
        }

        if (profile.getLastname() != null) {
            existingProfile.setLastname(profile.getLastname());
        }

        if (profile.getSecondSurname() != null) {
            existingProfile.setSecondSurname(profile.getSecondSurname());
        }

        if (profile.getEmail() != null) {
            existingProfile.setEmail(profile.getEmail());
        }

        if (profile.getDescription() != null) {
            existingProfile.setDescription(profile.getDescription());
        }

        if (profile.getGender() != null) {
            existingProfile.setGender(profile.getGender());
        }

        if (profile.getBirthday() != null) {
            existingProfile.setBirthday(profile.getBirthday());
        }

        if (profile.getPicture() != null) {
            existingProfile.setPicture(profile.getPicture());
        }

        return profileRepository.save(existingProfile);
    }

    public boolean deleteProfile(Long id) throws Exception {
        try {
            profileRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
