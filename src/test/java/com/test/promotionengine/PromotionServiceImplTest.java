package com.test.promotionengine;

import com.test.promotionengine.exception.PromotionException;
import com.test.promotionengine.model.Product;
import com.test.promotionengine.model.PromotedProduct;
import com.test.promotionengine.model.PromotionResponse;
import com.test.promotionengine.repository.impl.PromotionRepositoryImpl;
import com.test.promotionengine.service.impl.PromotionServiceImpl;
import org.json.simple.parser.ParseException;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PromotionServiceImplTest {
    private final PromotionRepositoryImpl repository = Mockito.mock(PromotionRepositoryImpl.class);

    private final PromotionServiceImpl service = new PromotionServiceImpl(repository);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    public void init() throws IOException, ParseException {
        List<PromotedProduct> promotedProductList = new ArrayList<>();
        List<String> skuList = List.of("C","D");

        PromotedProduct promotedProduct1 = new PromotedProduct(skuList,1,30);
        promotedProductList.add(promotedProduct1);
        Mockito.when(repository.getBulkPromotionOffers()).thenReturn(promotedProductList);

        List<PromotedProduct> promotedProductList1 = new ArrayList<>();
        List<String> skuList1 = List.of("A");
        List<String> skuList2 = List.of("B");

        PromotedProduct promotedProduct2 = new PromotedProduct(skuList1,3,130);
        PromotedProduct promotedProduct3 = new PromotedProduct(skuList2,2,45);
        promotedProductList1.add(promotedProduct2);
        promotedProductList1.add(promotedProduct3);
        Mockito.when(repository.getPromotionOffers()).thenReturn(promotedProductList1);
    }

    @Test
    public void TestGetPromotedPriceScenarioA() throws IOException, ParseException {
        init();
        Map<String, Long> cartMap = new HashMap<>();
        cartMap.put("A",1L);
        cartMap.put("B",1L);
        cartMap.put("C",1L);
        List<Product> updatedCartList = new ArrayList<>();
        Product product1 = new Product("A", 1, "50");
        Product product2 = new Product("B", 1, "30");
        Product product3 = new Product("C", 1, "20");
        updatedCartList.add(product1);
        updatedCartList.add(product2);
        updatedCartList.add(product3);
        Mockito.when(repository.getCartInformation(cartMap)).thenReturn(updatedCartList);

        assertEquals(service.getPromotedPrice(cartMap),PromotionResponse.builder().result(100.0).responseStatus(HttpStatus.OK).message("Success").build());
    }

    @Test
    public void TestGetPromotedPriceScenarioB() throws IOException, ParseException {
        init();
        Map<String, Long> cartMap = new HashMap<>();
        cartMap.put("A",5L);
        cartMap.put("B",5L);
        cartMap.put("C",1L);
        List<Product> updatedCartList = new ArrayList<>();
        Product product1 = new Product("A", 5, "50");
        Product product2 = new Product("B", 5, "30");
        Product product3 = new Product("C", 1, "20");
        updatedCartList.add(product1);
        updatedCartList.add(product2);
        updatedCartList.add(product3);
        Mockito.when(repository.getCartInformation(cartMap)).thenReturn(updatedCartList);

        assertEquals(service.getPromotedPrice(cartMap),PromotionResponse.builder().result(370.0).responseStatus(HttpStatus.OK).message("Success").build());
    }

    @Test
    public void TestGetPromotedPriceScenarioC() throws IOException, ParseException {
        init();
        Map<String, Long> cartMap = new HashMap<>();
        cartMap.put("A",3L);
        cartMap.put("B",5L);
        cartMap.put("C",1L);
        cartMap.put("D",1L);
        List<Product> updatedCartList = new ArrayList<>();
        Product product1 = new Product("A", 3, "50");
        Product product2 = new Product("B", 5, "30");
        updatedCartList.add(product1);
        updatedCartList.add(product2);
        Mockito.when(repository.getCartInformation(cartMap)).thenReturn(updatedCartList);

            assertEquals(service.getPromotedPrice(cartMap),PromotionResponse.builder().result(280.0).responseStatus(HttpStatus.OK).message("Success").build());
    }

    @Test
    public void TestGetPromotedPriceScenarioException() throws IOException, ParseException {
        init();
        thrown.expect(ParseException.class);
        Map<String, Long> cartMap = new HashMap<>();
        cartMap.put("A",3L);
        cartMap.put("B",5L);
        cartMap.put("C",1L);
        cartMap.put("D",1L);
        Mockito.when(repository.getCartInformation(cartMap)).thenThrow(ParseException.class);

        assertEquals(service.getPromotedPrice(cartMap),PromotionResponse.builder().result(null).responseStatus(HttpStatus.EXPECTATION_FAILED).message(null).build());
    }

    @Test
    public void TestGetPromotedPriceScenarioBadRequest() throws IOException, ParseException {
        init();
        thrown.expect(PromotionException.class);
        Map<String, Long> cartMap = new HashMap<>();
        cartMap.put("A",3L);
        cartMap.put("B",5L);
        cartMap.put("C",1L);
        cartMap.put("D",1L);
        cartMap.put("F",1L);
        List<Product> updatedCartList = new ArrayList<>();
        Product product1 = new Product("A", 3, "50");
        Product product2 = new Product("B", 5, "30");
        updatedCartList.add(product1);
        updatedCartList.add(product2);
        Mockito.when(repository.getCartInformation(cartMap)).thenReturn(updatedCartList);

        assertEquals(service.getPromotedPrice(cartMap),PromotionResponse.builder().result(null).responseStatus(HttpStatus.BAD_REQUEST).message("Please enter valid products!").build());
    }
}
