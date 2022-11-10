package com.me.reactivetutorial.events;

import com.me.reactivetutorial.db.entity.Product;
import com.me.reactivetutorial.event.GenericAppEvent;
import com.me.reactivetutorial.event.GenericPublisher;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventTest {
    @Test
    void testEvent()
    {
        Product product = Product.builder().name("Name1").brand("Brand1").build();
//        GenericAppEvent<Product> genericAppEvent = new GenericAppEvent<>(product, true);
        GenericPublisher<Product> genericPublisher = new GenericPublisher<>();
        genericPublisher.publishEvent(product, true);
    }

    @Test
    void testFlux()
    {

//        var flx = Flux.just(new Product(1L,"Nam1", "B1"),new Product(4L,"Nam5", "B5"), new Product(6L,"Nam1", "B1"));
//        SampleSubsriber<String> ss = new SampleSubsriber<>();
//        flx.map(x->x.getBrand()+x.getName()).subscribe(ss);
//        var flx2 = Flux.just();
//        flx2.log().switchIfEmpty(x-> {
//            System.out.println("hhhell");
//            Mono.never();
//                }
//        ).subscribe(System.out::println);
//limitRate(n) -specifies number of requests accepted by downstream publisher from upstream
//        Flux.range(3, 10)
//                //                .filter(x->x<6)
//
//                .buffer(3)//buffer groups elements to groups of n
////                .limitRate()- states the maximum downstream demand -limitRate(n, 0)- means strictly n
//                .doOnRequest(r -> System.out.println("request of " + r))//count of no of requests so far
//                .subscribe(new BaseSubscriber<>() {
//
//                    @Override
//                    public void hookOnSubscribe(Subscription subscription) {
//                        request(1);
//                    }
//
//                    @Override
//                    public void hookOnNext(List<Integer> integer) {
//                        System.out.println(integer);
////                        System.out.println("Cancelling after having received " + integer);
//                        request(1);
//                    }
//                });
//
        Flux.interval(Duration.of(300, ChronoUnit.MILLIS))
                .subscribe(System.out::println);
    }
}
class SampleSubsriber<T> extends BaseSubscriber<T>
{
    public void hookOnSubscribe(Subscription subscription) {
        System.out.println("Subscribed");
        request(2);
//        requestUnbounded();// similar to request(Long.MAX_VALUE)
    }

    public void hookOnNext(T value) {
        System.out.println(value);
        request(2);
    }
}
