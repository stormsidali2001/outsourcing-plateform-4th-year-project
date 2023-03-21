package com.example.workermicroservice.service;

import com.example.workermicroservice.Entities.worker.*;
import com.example.workermicroservice.dto.signupRequestDto.*;
import com.example.workermicroservice.repositpries.WorkerRepository;
import com.example.workermicroservice.types.WorkerStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
import java.util.stream.Collectors;

@Service
public class WorkerService {

    @Autowired
    private WorkerRepository workerRepository;

    public void signUpWorker(SignUpRequestDto sq){
        Worker worker = Worker.builder()
                .firstName(sq.getFirstName())
                .lastName(sq.getLastName())
                .status(WorkerStatus.PENDING)
                .signUpDate(new Date())
                .educationDetails(sq.getEducationDetails().stream().map(this::mapEducationDetail).toList())
                .address(mapToAddress(sq.getAddress()))
                .publicPrice(sq.getPublicPrice())
                .skills(sq.getSkills().stream().map(this::mapToSkill).toList())
                .phoneNumber(sq.getPhoneNumber())
                .portfolioProjects(sq.getPortfolioProjects().stream().map(this::mapToPortfolioProject).toList())
                .certifications(sq.getCertifications().stream().map(this::mapToCertification).toList())
                .workExperiences(sq.getWorkExperiences().stream().map(this::mapToWorkExperience).toList())
                .build();
        workerRepository.save(worker);

    }

    public EducationDetail mapEducationDetail(EducationDetailDto ed){
        return EducationDetail.builder()
                .description(ed.getDescription())
                .field(ed.getField())
                .startDate(ed.getStartDate())
                .endDate(ed.getEndDate())
                .school(ed.getSchool())
                .location(ed.getLocation())
                .skills(ed.getSkills().stream().map(this::mapToSkill).toList())
                .build();
    }

    public Skill mapToSkill(SkillDto skillDto){
        return Skill.builder()
                .name(skillDto.getName())
                .category(skillDto.getCategory())
                .build();
    }

    public Address mapToAddress(AddressDto ad){
        return Address.builder()
                .wilaya(ad.getWilaya())
                .commune(ad.getCommune())
                .addressDomissile(ad.getAddressDomissile())
                .numRue(ad.getNumRue())
                .code_postal(ad.getNumRue())
                .build();
    }

    public PortfolioProject mapToPortfolioProject(PortfolioProjectDto pp){
        return PortfolioProject.builder()
                .projectGoal(pp.getProjectGoal())
                .projectSolution(pp.getProjectSolution())
                .role(pp.getRole())
                .skills(pp.getSkills().stream().map(this::mapToSkill).toList())
                .title(pp.getTitle())
                .build();
    }
    public Certification mapToCertification(CertificationDto certification){
        return Certification.builder()
                .url(certification.getUrl())
                .skill(mapToSkill(certification.getSkill()))
                .title(certification.getTitle())
                .companyName(certification.getCompanyName())
                .issuedAt(certification.getIssuedAt())
                .build();
    }

    public WorkExperience mapToWorkExperience(WorkExperienceDto ex){
        return WorkExperience.builder()
                .companyName(ex.getCompanyName())
                .title(ex.getTitle())
                .type(ex.getTitle())
                .startDate(ex.getStartDate())
                .endDate(ex.getEndDate())
                .description(ex.getDescription())
                .location(ex.getLocation())
                .skills(ex.getSkills().stream().map(this::mapToSkill).toList())
                .build();
    }

}
