package com.me.reactivetutorial.db.repos;

import com.me.reactivetutorial.db.entity.Product;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepo extends R2dbcRepository<Product, Long> {
    @Query("SELECT * FROM product where name=:name or brand =:brand")
    Flux<Product> findByNameAndBrand(String name, String brand);

}
