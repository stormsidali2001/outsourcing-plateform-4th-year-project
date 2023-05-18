package com.example.workermicroservice.service;

<<<<<<<< HEAD:backend/springboot microservices/worker-microservice/src/main/java/com/example/workermicroservice/service/WorkerService.java
========
import com.example.companymicroservice.Entities.worker.*;
import com.example.companymicroservice.Proxy.Interaction.InteractionProxy;
import com.example.companymicroservice.dto.signupRequestDto.*;
import com.example.companymicroservice.models.ClickModel;
import com.example.companymicroservice.models.ImpressionModel;
import com.example.companymicroservice.models.WishModel;
import com.example.companymicroservice.repositpries.SkillRepository;
import com.example.companymicroservice.repositpries.WorkerRepository;
>>>>>>>> b7886830ad31ad52ed663ff99f69c19340a21189:backend/worker-microservice/src/main/java/com/example/companymicroservice/service/WorkerService.java
import com.example.companymicroservice.types.WorkerStatus;
import com.example.workermicroservice.Entities.worker.*;
import com.example.workermicroservice.dtos.*;
import com.example.workermicroservice.repositpries.SkillRepository;
import com.example.workermicroservice.repositpries.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

@Service
public class WorkerService {

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private InteractionProxy interactionProxy;




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
                .userId(sq.getUserId())
                .category(sq.getCategory())
                .build();
        workerRepository.save(worker);

    }

    public List<Skill> getSkills(){
        return skillRepository.findAll();
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

    public com.example.workermicroservice.Entities.worker.Skill mapToSkill(SkillDto skillDto){
        return com.example.workermicroservice.Entities.worker.Skill.builder()
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


    public Worker WorkerInteractions(String idWorker) {
        System.out.println("worker >>>>>> :: "+idWorker);
        Worker worker=workerRepository.findById(idWorker)
                .orElse(null);
        if (worker != null) {
            CompletableFuture<List<ImpressionModel>> impressionsFuture =
                    CompletableFuture.supplyAsync(() -> interactionProxy.getImpressions(idWorker, "toWorker"));
            CompletableFuture<List<WishModel>> wishesFuture =
                    CompletableFuture.supplyAsync(() -> interactionProxy.getWishes(idWorker, "WishToWorker"));

            List<ClickModel> clicks = new ArrayList<>(interactionProxy.getClicks(idWorker, "ClickToWorker"));
            worker.setClicks(clicks);

            // Wait for the impressions and wishes futures to complete
            List<ImpressionModel> impressions = impressionsFuture.join();
            List<WishModel> wishes = wishesFuture.join();

            worker.setImpressions(impressions);
            worker.setWishes(wishes);
        }
           return worker;

    }

   public List<Worker> getWorkersWithInteractions(String workerIds){
       // Split the comma-separated IDs into an array
       String[] ids = workerIds.split(",");
       List<Worker> workers = new ArrayList<>();

       // Process the array of IDs and retrieve the workers
       for (String id : ids) {
           Worker worker = WorkerInteractions(id);
           workers.add(worker);
       }


       return workers;
   }

    public Worker WorkerWishes(String idWorker) {
        System.out.println("worker >>>>>> :: "+idWorker);
        Worker worker=workerRepository.findById(idWorker)
                .orElse(null);
        if (worker != null) {
            List<WishModel> wishes =
                    new ArrayList<>(interactionProxy.getWishes(idWorker, "WishToWorker"));
            worker.setWishes(wishes);
        }
        return worker;

    }

    public List<Worker> getWorkersWithWishes(String workerIds){
        // Split the comma-separated IDs into an array
        String[] ids = workerIds.split(",");
        List<Worker> workers = new ArrayList<>();

        // Process the array of IDs and retrieve the workers
        for (String id : ids) {
            Worker worker = WorkerWishes(id);
            workers.add(worker);
        }


        return workers;
    }
    public Worker WorkerClicks(String idWorker) {
        System.out.println("worker >>>>>> :: "+idWorker);
        Worker worker=workerRepository.findById(idWorker)
                .orElse(null);
        if (worker != null) {
            List<ClickModel> clicks =
                    new ArrayList<>(interactionProxy.getClicks(idWorker, "ClickToWorker"));
            worker.setClicks(clicks);
        }
        return worker;

    }

    public List<Worker> getWorkersWithClicks(String workerIds){
        // Split the comma-separated IDs into an array
        String[] ids = workerIds.split(",");
        List<Worker> workers = new ArrayList<>();

        // Process the array of IDs and retrieve the workers
        for (String id : ids) {
            Worker worker = WorkerClicks(id);
            workers.add(worker);
        }


        return workers;
    }
}

