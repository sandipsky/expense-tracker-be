package com.sandipsky.expense_tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sandipsky.expense_tracker.dto.UserDTO;
import com.sandipsky.expense_tracker.entity.User;
import com.sandipsky.expense_tracker.exception.DuplicateResourceException;
import com.sandipsky.expense_tracker.exception.ResourceNotFoundException;
import com.sandipsky.expense_tracker.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User saveUser(UserDTO dto, MultipartFile imageFile) {
        if (repository.existsByUsername(dto.getUsername())) {
            throw new DuplicateResourceException("Username already exists");
        }
        if (repository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        User user = new User();
        mapDtoToEntity(dto, user);
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = saveImageFile(imageFile);
            user.setImageUrl(imageUrl);
        }
        return repository.save(user);
    }

    public List<UserDTO> getUsers() {
        return repository.findAll().stream().map(this::mapToDTO).toList();
    }

    public UserDTO getUserById(int id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return mapToDTO(user);
    }

    public User updateUser(int id, UserDTO dto, MultipartFile imageFile) {
        User existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (repository.existsByUsernameAndIdNot(dto.getUsername(), id)) {
            throw new DuplicateResourceException("Username already exists");
        }

        if (repository.existsByEmailAndIdNot(dto.getEmail(), id)) {
            throw new DuplicateResourceException("Email already exists");
        }

        mapDtoToEntity(dto, existing);
        if (imageFile != null && !imageFile.isEmpty()) {
            deleteImageFileIfExists(existing.getImageUrl());
            String imageUrl = saveImageFile(imageFile);
            existing.setImageUrl(imageUrl);
        }
        return repository.save(existing);
    }

    public void deleteUser(int id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        deleteImageFileIfExists(user.getImageUrl());
        repository.deleteById(id);
    }

    public Resource getUserImageFileById(int id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (user.getImageUrl() == null || user.getImageUrl().isBlank()) {
            throw new ResourceNotFoundException("Image not found for user");
        }

        try {
            Path filePath = Paths.get(user.getImageUrl().replaceFirst("/", "")).toAbsolutePath();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new ResourceNotFoundException("File not found or unreadable");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid file path", e);
        }
    }

    private UserDTO mapToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setPassword("");
        dto.setIsActive(user.getIsActive());
        return dto;
    }

    private void mapDtoToEntity(UserDTO dto, User user) {
        user.setUsername(dto.getUsername().trim());
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail().trim());
        String hashedPassword = passwordEncoder.encode(dto.getPassword());
        user.setPassword(hashedPassword);
        user.setIsActive(dto.getIsActive());
    }

    private String saveImageFile(MultipartFile file) {
        try {
            String uploadDir = "uploads/users/";
            String fileName = file.getOriginalFilename();
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return "/" + uploadDir + fileName; // e.g., /uploads/users/user_123.jpg
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image file", e);
        }
    }

    private void deleteImageFileIfExists(String imageUrl) {
        if (imageUrl != null && !imageUrl.isBlank()) {
            try {
                Path filePath = Paths.get(imageUrl.replaceFirst("/", "")).toAbsolutePath();
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                // Optionally log warning but don't block main flow
                System.err.println("Failed to delete image file: " + e.getMessage());
            }
        }
    }
}
