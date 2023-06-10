package com.example.companymicroservice.service;

import com.example.companymicroservice.Entities.company.Card;
import com.example.companymicroservice.Entities.company.CardItem;
import com.example.companymicroservice.Entities.company.Skill;
import com.example.companymicroservice.dto.CartItemDto;
import com.example.companymicroservice.dto.skillDto;
import com.example.companymicroservice.repositpries.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CardService {

    @Autowired
    CardRepository cardRepository;


    public ResponseEntity<String>  newCardItem(CartItemDto cartItemDto, String companyId){
        Card card=cardRepository.findCardByCompanyId(companyId);
        if(card != null){
            CardItem cardItem = mapToCardItem(cartItemDto); // Convert CartItemDto to CardItem

            if(!this.doesCardItemExist(cardItem.getWorkerId(),card)){
                List<CardItem> cardItems = card.getCardItems();
                cardItems.add(cardItem); // Add the new cardItem to the list
                card.setCardItems(cardItems); // Update the cardItems list in the card
                cardRepository.save(card); // Save the updated card
                return ResponseEntity.status(HttpStatus.CREATED).body("worker added to Pannier");
            } else {
                return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("this worker is already selected");
            }



        }else {
            List<CartItemDto> cartItemDtos = new ArrayList<>(); // List of CartItemDto objects
            cartItemDtos.add(cartItemDto);
            List<CardItem> cardItems = cartItemDtos.stream()
                    .map(this::mapToCardItem)
                    .toList();
            Card card1=Card.builder()
                    .companyId(companyId)
                    .cardItems(cardItems)
                    .build();
            cardRepository.save(card1);
        }

     return ResponseEntity.status(HttpStatus.CREATED).body("Worker added to Pannier");
    }



    private CardItem mapToCardItem(CartItemDto cartItemDto) {
        return CardItem.builder()
                .workerId(cartItemDto.getWorkerId())
                .firstName(cartItemDto.getFirstName())
                .lastName(cartItemDto.getLastName())
                .category(cartItemDto.getCategory())
                .nbHours(1)
                .publicPrice(cartItemDto.getPublicPrice())
                .skills(cartItemDto.getSkills().stream().map(this::mapToSkill).toList())
                .build();
    }
    private Skill mapToSkill(skillDto skillDto){
        return Skill.builder()
                .name(skillDto.getName())
                .category(skillDto.getCategory())
                .build();
    }

    public ResponseEntity<Object> getCardByIdCompany(String companyId){

        return ResponseEntity.ok(cardRepository.findCardByCompanyId(companyId));

    }

    public ResponseEntity<String> deleteCardItem(String idWorker,String idCompany){
        Card card=cardRepository.findCardByCompanyId(idCompany);
        if(card!=null){
            if (this.doesCardItemExist(idWorker, card)) {

                card.getCardItems().removeIf(cardItem -> cardItem.getWorkerId().equals(idWorker));
                cardRepository.save(card);
                return ResponseEntity.ok("item deleted");
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("item not found !");
            }

        }else {
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body("item not found !");
        }

    }
    public String updateItemHours(String idWorker, String idCompany, int newHours) {
        Card card = cardRepository.findCardByCompanyId(idCompany);
        if (card != null) {
            CardItem cardItemToUpdate = card.getCardItems().stream()
                    .filter(cardItem -> cardItem.getWorkerId().equals(idWorker))
                    .findFirst()
                    .orElse(null);

            if (cardItemToUpdate != null) {
                cardItemToUpdate.setNbHours(newHours);
                cardRepository.save(card);
                return "Item hours updated";
            } else {
                return "Item not found";
            }
        } else {
            return "No Card found";
        }
    }

    private boolean doesCardItemExist(String idWorker, Card card) {
        if (card != null) {
            return card.getCardItems().stream().anyMatch(cardItem -> cardItem.getWorkerId().equals(idWorker));
        }
        return false;
    }


}

