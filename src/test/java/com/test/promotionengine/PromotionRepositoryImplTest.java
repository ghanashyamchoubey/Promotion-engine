package com.test.promotionengine;

import com.test.promotionengine.model.Product;
import com.test.promotionengine.model.PromotedProduct;
import com.test.promotionengine.repository.impl.PromotionRepositoryImpl;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PromotionRepositoryImplTest {

    private final PromotionRepositoryImpl repository = new PromotionRepositoryImpl();

    @Test
    public void TestGetPromotionOffers() throws IOException, ParseException {
        List<PromotedProduct> promotedProductList = new ArrayList<>();
        List<String> skuList = List.of("A");
        List<String> skuList2 = List.of("B");

        PromotedProduct promotedProduct1 = new PromotedProduct(skuList,3,130);
        PromotedProduct promotedProduct2 = new PromotedProduct(skuList2,2,45);
        promotedProductList.add(promotedProduct1);
        promotedProductList.add(promotedProduct2);
        assertEquals(repository.getPromotionOffers(), promotedProductList);
    }

    @Test
    public void TestGetBulkPromotionOffers() throws IOException, ParseException {
        List<PromotedProduct> promotedProductList = new ArrayList<>();
        List<String> skuList = List.of("C","D");

        PromotedProduct promotedProduct1 = new PromotedProduct(skuList,1,30);
        promotedProductList.add(promotedProduct1);
        assertEquals(repository.getBulkPromotionOffers(), promotedProductList);
    }

    @Test
    public void TestGetCartInformation() throws IOException, ParseException {
        Map<String, Long> cartMap = new HashMap<>();
        cartMap.put("A",3L);
        cartMap.put("B",2L);
        cartMap.put("C",1L);
        List<Product> updatedCartList = new ArrayList<>();
        Product product1 = new Product("A", 3, "50");
        Product product2 = new Product("B", 2, "30");
        Product product3 = new Product("C", 1, "20");
        updatedCartList.add(product1);
        updatedCartList.add(product2);
        updatedCartList.add(product3);
        assertEquals(repository.getCartInformation(cartMap), updatedCartList);
    }
}
