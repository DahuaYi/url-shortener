package com.urlshortener.shortener.service;

import com.urlshortener.shortener.entity.UrlMapping;
import com.urlshortener.shortener.repository.UrlMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UrlService {
    @Autowired
    private UrlMappingRepository urlMappingRepository;

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int SHORT_CODE_LENGTH = 6;
    private final Random random = new Random();

    public UrlMapping shortenUrl(String originalUrl) {
        String shortCode = generateShortCode();
        UrlMapping urlMapping = new UrlMapping(originalUrl, shortCode);
        return urlMappingRepository.save(urlMapping);
    }

    private String generateShortCode() {
        StringBuilder sb = new StringBuilder(SHORT_CODE_LENGTH);
        for (int i = 0; i < SHORT_CODE_LENGTH; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    public String getAndTrackOriginalUrl(String shortCode) {
        return urlMappingRepository.findByShortCode(shortCode).map(urlMapping -> {
            urlMapping.setRedirectCount(urlMapping.getRedirectCount() + 1);
            urlMappingRepository.save(urlMapping);
            return urlMapping.getOriginalUrl();
        }).orElse(null);
    }

    public java.util.Map<String, Object> getAnalytics(String shortCode) {
        return urlMappingRepository.findByShortCode(shortCode).map(urlMapping -> {
            java.util.Map<String, Object> map = new java.util.HashMap<>();
            map.put("originalUrl", urlMapping.getOriginalUrl());
            map.put("shortCode", urlMapping.getShortCode());
            map.put("redirectCount", urlMapping.getRedirectCount());
            map.put("createdAt", urlMapping.getCreatedAt());
            return map;
        }).orElse(null);
    }
}
