package com.example.workermicroservice.service;

import com.example.workermicroservice.Entities.company.Company;
import com.example.workermicroservice.Entities.company.SocialMediaLink;
import com.example.workermicroservice.dto.SignUpCompanyDto;
import com.example.workermicroservice.dto.SocialMediaLinkDto;
import com.example.workermicroservice.repositpries.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public String signupCompany(SignUpCompanyDto company){
        companyRepository.save(
                Company.builder()
                        .name(company.getName())
                        .field(company.getField())
                        .website(company.getWebsite())
                        .type(company.getType())
                        .size(company.getSize())
                        .socialMediaLinks(company.getSocialMediaLinks().stream().map(this::mapToSocialMediaLink).toList())
                .build()
        );

        return "company registered succesfully";
    }

    private SocialMediaLink mapToSocialMediaLink(SocialMediaLinkDto s){
        return SocialMediaLink.builder()
                .name(s.getName())
                .url(s.getUrl())
                .build();
    }

}
