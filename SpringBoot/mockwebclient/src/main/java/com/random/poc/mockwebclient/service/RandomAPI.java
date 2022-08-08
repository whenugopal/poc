package com.random.poc.mockwebclient.service;

import com.random.poc.mockwebclient.model.Response;
import org.apache.juli.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class RandomAPI {

    private Logger logger = LoggerFactory.getLogger(RandomAPI.class);

    @Autowired
    WebClient webClient;

    public Mono<Response> fetch() {
        logger.info("Inside The fetch ?");
        return webClient
                .get()
                .uri("https://api.agify.io?name=michael")
                .exchangeToMono(res -> {
                            if(res.statusCode().isError()) {
                                return Mono.empty();
                            } else {
                                return res.bodyToMono(Response.class);
                            }
                })
                .doFinally(res -> logger.info("hai"))
                .doOnError(res -> logger.info("Error"));
                //.exchangeToMono(res -> res.bodyToMono(Response.class));
    }
}
