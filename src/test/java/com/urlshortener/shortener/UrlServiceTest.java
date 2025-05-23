package com.urlshortener.shortener;

import com.urlshortener.shortener.service.UrlService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UrlServiceTest {
    @Test
    void testGenerateShortCodeLength() {
        UrlService service = new UrlService();
        String code = service.shortenUrl("https://example.com").getShortCode();
        assertEquals(6, code.length());
    }
} 