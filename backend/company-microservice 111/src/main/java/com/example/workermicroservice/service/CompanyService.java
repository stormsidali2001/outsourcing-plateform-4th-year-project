package com.example.companymicroservice.service;

import com.example.companymicroservice.Entities.company.Company;
import com.example.companymicroservice.Entities.company.SocialMediaLink;
import com.example.companymicroservice.dto.SignUpCompanyDto;
import com.example.companymicroservice.dto.SocialMediaLinkDto;
import com.example.companymicroservice.repositpries.CompanyRepository;
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
