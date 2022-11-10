package com.me.reactivetutorial.routers;

import com.me.reactivetutorial.db.repos.ProductRepo;
import com.me.reactivetutorial.handlers.MainHandler;
import com.me.reactivetutorial.services.ProductLookupService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_PLAIN;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class MainRouter {
    private final ProductLookupService productLookupService;

    private final ProductRepo productRepo;

    public MainRouter(ProductLookupService productLookupService, ProductRepo productRepo) {
        this.productLookupService = productLookupService;
        this.productRepo = productRepo;
    }

    @Bean
    public RouterFunction<ServerResponse> testRoute(MainHandler mainHandler)
    {
        System.out.println("Hello");
        return route(GET("test").and(accept(TEXT_PLAIN)),mainHandler::testHandler);
    }

    @Bean
    public RouterFunction<ServerResponse> productListing(MainHandler mainHandler) {
       return route(
                        GET("/products"), productLookupService::findByAnyField)
               .andRoute(GET("products/{id}"),request -> productLookupService.findById(request.pathVariable("id")))
               .andRoute(POST("products").and(accept(APPLICATION_JSON)), mainHandler::addProduct)
               .andRoute(OPTIONS("refresh"),request ->productLookupService.refreshDB())
               ;

    }



}
