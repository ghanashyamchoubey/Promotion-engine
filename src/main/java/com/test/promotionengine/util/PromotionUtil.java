package com.test.promotionengine.util;

import com.test.promotionengine.constant.Constants;
import com.test.promotionengine.model.PromotedProduct;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PromotionUtil {
    private PromotionUtil(){};

    public static Map<String, String> populateSkuInformation() throws IOException, ParseException {
        Map<String, String> skuInformationMap = new HashMap<>();
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(new FileReader("src/main/resources/skulist.json"));
        JSONObject jsonObject = (JSONObject) obj;
        JSONArray skuArray = (JSONArray) jsonObject.get(Constants.SKU_ITEM_LIST);
        for (Object skuInfo : skuArray) {
            JSONObject sku = (JSONObject) skuInfo;
            String skuId = (String) sku.get(Constants.SKU_ID);
            String skuPrice = (String) sku.get(Constants.SKU_PRICE);
            skuInformationMap.put(skuId, skuPrice);
        }
        return skuInformationMap;
    }
    public static List<PromotedProduct> getPromotedProductList(List<PromotedProduct> promotedProductList, JSONArray offerArray) {
        for (Object offerInfo : offerArray) {
            JSONObject offer = (JSONObject) offerInfo;
            JSONArray skuIds = (JSONArray) offer.get(Constants.SKU_ID);
            List<String> skuIdList = new ArrayList<>();
            for (Object skuId : skuIds) {
                skuIdList.add(String.valueOf(skuId));
            }
            long quantity = (Long) offer.get(Constants.QUANTITY);
            long finalPrice = (Long) offer.get(Constants.FINAL_PRICE);
            PromotedProduct promotedProduct = new PromotedProduct(skuIdList, quantity, finalPrice);
            promotedProductList.add(promotedProduct);
        }

        return promotedProductList;
    }

}
