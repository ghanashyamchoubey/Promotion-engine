package com.test.promotionengine.service;

import com.test.promotionengine.model.PromotionResponse;
import java.util.Map;

/**
 * Promotion Service Interface
 */
public interface PromotionService {

    /**
     * Returns the price after promotions are applied.
     * @param cartMap
     * @return Response with finalPrice
     */
    public PromotionResponse getPromotedPrice(Map<String, Long> cartMap);
}
