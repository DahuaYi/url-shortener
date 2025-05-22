package com.urlshortener.shortener.controller;

import com.urlshortener.shortener.entity.UrlMapping;
import com.urlshortener.shortener.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class UrlController {
    @Autowired
    private UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<Map<String, String>> shortenUrl(@RequestBody Map<String, String> request) {
        String originalUrl = request.get("url");
        UrlMapping urlMapping = urlService.shortenUrl(originalUrl);
        return ResponseEntity.ok(Map.of("shortCode", urlMapping.getShortCode()));
    }

    @GetMapping("/analytics/{shortCode}")
    public ResponseEntity<Map<String, Object>> getAnalytics(@PathVariable String shortCode) {
        Map<String, Object> analytics = urlService.getAnalytics(shortCode);
        if (analytics != null) {
            return ResponseEntity.ok(analytics);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
