package com.example.workermicroservice.service;
import com.example.workermicroservice.Entities.category.Category;
import com.example.workermicroservice.Entities.worker.*;
import com.example.workermicroservice.Projections.Email;
import com.example.workermicroservice.Projections.WorkerProjection;
import com.example.workermicroservice.Proxy.AuthProxy;
import com.example.workermicroservice.Proxy.InteractionProxy;
import com.example.workermicroservice.dtos.*;
import com.example.workermicroservice.repositpries.CategoryRepository;
import com.example.workermicroservice.repositpries.SkillRepository;
import com.example.workermicroservice.repositpries.WorkerRepository;
import com.example.workermicroservice.types.WorkerStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.print.Pageable;
import java.util.List;

import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class WorkerService {

    @Autowired
    private WorkerRepository workerRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private InteractionProxy interactionProxy;
    @Autowired
    private AuthProxy authProxy;
    public ResponseEntity<String> approveWorker(String workerId){
        List<Optional<Worker>> allByUserIdIn = workerRepository.findAllByUserIdIn(List.of(workerId));
        if(allByUserIdIn.size() ==0 ){
            return ResponseEntity.badRequest().body("worker not found");
        }
        if( allByUserIdIn.get(0).isEmpty()){
            return ResponseEntity.badRequest().body("worker not found");
        }
        Worker w = allByUserIdIn.get(0).get();

        w.setStatus(WorkerStatus.APPROVED);
        workerRepository.save(w);
        return ResponseEntity.ok("worker approved");
    }
    public boolean deosWorkerExist(String idWorker){
        return workerRepository.existsWorkerByUserId(idWorker);
    }
    public List<String> getWorkerIds(){
//        return this.workerRepository.findAllUserIds();

            List<Worker> workers = workerRepository.findAll();
            return workers.stream()
                    .map(Worker::getUserId)
                    .collect(Collectors.toList());

    }
    public List<PaginatedWorkerResponse> getAllWorkers (Integer page , Integer pageSize){

        Page<Worker> workers =  this.workerRepository.findAll(PageRequest.of(page, pageSize));
        return workers.stream().map((Worker w) ->{
            return PaginatedWorkerResponse.builder()
                    .firstName(w.getFirstName())
                    .lastName(w.getLastName())
//                    .status(w.getStatus())
//                    .phoneNumber(w.getPhoneNumber())
                    .skills(w.getSkills())
                    .userId(w.getUserId())
//                    .signUpDate(w.getSignUpDate())
                    .publicPrice(w.getPublicPrice())
                    .category(w.getCategory())
                    .build();
        }).toList();
    }
    public List<WorkerProjection> getWorkers(){

        List<WorkerProjection> workers = this.workerRepository.getWorkers();
        List<String> userIds = workers.stream()
                .map(WorkerProjection::getUserId)
                .collect(Collectors.toList());
        String ids=  String.join(",", userIds);
        List<Email> emails=authProxy.getWorkerEmail(ids);

        for (WorkerProjection worker : workers) {
            for (Email email : emails) {
                if (email.getUserId().equals(worker.getUserId())) {
                    worker.setEmail(email.getEmail());
                    break;
                }
            }
        }

return workers;

    }


    public void signUpWorker(SignUpRequestDto sq,boolean isAdmin){
        Worker worker = Worker.builder()
                .firstName(sq.getFirstName())
                .lastName(sq.getLastName())
                .status(isAdmin ? WorkerStatus.APPROVED:WorkerStatus.PENDING)
                .signUpDate(new Date())
                .educationDetails(sq.getEducationDetails().stream().map(this::mapEducationDetail).toList())
                .address(mapToAddress(sq.getAddress()))
                .publicPrice(sq.getPublicPrice())
                .skills(sq.getSkills().stream().map(this::mapToSkill).toList())
                .title(sq.getTitle())
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

      public List<Category> getCategories(){return categoryRepository.findAll();}
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


    public Worker WorkerWithNbrInteractions(String idWorker) {
        Worker worker=workerRepository.findWorkerByUserId(idWorker);

        if (worker != null) {
            CompletableFuture<Integer> impressionsFuture =
                    CompletableFuture.supplyAsync(() -> interactionProxy.getNbrImpressions(idWorker));
            CompletableFuture<Integer> wishesFuture =
                    CompletableFuture.supplyAsync(() -> interactionProxy.getNbrWishes(idWorker));
            CompletableFuture<Integer> clicksFuture =
                    CompletableFuture.supplyAsync(() -> interactionProxy.getNbrClicks(idWorker));

//            int clicks = interactionProxy.getNbrClicks(idWorker);
//             worker.setNbrClicks(clicks);
            // Wait for the impressions and wishes futures to complete
            int impressions = impressionsFuture.join();
            int wishes = wishesFuture.join();
            int clicks=clicksFuture.join();

            worker.setNbrImpressions(impressions);
            worker.setNbrWishes(wishes);
            worker.setNbrClicks(clicks);
        }
           return worker;

    }

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

