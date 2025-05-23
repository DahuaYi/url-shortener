package com.urlshortener.shortener;

import org.junit.jupiter.api.Test;
import java.lang.reflect.Method;
import static org.junit.jupiter.api.Assertions.*;

public class UrlServiceTest {
    @Test
    void testGenerateShortCodeLength() throws Exception {
        // Use reflection to test the private generateShortCode method
        var serviceClass = Class.forName("com.urlshortener.shortener.service.UrlService");
        Object service = serviceClass.getDeclaredConstructor().newInstance();
        Method method = serviceClass.getDeclaredMethod("generateShortCode");
        method.setAccessible(true);
        String code = (String) method.invoke(service);
        assertEquals(6, code.length());
    }
} 