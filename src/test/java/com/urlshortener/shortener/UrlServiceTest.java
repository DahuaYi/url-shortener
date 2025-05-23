package com.urlshortener.shortener;

import org.junit.jupiter.api.Test;
import java.lang.reflect.Method;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashSet;

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

    @Test
    void testShortCodeUniqueness() throws Exception {
        var serviceClass = Class.forName("com.urlshortener.shortener.service.UrlService");
        Object service = serviceClass.getDeclaredConstructor().newInstance();
        Method method = serviceClass.getDeclaredMethod("generateShortCode");
        method.setAccessible(true);
        HashSet<String> codes = new HashSet<>();
        for (int i = 0; i < 1000; i++) {
            String code = (String) method.invoke(service);
            assertFalse(codes.contains(code), "Duplicate code found: " + code);
            codes.add(code);
        }
    }

    @Test
    void testIsValidUrlTrue() throws Exception {
        var serviceClass = Class.forName("com.urlshortener.shortener.service.UrlService");
        Object service = serviceClass.getDeclaredConstructor().newInstance();
        Method method = serviceClass.getDeclaredMethod("isValidUrl", String.class);
        method.setAccessible(true);
        assertTrue((Boolean) method.invoke(service, "https://example.com"));
        assertTrue((Boolean) method.invoke(service, "http://localhost:8080/test"));
    }

    @Test
    void testIsValidUrlFalse() throws Exception {
        var serviceClass = Class.forName("com.urlshortener.shortener.service.UrlService");
        Object service = serviceClass.getDeclaredConstructor().newInstance();
        Method method = serviceClass.getDeclaredMethod("isValidUrl", String.class);
        method.setAccessible(true);
        assertFalse((Boolean) method.invoke(service, "not_a_url"));
        assertFalse((Boolean) method.invoke(service, "htp:/bad-url"));
    }
} 