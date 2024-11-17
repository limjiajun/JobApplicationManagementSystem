package net.javaguides.springboot.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import net.javaguides.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import net.javaguides.springboot.model.Job;
import net.javaguides.springboot.model.User;

import net.javaguides.springboot.model.JobApplication;
import net.javaguides.springboot.model.Resume;
import net.javaguides.springboot.service.JobApplicationService;
import net.javaguides.springboot.service.JobService;
import net.javaguides.springboot.service.ResumeService;
import net.javaguides.springboot.service.ResumeService;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final JobService jobService;
    private final JobApplicationService jobApplicationService;
    private ResumeService resumeService;

    @Autowired
    public AdminController(UserService userService, JobService jobService, JobApplicationService jobApplicationService, ResumeService resumeService) {
        this.userService = userService;
        this.jobService = jobService;
        this.jobApplicationService = jobApplicationService;
        this.resumeService = resumeService;
    }

    @GetMapping
    public String adminDashboard(Model model) {
        long totalUsers = userService.getAllUsers().size();

        long totalResumes = resumeService.getAllResumes().size();
        long totalJobs = jobService.getAllJobs().size();

        // 计算薪资范围
        double minSalary = jobService.getAllJobs().stream()
                .mapToDouble(Job::getSalary)
                .min()
                .orElse(0);
        double maxSalary = jobService.getAllJobs().stream()
                .mapToDouble(Job::getSalary)
                .max()
                .orElse(0);
        String salaryRange = String.format("最低薪资: %.2f, 最高薪资: %.2f", minSalary, maxSalary);

        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalResumes", totalResumes);
        model.addAttribute("totalJobs", totalJobs);
        model.addAttribute("minSalary", minSalary);
        model.addAttribute("maxSalary", maxSalary);
        model.addAttribute("salaryRange", salaryRange);

        return "admin"; // 返回后台管理页面
    }

    @GetMapping("/create_job")
    public String createJobForm(Model model) {
        model.addAttribute("job", new Job()); // Empty Job object for form
        return "create_job"; // Form for creating a job
    }

    @PostMapping("/create_job")
    public String saveJob(@ModelAttribute Job job, @RequestParam("image") MultipartFile imageFile) throws IOException {
        String uploadDir = "src/main/resources/static/uploads/";

        // Ensure upload directory exists
        File uploadDirectory = new File(uploadDir);
        if (!uploadDirectory.exists()) {
            uploadDirectory.mkdirs();
        }

        if (!imageFile.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            Path filePath = Paths.get(uploadDir + fileName);
            Files.copy(imageFile.getInputStream(), filePath);

            // Set image path for database storage
            job.setImagePath("/uploads/" + fileName);
        }
        jobService.saveJob(job); // Save job to the database
        return "redirect:/admin/jobs"; // Redirect to job list
    }

//    @GetMapping("/jobs")
//    public String viewJobs(Model model) {
//        List<Job> jobs = jobService.getAllJobs();
//        model.addAttribute("jobs", jobs);
//        return "job_table"; // Job list page
//    }
//    @GetMapping("/jobs")
//    public String listJobs(@RequestParam(defaultValue = "0") int page, Model model) {
//        Page<Job> jobPage = jobService.findAll(PageRequest.of(page, 10)); // 每页显示 10 个职位
//        model.addAttribute("jobs", jobPage.getContent());
//        model.addAttribute("page", jobPage); // 将分页数据传递到视图
//        return "job_table-list";  // 返回模板
//    }
// 显示所有职位，不分页
@GetMapping("/jobs")
public String viewJobs(Model model) {
    List<Job> jobs = jobService.getAllJobs();
    model.addAttribute("jobs", jobs);
    return "job_table"; // Job list page
}

//    // 分页显示职位
//    @GetMapping("/jobs")
//    public String listJobs(@RequestParam(defaultValue = "0") int page, Model model) {
//        Page<Job> jobPage = jobService.findAll(PageRequest.of(page, 10)); // 每页显示 10 个职位
//        model.addAttribute("jobs", jobPage.getContent());
//        model.addAttribute("page", jobPage); // 将分页数据传递到视图
//        return "job_table-list";  // 返回模板
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
        return "job_grid";
    }

