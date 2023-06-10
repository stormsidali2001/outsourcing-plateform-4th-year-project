package com.example.companymicroservice.controller;
import com.example.companymicroservice.Entities.company.Card;
import com.example.companymicroservice.dto.CartItemDto;
import com.example.companymicroservice.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CardController {

    @Autowired
    CardService cardService;


@PostMapping("new-card")
public String newCard(@RequestBody List<CartItemDto> cardDto, @RequestHeader("X-userId") String companyId) {
    return cardService.newCard(cardDto,companyId);
}

    @PostMapping("new-cardItem")
    public String newCardItem(@RequestBody CartItemDto cardDto, @RequestHeader("X-userId") String companyId) {
        return cardService.newCardItem(cardDto,companyId);
    }


    //get a card by IdCompany
  @GetMapping("card/{companyId}")
    public Card getCard(@PathVariable("companyId") String companyId){
        return cardService.getCardByIdCompany(companyId);
 }

 @DeleteMapping("card/cardItem/delete/{idWorker}")
    public String deleteCardItem(@PathVariable("idWorker")String idWorker ,@RequestHeader("X-userId") String companyId){
       return cardService.deleteCardItem(idWorker,companyId);
    }
@PutMapping("card/cardItem/update/{idWorker}")
    public String updateCardItemHours(@PathVariable("idWorker")String idWorker ,@RequestHeader("X-userId")String companyId,@RequestBody int newHours){
        return cardService.updateItemHours(idWorker,companyId,newHours);
}

}
