package codesync.ticketsystem.services;

import codesync.ticketsystem.entities.ProfileEntity;
import codesync.ticketsystem.entities.UserEntity;
import codesync.ticketsystem.repositories.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;

@Service
public class ProfileService {
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    UserService userService;

    public List<ProfileEntity> getProfiles() {
        return profileRepository.findAll();
    }

    public Optional<ProfileEntity> getProfileByEmail(String email) {
        return profileRepository.findByEmail(email);
    }

    public Optional<ProfileEntity> getProfileById(Long id) {
        return profileRepository.findById(id);
    }

    public ProfileEntity updateProfile(ProfileEntity profile) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("User is not authenticated");
        }

        ProfileEntity existingProfile = profileRepository.findById(profile.getId())
                .orElseThrow(() -> new EntityNotFoundException("Profile with id " + profile.getId() + " does not exist."));

        UserEntity existingUser = userService.getUserByProfileId (existingProfile.getId())
                .orElseThrow(() -> new EntityNotFoundException("User with profile id " + existingProfile.getId() + " does not exist."));

        String currentUsername = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));

        if (!currentUsername.equals(existingUser.getUsername()) && !isAdmin) {
            throw new SecurityException("Not authorized to update this user");
        }

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

        return profileRepository.save(existingProfile);
    }

    @PreAuthorize("hasRole('ADMIN')")
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

    public byte[] handleImageDownload(String imageUrl) throws Exception {
        ImageInputStream iis = null;
        try {
            URL url = new URL(imageUrl);
            iis = ImageIO.createImageInputStream(url.openStream());
            Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(iis);

            if (!imageReaders.hasNext()) {
                throw new Exception("No image readers found for the image");
            }

            ImageReader reader = imageReaders.next();
            reader.setInput(iis);
            String formatName = reader.getFormatName();

            System.out.println("FileExtension: " + formatName);

            if (!formatName.equalsIgnoreCase("jpg")
                    && !formatName.equalsIgnoreCase("png")
                    && !formatName.equalsIgnoreCase("jpeg")) {
                throw new Exception("Only JPG, JPEG & PNG files allowed");
            }

            BufferedImage image = reader.read(0);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, formatName, baos);
            byte[] bytes = baos.toByteArray();

            int maxFileSize = 5 * 1024 * 1024; // 5MB
            if (bytes.length > maxFileSize) {
                throw new Exception("File size must be less or equal to 5MB");
            }

            System.out.println("Byte array length: " + bytes.length);

            return bytes;
        } catch (IOException e) {
            throw new Exception("Failed to download or process image: " + e.getMessage(), e);
        } finally {
            if (iis != null) {
                try {
                    iis.close();
                } catch (IOException ex) {
                    System.err.println("Failed to close ImageInputStream: " + ex.getMessage());
                }
            }
        }
    }
}
