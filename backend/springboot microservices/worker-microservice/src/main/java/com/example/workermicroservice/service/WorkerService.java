package com.example.workermicroservice.service;
import com.example.workermicroservice.Entities.worker.*;
import com.example.workermicroservice.dtos.*;
import com.example.workermicroservice.repositpries.SkillRepository;
import com.example.workermicroservice.repositpries.WorkerRepository;
import com.example.workermicroservice.types.WorkerStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.print.Pageable;
import java.util.List;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class WorkerService {

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private SkillRepository skillRepository;



    public List<PaginatedWorkerResponse> getAllWorkers (Integer page , Integer pageSize){

        Page<Worker> workers =  this.workerRepository.findAll(PageRequest.of(page, pageSize));
        return workers.stream().map((Worker w) ->{
            return PaginatedWorkerResponse.builder()
                    .firstName(w.getFirstName())
                    .lastName(w.getLastName())
                    .status(w.getStatus())
                    .phoneNumber(w.getPhoneNumber())
                    .signUpDate(w.getSignUpDate())
                    .publicPrice(w.getPublicPrice())
                    .category(w.getCategory())
                    .build();
        }).toList();
    }


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


    public List<WorkerExistsResponseDto> getWorkersExist( List<String> workerIds){
        AtomicInteger i = new AtomicInteger();
        List<Optional<Worker>> workers =   this.workerRepository.findAllByUserIdIn(workerIds);
        System.out.println("worker found "+workers.toString());
        return workerIds.stream().map((String id)->{
            int index =  i.getAndIncrement();
            boolean b = (index <= workers.size()-1);
            if(b){
                b = workers.get(index).isPresent();
            }

            return WorkerExistsResponseDto.builder()
                    .workerId(id)
                    .exists(b)
                    .status(b? ( workers.get(index)).get().getStatus():null)
                    .publicPrice(b? ( workers.get(index)).get().getPublicPrice():null)
                    .build();
        }).toList();

    }



    public EducationDetail mapEducationDetail(EducationDetailDto ed){
        return EducationDetail.builder()
                .description(ed.getDescription())
                .field(ed.getField())
                .startDate(ed.getStartDate())
                .endDate(ed.getEndDate())
                .school(ed.getSchool())
                .location(ed.getLocation())
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
                .build();
    }


//    public Worker WorkerInteractions(String idWorker) {
//        System.out.println("worker >>>>>> :: "+idWorker);
//        Worker worker=workerRepository.findById(idWorker)
//                .orElse(null);
//        if (worker != null) {
//            CompletableFuture<List<ImpressionModel>> impressionsFuture =
//                    CompletableFuture.supplyAsync(() -> interactionProxy.getImpressions(idWorker, "toWorker"));
//            CompletableFuture<List<WishModel>> wishesFuture =
//                    CompletableFuture.supplyAsync(() -> interactionProxy.getWishes(idWorker, "WishToWorker"));
//
//            List<ClickModel> clicks = new ArrayList<>(interactionProxy.getClicks(idWorker, "ClickToWorker"));
//            worker.setClicks(clicks);
//
//            // Wait for the impressions and wishes futures to complete
//            List<ImpressionModel> impressions = impressionsFuture.join();
//            List<WishModel> wishes = wishesFuture.join();
//
//            worker.setImpressions(impressions);
//            worker.setWishes(wishes);
//        }
//           return worker;
//
//    }

//   public List<Worker> getWorkersWithInteractions(String workerIds){
//       // Split the comma-separated IDs into an array
//       String[] ids = workerIds.split(",");
//       List<Worker> workers = new ArrayList<>();
//
//       // Process the array of IDs and retrieve the workers
//       for (String id : ids) {
//           Worker worker = WorkerInteractions(id);
//           workers.add(worker);
//       }
//
//
//       return workers;
//   }
//
//    public Worker WorkerWishes(String idWorker) {
//        System.out.println("worker >>>>>> :: "+idWorker);
//        Worker worker=workerRepository.findById(idWorker)
//                .orElse(null);
//        if (worker != null) {
//            List<WishModel> wishes =
//                    new ArrayList<>(interactionProxy.getWishes(idWorker, "WishToWorker"));
//            worker.setWishes(wishes);
//        }
//        return worker;
//
//    }
//
//    public List<Worker> getWorkersWithWishes(String workerIds){
//        // Split the comma-separated IDs into an array
//        String[] ids = workerIds.split(",");
//        List<Worker> workers = new ArrayList<>();
//
//        // Process the array of IDs and retrieve the workers
//        for (String id : ids) {
//            Worker worker = WorkerWishes(id);
//            workers.add(worker);
//        }
//
//
//        return workers;
//    }
//    public Worker WorkerClicks(String idWorker) {
//        System.out.println("worker >>>>>> :: "+idWorker);
//        Worker worker=workerRepository.findById(idWorker)
//                .orElse(null);
//        if (worker != null) {
//            List<ClickModel> clicks =
//                    new ArrayList<>(interactionProxy.getClicks(idWorker, "ClickToWorker"));
//            worker.setClicks(clicks);
//        }
//        return worker;
//
//    }
//
//    public List<Worker> getWorkersWithClicks(String workerIds){
//        // Split the comma-separated IDs into an array
//        String[] ids = workerIds.split(",");
//        List<Worker> workers = new ArrayList<>();
//
//        // Process the array of IDs and retrieve the workers
//        for (String id : ids) {
//            Worker worker = WorkerClicks(id);
//            workers.add(worker);
//        }
//
//
//        return workers;
//    }
}

