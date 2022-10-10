package com.test.promotionengine.validator;

import com.test.promotionengine.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PromotionValidator {

    private PromotionValidator(){}

    public static boolean isValidProduct(Map<String, Long> cartMap, List<Product> cartInformationList) {
        List<String> skuList = new ArrayList<>();
        List<String> cartItemList = new ArrayList<>();
        Map<String, Long> newMap = cartMap.entrySet().stream().filter(x -> x.getValue()>0).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        newMap.forEach((key, value) -> cartItemList.add(key));
        for(Product items : cartInformationList){
            skuList.add(items.getSkuId());
        }
        return skuList.containsAll(cartItemList);
    }

}
