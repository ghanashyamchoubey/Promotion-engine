package com.test.promotionengine.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotedProduct {
    private List<String> skuIds;
    private long quantity;
    private long finalPrice;

}
