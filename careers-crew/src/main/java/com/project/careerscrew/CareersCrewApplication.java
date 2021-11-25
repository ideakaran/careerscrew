package com.project.careerscrew;

import com.project.careerscrew.config.AppProperties;
import com.project.careerscrew.entities.Interviewer;
import com.project.careerscrew.entities.Job;
import com.project.careerscrew.entities.UserEntity;
import com.project.careerscrew.repository.InterviewerRepository;
import com.project.careerscrew.repository.JobRepository;
import com.project.careerscrew.repository.UserRepository;
import com.project.careerscrew.security.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;


@Slf4j
@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class CareersCrewApplication implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AppProperties appProperties;

    public CareersCrewApplication(UserRepository userRepository, PasswordEncoder passwordEncoder, AppProperties appProperties) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.appProperties = appProperties;
    }


    public static void main(String[] args) {
        SpringApplication.run(CareersCrewApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Optional<UserEntity> admin = userRepository.findByEmail(appProperties.getDefaults().getInitialAdminEmail());
        if (!admin.isPresent()) {
            log.warn("Initial Admin-User Not Found, initializing default admin with email: email");
            String initialAdminPassword = passwordEncoder.encode(appProperties.getDefaults().getInitialAdminPass());
            UserEntity defaultAdmin = new UserEntity();
            defaultAdmin.setFullName(appProperties.getDefaults().getInitialAdminName());
            defaultAdmin.setEmail(appProperties.getDefaults().getInitialAdminEmail());
            defaultAdmin.setPassword(initialAdminPassword);
            defaultAdmin.setRoles(Set.of(UserRole.ROLE_ADMIN));
            userRepository.save(defaultAdmin);
            log.info("Default Admin-User Initialization Successful");
        }

        createDefaultJobs();
        createDefaultInterviewers();

    }

    @Autowired
    JobRepository jobRepository;

    private void createDefaultJobs() {
        List<Job> jobList = new ArrayList<>();
        jobList.add(new Job(1l, "Senior Java/Angular Developer", true,"Strong object-oriented programming skills using Java (Spring Boot, MVC, J2EE)\n" +
                "Knowledge of relevant technologies inclusive of (Angular, JavaScript, jQuery)\n" +
                "Knowledge of algorithms and data structures"));
        jobList.add(new Job(2l, "Senior Java/Scala Developer", true,"As a Java / Scala Developer, you will work with the team to design and develop a mission critical Data Ingestion and Distribution Platform as well as several new and exciting products in the Big Data/Advanced Analytics space. You will operate in a fast-paced environment where multiple project deliverables are coordinated within specified deadlines."));
        jobList.add(new Job(3l, "Full Stack Developer", true,"Minimum 5 years of experience in Software development using C#.\n" +
                "Strong theoretical and practical knowledge of object-oriented programming\n" +
                "Ability to write reusable C# libraries"));
        jobList.add(new Job(4l, "Devops Engineer", true,"Translate application stories and requirements into functional applications.\n" +
                "Design, code, and maintain efficient and reliable C# code.\n" +
                "Write neat and clean code mostly for desktop application.\n" +
                "Create test cases and follow test cases to implement the requirements."));
        jobList.add(new Job(5l, "UX/UI Engineer", true,"Translate application stories and requirements into functional applications.\n" +
                "Working closely with managers, developers and other internal stakeholders to build storyboards, proof-of-concept, and prototypes of proposed websites and applications"));
        jobList.add(new Job(6l, "Other", true,"Application Support Analyst will be responsible for providing operational support for enterprise applications and database systems. This role oversees the daily requirements for support of the systems leveraged by Business Operations teams to provide support for critical production processes working with engineering and operations teams to identify and fulfill support requirements."));

        jobRepository.saveAll(jobList);
    }

    @Autowired
    InterviewerRepository interviewerRepository;

    private void createDefaultInterviewers() {
        List<Interviewer> interviewerList = new ArrayList<>();
        interviewerList.add(new Interviewer(1l, "Anushasan Poudel", "anushasan.poudel@abc.com"));
        interviewerList.add(new Interviewer(2l, "Anil Maharjan", "anil.maharjan.1@abc.com"));
        interviewerList.add(new Interviewer(3l, "Gagan Basnet", "gagan.basnet@abc.com"));
        interviewerList.add(new Interviewer(4l, "Karan Pant", "karan.pant@abc.com"));
        interviewerList.add(new Interviewer(5l, "Shrawan Adhikari", "shrawan.adhikari@abc.com"));

        interviewerRepository.saveAll(interviewerList);
    }
}
