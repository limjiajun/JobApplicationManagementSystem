package net.javaguides.springboot.web;

import net.javaguides.springboot.model.Job;
import net.javaguides.springboot.model.Resume;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.repository.UserRepository;
import net.javaguides.springboot.service.JobApplicationService;
import net.javaguides.springboot.service.JobService;
import net.javaguides.springboot.service.ResumeService;
import net.javaguides.springboot.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/user")
public class UserController {

    private final JobService jobService;
    private final JobApplicationService jobApplicationService;
    private final UserService userService;
    private final ResumeService resumeService;

;
    @Autowired
    public UserController(JobService jobService, JobApplicationService jobApplicationService,UserService userService, ResumeService resumeService) {
        this.jobService = jobService;
        this.jobApplicationService = jobApplicationService;
        this.userService = userService;
        this.resumeService = resumeService;
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("")
    public String userDashboard(Model model) {
        model.addAttribute("welcomeMessage", "欢迎来到用户主页！");
        return "index"; // 定向到 index.html 页面
    }

//    @GetMapping("/jobs/grid")
//    public String viewJobGrid(Model model) {
//        List<Job> jobs = jobService.getAllJobs();
//        model.addAttribute("jobs", jobs);
//        return "job_grid_user"; // 定向到 job_grid_user.html 页面
//    }
    @GetMapping("/jobs/grid")
    public String viewJobGrid(@RequestParam(defaultValue = "") String search,
                              @RequestParam(defaultValue = "0") int page, Model model) {
        Pageable pageable = PageRequest.of(page, 9); // Show 9 jobs per page
        Page<Job> jobPage;

        if (search.isEmpty()) {
            jobPage = jobService.findAll(pageable);
        } else {
            jobPage = jobService.searchJobs(search, pageable);
        }

        model.addAttribute("jobs", jobPage.getContent());
        model.addAttribute("page", jobPage);
        model.addAttribute("searchQuery", search);
        return "job_grid_user";
    }
    @GetMapping("/jobs/detail/{id}")
    public String viewJobDetail(@PathVariable Long id, Model model, Principal principal) {
        Job job = jobService.getJobById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid job Id:" + id));
        
        // Get the currently logged-in user
        String userEmail = principal.getName();
        User user = userService.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userEmail));
        
        // Check if the user has already applied
        boolean alreadyApplied = jobApplicationService.isUserAlreadyApplied(job.getId(), user.getId());
        
        // Pass the job and application status to the model
        model.addAttribute("job", job);
        model.addAttribute("alreadyApplied", alreadyApplied);  // Send the status to the view
        
        return "job_detail_user";  // Return the job detail page
    }
    @PostMapping("/apply/{id}")
    public String applyForJob(@PathVariable Long id, Principal principal) {
        // Get the email of the currently logged-in user
        String userEmail = principal.getName();
    
        // Fetch the job details
        Job job = jobService.getJobById(id).orElseThrow(() -> new IllegalArgumentException("Invalid job Id:" + id));
    
        // Fetch the user based on email
        Optional<User> user = userService.findByEmail(userEmail);
        if (!user.isPresent()) {  // Use isPresent() to check if the Optional is empty
            throw new IllegalArgumentException("User not found: " + userEmail);
        }
        
        // Apply for the job
        jobApplicationService.applyForJob(job, user);
    
        return "redirect:/user/jobs/grid"; // Redirect back to job grid after applying
    }
     @GetMapping("/resume")
    public String viewResume(Model model, Principal principal) {
        String userEmail = principal.getName();
        User user = userService.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Resume resume = resumeService.getResumeByUserId(user.getId());
        model.addAttribute("resume", resume);
        return "resume";  // Return the resume page
    }


    @PostMapping("/resume/upload")
    public String uploadResume(@RequestParam("photo") MultipartFile photo,
                                @RequestParam("resumeFile") MultipartFile resumeFile,
                                @RequestParam("fullName") String fullName,
                                @RequestParam("email") String email,
                                @RequestParam("gender") String gender,
                                @RequestParam("age") int age,
                                @RequestParam("phoneNumber") String phoneNumber,
                                @RequestParam("maritalStatus") String maritalStatus,
                                @RequestParam("expectedSalary") String expectedSalary,
                                Principal principal) throws IOException {
        
        String uploadDir = "src/main/resources/static/uploads/";
    
        // 确保上传目录存在
        File uploadDirectory = new File(uploadDir);
        if (!uploadDirectory.exists()) {
            uploadDirectory.mkdirs();
        }
    
        // 获取当前登录用户
        String userEmail = principal.getName();
        User user = userService.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    
        // 创建简历对象
        Resume resume = new Resume();
        resume.setFullName(fullName);
        resume.setEmail(email);
        resume.setGender(gender);
        resume.setAge(age);
        resume.setPhoneNumber(phoneNumber);
        resume.setMaritalStatus(maritalStatus);
        resume.setExpectedSalary(expectedSalary);
    
        // 上传照片
        if (!photo.isEmpty()) {
            String photoFileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename();
            Path photoPath = Paths.get(uploadDir + photoFileName);
            Files.copy(photo.getInputStream(), photoPath);
    
            // 设置照片路径到数据库
            resume.setPhotoPath("/uploads/" + photoFileName);
        }
    
        // 上传简历文件
        if (!resumeFile.isEmpty()) {
            String resumeFileName = System.currentTimeMillis() + "_" + resumeFile.getOriginalFilename();
            Path resumeFilePath = Paths.get(uploadDir + resumeFileName);
            Files.copy(resumeFile.getInputStream(), resumeFilePath);
    
            // 设置简历文件路径到数据库
            resume.setResumeFilePath("/uploads/" + resumeFileName);
        }
    
        // 保存简历到数据库
        resumeService.saveResume(user, photo, resumeFile, fullName, userEmail, gender, age, phoneNumber, maritalStatus, expectedSalary);
    
        // 重定向到用户简历页面
        return "redirect:/user/resume"; // 跳转到简历页面
    }

    @GetMapping("/resume/edit")
    public String editResume(Model model, Principal principal) {
        String userEmail = principal.getName();
        User user = userService.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Resume resume = resumeService.getResumeByUserId(user.getId());
        model.addAttribute("resume", resume);
        return "edit_resume"; // Display the edit resume form
    }

    @PostMapping("/resume/edit")
    public String updateResume(@RequestParam("resumeId") Long resumeId,
                                @RequestParam("photo") MultipartFile photo,
                                @RequestParam("resumeFile") MultipartFile resumeFile,
                                @RequestParam("fullName") String fullName,
                                @RequestParam("email") String email,
                                @RequestParam("gender") String gender,
                                @RequestParam("age") int age,
                                @RequestParam("phoneNumber") String phoneNumber,
                                @RequestParam("maritalStatus") String maritalStatus,
                                @RequestParam("expectedSalary") String expectedSalary,
                                Principal principal) throws IOException {

        String userEmail = principal.getName();
        User user = userService.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Update the resume
        resumeService.updateResume(resumeId, user, photo, resumeFile, fullName, email, gender, age, phoneNumber, maritalStatus, expectedSalary);

        return "redirect:/user/resume"; // Redirect back to the resume page after updating
    }
    
}