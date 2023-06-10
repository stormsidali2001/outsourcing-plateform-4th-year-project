package com.example.companymicroservice.controller;

import com.example.companymicroservice.Entities.company.Card;
import com.example.companymicroservice.dto.CartItemDto;
import com.example.companymicroservice.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CardController {

    @Autowired
    CardService cardService;



    @PostMapping("new-cardItem")
    public ResponseEntity<String> newCard(@RequestBody CartItemDto cardDto, @RequestHeader("x-userid") String userId  , @RequestHeader("x-role") String role) {
        System.out.println("role "+role+" id "+userId);
       if(!role.equals("COMPANY")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("not a company khy");
       }
        return cardService.newCardItem(cardDto,userId);
    }


    //get a card by IdCompany
 @GetMapping("card")
    public ResponseEntity<Object> getCard( @RequestHeader("x-userid") String userId  , @RequestHeader("x-role") String role){
        System.out.println("role "+role+" id "+userId);
        if(!role.equals("COMPANY")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("not a company khy");
        }
    return cardService.getCardByIdCompany(userId);
 }

 @DeleteMapping("card/cardItem/delete/{idWorker}")
    public ResponseEntity<Object> deleteCardItem(@PathVariable("idWorker")String idWorker  ,@RequestHeader("x-userid") String userId  , @RequestHeader("x-role") String role){
     if(!role.equals("COMPANY")){
         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("not a company khy");
     }
        return ResponseEntity.ok(cardService.deleteCardItem(idWorker,userId));
    }
@PutMapping("card/cardItem/update/{idWorker}/nbHours/{nbHours}")
    public ResponseEntity<String> updateCardItemHours(@PathVariable("idWorker") String idWorker ,@RequestHeader("x-userid") String userId,@PathVariable("nbHours") int newHours){
        return ResponseEntity.ok(cardService.updateItemHours(idWorker,userId,newHours));
}


}