//    @GetMapping("/jobs/grid")
//    public String viewJobGrid(Model model) {
//        List<Job> jobs = jobService.getAllJobs();
//        model.addAttribute("jobs", jobs);
//        return "job_grid"; // 定向到 job_grid_user.html 页面
//    }

    @GetMapping("/jobs/edit/{id}")
    public String editJobForm(@PathVariable Long id, Model model) {
        Job job = jobService.getJobById(id).orElseThrow(() -> new IllegalArgumentException("Invalid job Id:" + id));
        model.addAttribute("job", job);
        return "edit_job"; // Form for editing a job
    }

//    @PostMapping("/jobs/edit/{id}")
//    public String updateJob(@PathVariable Long id, @ModelAttribute Job job) {
//        job.setId(id);
//        jobService.saveJob(job);
//        return "redirect:/admin/jobs"; // Redirect to job list after update
//    }
@PostMapping("/jobs/edit/{id}")
public String updateJob(@PathVariable Long id, @ModelAttribute Job job, @RequestParam(value = "image", required = false) MultipartFile imageFile) throws IOException {
    // Retrieve the existing job from the database
    Job existingJob = jobService.getJobById(id).orElseThrow(() -> new IllegalArgumentException("Invalid job Id:" + id));

    // Handle image upload
    if (imageFile != null && !imageFile.isEmpty()) {
        // If new image is uploaded, save it and update the imagePath
        String uploadDir = "src/main/resources/static/uploads/";
        File uploadDirectory = new File(uploadDir);
        if (!uploadDirectory.exists()) {
            uploadDirectory.mkdirs();
        }

        String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
        Path filePath = Paths.get(uploadDir + fileName);
        Files.copy(imageFile.getInputStream(), filePath);

        // Set the new image path
        job.setImagePath("/uploads/" + fileName);
    } else {
        // If no new image is uploaded, keep the existing imagePath
        job.setImagePath(existingJob.getImagePath());
    }

    // Update job details
    job.setId(id);
    jobService.saveJob(job);

    return "redirect:/admin/jobs"; // Redirect to job list after update
}


    @PostMapping("/jobs/delete/{id}")
    public String deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return "redirect:/admin/jobs"; // Redirect to job list after deletion
    }

    @GetMapping("/jobs/detail/{id}")
    public String viewJobDetail(@PathVariable Long id, Model model) {
        Job job = jobService.getJobById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid job Id:" + id));
        model.addAttribute("job", job);
        return "job_detail"; // Job detail page
    }

    @GetMapping("/applications")
    public String viewApplications(Model model) {
        List<JobApplication> applications = jobApplicationService.getAllApplications();
        model.addAttribute("jobApplications", applications);  // Pass 'jobApplications' here
        return "admin_job_applications";  // Thymeleaf template name
    }

    @PostMapping("/approve/{id}")
    public String approveApplication(@PathVariable Long id) {
        jobApplicationService.changeApplicationStatus(id, JobApplication.ApplicationStatus.APPROVED);
        return "redirect:/admin/applications"; // Redirect to applications list after approval
    }

    @PostMapping("/reject/{id}")
    public String rejectApplication(@PathVariable Long id) {
        jobApplicationService.changeApplicationStatus(id, JobApplication.ApplicationStatus.REJECTED);
        return "redirect:/admin/applications"; // Redirect to applications list after rejection
    }


    @GetMapping("/resumes/{id}")
    public String viewResume(@PathVariable Long id, Model model) {
        System.out.println("Loading resume for user id: " + id);
        Resume resume = resumeService.getResumeByUserId(id);
        model.addAttribute("resume", resume);
        return "admin_resumes";  // 返回视图
    }

    @GetMapping("/resumes")
    public String viewResumes(Model model) {
        List<Resume> resumes = resumeService.getAllResumes();
        model.addAttribute("resumes", resumes);
        return "resume_list"; // 返回视图
    }

    @GetMapping("/download/resume/{fileName}")
    public ResponseEntity<Resource> downloadResume(@PathVariable String fileName) throws IOException {
        Path filePath = Paths.get("src/main/resources/static/uploads/resume/" + fileName);
        Resource resource = new UrlResource(filePath.toUri());

        if (resource.exists() || resource.isReadable()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } else {
            throw new RuntimeException("Could not read the file!");
        }
    }

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "manage_users";
    }


    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id, Model model) {
        try {
            userService.deleteUser(id);
            return "redirect:/admin/users";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            List<User> users = userService.getAllUsers();
            model.addAttribute("users", users);
            return "manage_users"; // Reload the page with an error message
        }
    }


}

