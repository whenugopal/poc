package com.random.poc.mockwebclient.service;

import com.random.poc.mockwebclient.model.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.function.Function;

import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {RandomAPI.class})
@ExtendWith(SpringExtension.class)
class RandomAPITest {
    @Autowired
    private RandomAPI randomAPI;

    @MockBean
    private WebClient webClient;

    @MockBean
    private ClientResponse mockedClientResponse;

    /**
     * Method under test: {@link RandomAPI#fetch()}
     */
    @Test
    void testFetch() {

        final var uriSpecMock = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
        final var headersSpecMock = Mockito.mock(WebClient.RequestHeadersSpec.class);
        when(mockedClientResponse.statusCode()).thenReturn(HttpStatus.OK);
//        when(mockedClientResponse.statusCode().isError()).thenReturn(Boolean.FALSE);
        Mockito.doAnswer(invocationOnMock -> {
                    return invocationOnMock
                            .<Function<ClientResponse, Mono<Response>>>getArgument(0)
                            .apply(mockedClientResponse);
                })
                .when(headersSpecMock)
                .exchangeToMono(ArgumentMatchers.<Function<ClientResponse, Mono<Response>>>any());
        when(webClient.get()).thenReturn(uriSpecMock);
        when(uriSpecMock.uri(ArgumentMatchers.<String>notNull())).thenReturn(headersSpecMock);
        randomAPI.fetch();

    }
}

