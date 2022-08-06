package com.random.poc.mockwebclient.controller;

import com.random.poc.mockwebclient.model.Response;
import com.random.poc.mockwebclient.service.RandomAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class RandomController {

    @Autowired
    RandomAPI randomAPI;

    @GetMapping("test")
    public Mono<Response> test() {
        return randomAPI.fetch();
    }

}
