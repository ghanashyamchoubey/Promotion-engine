package com.test.promotionengine.repository.impl;

import com.test.promotionengine.constant.Constants;
import com.test.promotionengine.model.Product;
import com.test.promotionengine.model.PromotedProduct;
import com.test.promotionengine.repository.PromotionRepository;
import com.test.promotionengine.util.PromotionUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class PromotionRepositoryImpl implements PromotionRepository {

    @Override
    public List<PromotedProduct> getPromotionOffers() throws IOException, ParseException {
        List<PromotedProduct> promotedProductList = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(new FileReader("src/main/resources/activeindividualoffers.json"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray offerArray = (JSONArray) jsonObject.get(Constants.OFFER);
        return PromotionUtil.getPromotedProductList(promotedProductList, offerArray);
    }

    @Override
    public List<PromotedProduct> getBulkPromotionOffers() throws IOException, ParseException {
        List<PromotedProduct> promotedProductList = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(new FileReader("src/main/resources/activebulkoffers.json"));
        JSONObject jsonObject = (JSONObject) obj;
        JSONArray offerArray = (JSONArray) jsonObject.get(Constants.BULK_OFFER);
        return PromotionUtil.getPromotedProductList(promotedProductList, offerArray);
    }



    @Override
    public List<Product> getCartInformation(Map<String, Long> cartMap) throws IOException, ParseException {
        Map<String, String> skuInformationMap = PromotionUtil.populateSkuInformation();
        List<Product> updatedCartList = new ArrayList<>();
            for (Map.Entry item : cartMap.entrySet()) {
                Product product = new Product();
                String skuId = (String) item.getKey();
                long quantity = (long) item.getValue();
                if (skuInformationMap.containsKey(skuId) && quantity > 0){
                    product.setSkuId(skuId);
                    product.setPrice(skuInformationMap.get(skuId));
                    product.setOrderedQuantity(quantity);
                    updatedCartList.add(product);
                }
            }
        return updatedCartList;
    }
}
