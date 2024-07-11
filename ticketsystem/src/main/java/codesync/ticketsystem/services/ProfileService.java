package codesync.ticketsystem.services;

import codesync.ticketsystem.entities.ProfileEntity;
import codesync.ticketsystem.repositories.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProfileService {
    @Autowired
    ProfileRepository profileRepository;

    public List<ProfileEntity> getProfiles() {
        return profileRepository.findAll();
    }

    public Optional<ProfileEntity> getProfileById(Long id) {
        return profileRepository.findById(id);
    }

    public ProfileEntity updateProfile(ProfileEntity profile) {
        ProfileEntity existingProfile = profileRepository.findById(profile.getId())
                .orElseThrow(() -> new EntityNotFoundException("Profile with id " + profile.getId() + " does not exist."));

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

    public byte[] handleFileUpload(MultipartFile file) throws Exception {
        try {
            String originalFilename = file.getOriginalFilename();

            if (!originalFilename.endsWith(".jpg")
                    && !originalFilename.endsWith("png")
                    && !originalFilename.endsWith("jpeg")) {
                throw new Exception("Only JPG, JPEG & PNG files allowed");
            }

            long fileSize = file.getSize();

            int maxFileSize = 5 * 1024 * 1024;

            if (fileSize > maxFileSize) {
                throw new Exception("File size must be less or equal that 5MB");
            }

            byte[] bytes = file.getBytes();

            System.out.println(bytes);

            return bytes;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
