package com.test.promotionengine.controller;

import com.test.promotionengine.service.PromotionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "/promotion")
public class PromotionController {

    private PromotionService promotionService;

    public PromotionController(PromotionService promotionService){
        this.promotionService = promotionService;
    }

    /**
     * Check final price after applying promotion URL
     * @param cartMap
     * @return ResponseEntity
     */
    @PostMapping(path = "/checkPrice", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> checkFinalPrice(@RequestBody Map<String, Long> cartMap) {
        return ResponseEntity.ok(promotionService.getPromotedPrice(cartMap));
    }


    /**
     * HealthCheck URL
     * @return Pong
     */
    @GetMapping(path = "/ping", produces = "application/json")
    public ResponseEntity<?> healthCheck() {
        return new ResponseEntity<>("Pong", HttpStatus.OK);
    }
}
