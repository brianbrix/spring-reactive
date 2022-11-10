package com.me.reactivetutorial.services.impl;

import com.me.reactivetutorial.db.entity.Product;
import com.me.reactivetutorial.db.repos.ProductRepo;
import com.me.reactivetutorial.services.ProductLookupService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Service
@RequiredArgsConstructor
public class ProductLookupServiceImpl implements ProductLookupService {
    private final ProductRepo productRepo;
    @Override
    public Mono<ServerResponse> findById(String id) {
        Mono<Product> productMono= productRepo.findById(Long.parseLong(id));
        return productMono.flatMap(prod ->ok().body(BodyInserters.fromValue(prod))).switchIfEmpty(ServerResponse.notFound().build());
    }

    @Override
    public Mono<ServerResponse> findByAnyField(ServerRequest req) {
        Flux<Product> productFlux;
        if (req.queryParams().isEmpty()) {
             productFlux = productRepo.findAll();
        } else {
            productFlux = productRepo.findByNameAndBrand(req.queryParam("name").orElse(null), req.queryParam("brand").orElse(null));

        }
        return ServerResponse.ok().body(productFlux,Product.class).switchIfEmpty(ServerResponse.badRequest().body(BodyInserters.fromValue("Bad request.")));
    }

    @Override
    public Mono<ServerResponse> findAllPaginated(PageRequest pageRequest) {
        return null;
    }

    @Override
    public Mono<ServerResponse> refreshDB() {
        var x =productRepo.deleteAll()
                .thenMany(
                        Flux.just("Name1:Brand1", "Name2:Brand2", "Name3:Brand3")
                                .map(name->Product.builder().name(name.split(":")[0]).brand(name.split(":")[1]).build())
                                .flatMap(productRepo::save)
                )
                .thenMany(productRepo.findAll());
        return ServerResponse.ok().body(x, Product.class);
    }

}
