package com.example.interactionmicroservice;

import com.example.interactionmicroservice.dto.ClickDto;
import com.example.interactionmicroservice.dto.ImpressionDto;
import com.example.interactionmicroservice.dto.WishDto;
import com.example.interactionmicroservice.proxy.CompanyProxy;
import com.example.interactionmicroservice.proxy.WorkerProxy;
import com.example.interactionmicroservice.repositories.ImpressionRepo;
import com.example.interactionmicroservice.service.ClickService;
import com.example.interactionmicroservice.service.ImpressionService;
import com.example.interactionmicroservice.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@SpringBootApplication
@EnableFeignClients
//@EnableWebMvc
public class InteractionMicroserviceApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(InteractionMicroserviceApplication.class, args);
    }
    @Autowired
    private WorkerProxy workerProxy;

    @Autowired
    private ClickService clickService;
    @Autowired
    private ImpressionService impressionService;
    @Autowired
    private WishService wishService;

    @Autowired
    private ImpressionRepo impressionRepo;

    @Autowired
    private CompanyProxy companyProxy;
    @Override
    public void run(String... args) throws Exception {
//        if(impressionRepo.count() >0) return;

        List<String> workers=workerProxy.getWorkers();
        List<String> companies=companyProxy.getCompanies();

        for (String worker : workers) {
            System.out.println(">>>> "+worker);
            for (String company : companies) {
                System.out.println("<<<<< "+company);
                WishDto wishDto = new WishDto();
                wishDto.setIdWorker(worker);

                ClickDto clickDto = new ClickDto();
                clickDto.setIdWorker(worker);

                ImpressionDto impressionDto = new ImpressionDto();
                impressionDto.setIdWorker(worker);

                impressionService.newImpression(impressionDto,company);

                clickService.newClick(clickDto,company);

                wishService.newWish(wishDto,company);
            }
        }
    }
}
