package com.test.promotionengine.service.impl;

import com.test.promotionengine.exception.PromotionException;
import com.test.promotionengine.model.Product;
import com.test.promotionengine.model.PromotedProduct;
import com.test.promotionengine.model.PromotionResponse;
import com.test.promotionengine.repository.PromotionRepository;
import com.test.promotionengine.service.PromotionService;
import com.test.promotionengine.validator.PromotionValidator;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class PromotionServiceImpl implements PromotionService {

    private PromotionRepository promotionRepository;
    private List<PromotedProduct> promotedProductList;
    private List<PromotedProduct> bulkPromotedProductList;
    private List<Product> cartInformationList;

    public PromotionServiceImpl(PromotionRepository promotionRepository){
        this.promotionRepository = promotionRepository;
    }
    @Override
    public PromotionResponse getPromotedPrice(Map<String, Long> cartMap){
        double individualProductPrice = 0.0;
        double finalPrice = 0.0;
        try {
            promotedProductList = promotionRepository.getPromotionOffers();
            bulkPromotedProductList = promotionRepository.getBulkPromotionOffers();

            List<String> orderedSkuList = new ArrayList<>();
            finalPrice = getBulkItemsFinalPrice(cartMap, finalPrice, orderedSkuList, bulkPromotedProductList);
            cartInformationList = promotionRepository.getCartInformation(cartMap);
            if(!PromotionValidator.isValidProduct(cartMap, cartInformationList)){
                throw new PromotionException("Please enter valid products!");
            }
            for (Product item : cartInformationList) {
                long orderedQty = item.getOrderedQuantity();
                String orderedProduct = item.getSkuId();
                for (PromotedProduct promotedProduct : promotedProductList) {
                    if (promotedProduct.getSkuIds().contains(orderedProduct)) {
                        List<String> offeredProducts = promotedProduct.getSkuIds();
                        long offeredQty = promotedProduct.getQuantity();
                        individualProductPrice = calculateIndividualProductPrice(offeredProducts, offeredQty, orderedQty, item, promotedProduct);
                        break;
                    }
                }

            if (individualProductPrice == 0){
                    individualProductPrice = orderedQty * Double.valueOf(item.getPrice());
                }
                finalPrice += individualProductPrice;
                individualProductPrice = 0.0;
            }

        }catch (PromotionException e) {
            return PromotionResponse.builder()
                    .responseStatus(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .result(null)
                    .build();
        }
        catch (IOException | ParseException e) {
            return PromotionResponse.builder()
                    .responseStatus(HttpStatus.EXPECTATION_FAILED)
                    .message(e.getMessage())
                    .result(null)
                    .build();
        }
        return responseBuilder(finalPrice);
    }

    private static double getBulkItemsFinalPrice(Map<String, Long> cartMap, double finalPrice, List<String> orderedSkuList, List<PromotedProduct> bulkPromotedProductList) {
        cartMap.forEach((key, value) -> orderedSkuList.add(key));
        for(PromotedProduct promotedProduct : bulkPromotedProductList){
            if(orderedSkuList.containsAll(promotedProduct.getSkuIds())){
                for(String skuId : promotedProduct.getSkuIds()){
                    cartMap.put(skuId,cartMap.get(skuId)-1);
                }
                finalPrice = promotedProduct.getFinalPrice();
            }
        }
        return finalPrice;
    }

    private double calculateIndividualProductPrice(List<String> offeredProducts, long offeredQty, long orderedQty, Product product, PromotedProduct promotedProduct) {
        int tempPrice = 0;
        while (orderedQty >= offeredQty && offeredProducts.size() == 1) {
            tempPrice += promotedProduct.getFinalPrice();
            orderedQty -= offeredQty;
        }
        return tempPrice + (orderedQty * Double.valueOf(product.getPrice()));
    }

    private PromotionResponse responseBuilder(Double finalPrice) {
        return PromotionResponse.builder()
                .responseStatus(HttpStatus.OK)
                .result(finalPrice)
                .message("Success")
                .build();
    }
}
