package com.me.reactivetutorial.routers;

import com.me.reactivetutorial.db.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MainRouterTest {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testRoute() {
        webTestClient.get().uri("/test")
                .exchange()
                .expectStatus().isOk().expectBody(String.class).isEqualTo("Hello World");
    }

    @Test
    @DisplayName("All PRODUCTS EMPTY")
    void productListingNull() {
        webTestClient.get().uri("/products")
                .headers(httpHeaders -> httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON)))
                .exchange()
                .expectStatus().isOk().expectBodyList(Product.class);

    }
    @Test
    @DisplayName("All PRODUCTS FOUND")
    void productListingNotNull() {
        webTestClient.get().uri("/products")
                .headers(httpHeaders -> httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON)))
                .exchange()
                .expectStatus().isOk().expectBodyList(Product.class);

    }

    @Test
    @DisplayName(" PRODUCTS FOUND BY FIELD")
    void productListingByFieldNotNull() {
        webTestClient.get().uri("/products?name=name2")
                .headers(httpHeaders -> httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON)))
                .exchange()
                .expectStatus().isOk().expectBodyList(Product.class);

    }
    @Test
    @DisplayName("One product not found")
    void singleProductNull()
    {
        webTestClient.get().uri("/products/20")
                .headers(httpHeaders -> httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON)))
                .exchange()
                .expectStatus().isNotFound().expectBody(Product.class).isEqualTo(null);
    }

    @Test
    @DisplayName("One product  found")
    void singleProductNotNull()
    {
        webTestClient.get().uri("/products/2")
                .headers(httpHeaders -> httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON)))
                .exchange()
                .expectStatus().isOk().expectBody().jsonPath("$.id").isEqualTo(2);
    }

    @Test
    @DisplayName("ADD PRODUCT")
    void testAddProduct()
    {
        webTestClient.post().uri("/products")
                .body(Mono.just(Product.builder().brand("Brand1").name("name").build()), Product.class)
                .headers(httpHeaders -> httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON)))
                .exchange()
                .expectStatus().isOk().expectBody().jsonPath("$.name").isEqualTo("name")
                .jsonPath("$.brand").isEqualTo("Brand1")
                ;
    }
    @Test
    @DisplayName("TEST DB REFRESH")
    void testDBRefresh()
    {
        webTestClient.options().uri("/refresh")
                .headers(httpHeaders -> httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON)))
                .exchange()
                .expectStatus().isOk().expectBodyList(Product.class);
        ;
    }
}