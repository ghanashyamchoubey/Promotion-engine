package com.test.promotionengine.exception;

public class PromotionException extends Exception{

    public PromotionException() {
    }

    public PromotionException(String message) {
        super(message);
    }

    public PromotionException(String message, Throwable cause) {
        super(message, cause);
    }
}
