package com.poc.image.controller;

import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@RestController
public class BaseController {

    @RequestMapping(value = "/test", produces = { "image/jpeg" }, method = RequestMethod.GET)
    public ResponseEntity<Mono<byte[]>> test() {
        URI uri = URI.create(
                "https://education-qa.wiley.com/wpng/api/v1/content/product/22a446cf-3a2d-4add-8176-c3af5cffdf62/cover/portrait");
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri);
        return ResponseEntity.ok().body(fetchImage(headers));
    }

    public Mono<byte[]> fetchImage(HttpHeaders uri) {
        return WebClient.builder().build()
                .get()
                .uri(uri.getLocation())
                .accept(MediaType.IMAGE_JPEG)
                .exchange()
                .flatMap(res -> {
                    if (res.statusCode().is3xxRedirection()) {
                        return fetchImage(res.headers().asHttpHeaders());
                    } else {
                        return res.bodyToMono(byte[].class);
                    }
                });

    }
}
