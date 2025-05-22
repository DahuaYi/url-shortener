package com.urlshortener.shortener.controller;

import com.urlshortener.shortener.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedirectController {
    @Autowired
    private UrlService urlService;

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirectToOriginalUrl(@PathVariable String shortCode) {
        String originalUrl = urlService.getAndTrackOriginalUrl(shortCode);
        if (originalUrl != null) {
            return ResponseEntity.status(302).header("Location", originalUrl).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
} 