package com.me.reactivetutorial.services;


import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface ProductLookupService {
    Mono<ServerResponse> findById(String id);
    Mono<ServerResponse> findByAnyField(ServerRequest request);

    Mono<ServerResponse> refreshDB();

}
