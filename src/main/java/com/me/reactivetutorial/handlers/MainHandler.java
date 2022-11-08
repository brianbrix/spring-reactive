package com.me.reactivetutorial.handlers;

import com.me.reactivetutorial.db.entity.Product;
import com.me.reactivetutorial.db.repos.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Deprecated
public class MainHandler {


    private final ProductRepo productRepo;
    public Mono<ServerResponse> testHandler(ServerRequest serverRequest)
    {
        System.out.println("Hello");
        return ServerResponse.ok().body(BodyInserters.fromValue("Hello World"));
    }

    public  Mono<ServerResponse> testHandler2(ServerRequest serverRequest)
    {
        return ServerResponse.ok().body(BodyInserters.empty());
    }
//
//    public Mono<ServerResponse> getProducts(ServerRequest serverRequest)
//    {
//    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(productRepo.findAll(), Product.class);
//    }

    public Mono<ServerResponse> getProduct(ServerRequest serverRequest)
    {
        Mono<Product> productMono = productRepo.findById(Long.parseLong(serverRequest.pathVariable("id")));
        return productMono.flatMap(product -> ServerResponse.ok().body(BodyInserters.fromValue(product))).switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> addProduct(ServerRequest serverRequest)
    {
        Mono<Product> productMono = serverRequest.bodyToMono(Product.class);
        return productMono.flatMap(product ->
            ServerResponse.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(productRepo.save(product), Product.class));
    }




}
