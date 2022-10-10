package com.test.promotionengine.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PromotionResponse {
    private Double result;
    private HttpStatus responseStatus;
    private String message;

}
