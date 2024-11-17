package net.javaguides.springboot.service;

import net.javaguides.springboot.model.Resume;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.repository.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;

    @Autowired
    public ResumeService(ResumeRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    public Resume getResumeByUserId(Long userId) {
        Resume resume = resumeRepository.findByUserId(userId);
        if (resume == null) {
            System.out.println("No resume found for user ID: " + userId);
        }
        return resume;
    }

public Resume saveResume(User user, MultipartFile photo, MultipartFile resumeFile, String fullName, String email, String gender, int age, String phoneNumber, String maritalStatus, String expectedSalary) throws IOException {
    try {
        // Save files and get file paths with the new prefix
        String photoPath = saveFile(photo, "photo");
        String resumeFilePath = saveFile(resumeFile, "resume");

        System.out.println("Saving resume for user: " + user.getEmail());
        Resume resume = new Resume();
        resume.setUser(user);
        resume.setPhotoPath(photoPath);
        resume.setResumeFilePath(resumeFilePath);
        resume.setFullName(fullName);
        resume.setEmail(email);
        resume.setGender(gender);
        resume.setAge(age);
        resume.setPhoneNumber(phoneNumber);
        resume.setMaritalStatus(maritalStatus);
        resume.setExpectedSalary(expectedSalary);

        System.out.println("Saving resume to the database");
        return resumeRepository.save(resume);
    } catch (IOException e) {
        System.out.println("Error saving resume: " + e.getMessage());
        e.printStackTrace();
        throw new RuntimeException("Error saving the resume", e);
    }
}
    private String saveFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null;
        }

        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        String uploadDir = "src/main/resources/static/uploads/resume/"; // Define the upload directory
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath); // Create directories if they don't exist
        }

        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Return the relative path that will be used for accessing the file in the application
        return "/uploads/resume/" + fileName;
    }


    public Resume updateResume(Long resumeId, User user, MultipartFile photo, MultipartFile resumeFile, String fullName, String email, String gender, int age, String phoneNumber, String maritalStatus, String expectedSalary) throws IOException {
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid resume Id: " + resumeId));

        resume.setFullName(fullName);
        resume.setEmail(email);
        resume.setGender(gender);
        resume.setAge(age);
        resume.setPhoneNumber(phoneNumber);
        resume.setMaritalStatus(maritalStatus);
        resume.setExpectedSalary(expectedSalary);

        if (!photo.isEmpty()) {
            String photoPath = saveFile(photo, "photo");
            resume.setPhotoPath(photoPath);
        }

        if (!resumeFile.isEmpty()) {
            String resumeFilePath = saveFile(resumeFile, "resume");
            resume.setResumeFilePath(resumeFilePath);
        }

        return resumeRepository.save(resume);
    }

    private String saveFile(MultipartFile file, String type) throws IOException {
        if (file.isEmpty()) {
            return null;
        }

        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        String uploadDir = "src/main/resources/static/uploads/" + type; // Dynamic directory based on file type
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath); // Create directories if they don't exist
        }

        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Only return the file name for database storage
        return fileName;
    }


    public void deleteResume(Long resumeId) {

        resumeRepository.deleteById(resumeId);
    }
    //getAllResumes
    public List<Resume> getAllResumes() {
        return resumeRepository.findAll();
    }

}
