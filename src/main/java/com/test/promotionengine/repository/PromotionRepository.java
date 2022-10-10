package com.test.promotionengine.repository;

import com.test.promotionengine.model.Product;
import com.test.promotionengine.model.PromotedProduct;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface PromotionRepository {

    /**
     * Returns the offers that are currently active on individual products.
     * @return List of products
     */
    public List<PromotedProduct> getPromotionOffers() throws IOException, ParseException;

    /**
     * Returns the offers that are currently active on bulk products.
     * @return List of products
     */
    public List<PromotedProduct> getBulkPromotionOffers() throws IOException, ParseException;

    /**
     * Returns details of the items that are in cart.
     * @return List of Products
     */
    public List<Product> getCartInformation(Map<String, Long> cartMap) throws IOException, ParseException;
}
