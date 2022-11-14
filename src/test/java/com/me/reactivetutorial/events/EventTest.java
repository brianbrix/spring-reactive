package com.me.reactivetutorial.events;

import com.me.reactivetutorial.db.entity.Product;
import com.me.reactivetutorial.event.GenericPublisher;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.function.Predicate;

@Log4j2
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventTest {
    /**
     * onErrorReturn: return fallback value for entire stream (mono/flux). E.g. if there’s a flux of 10 elements, and error happens on element 3, then rest 4,5,6… won’t be executed, instead the fallback value will be considered.
     * <p>
     * onErrorResume: return fallback value in terms on Mono/Flux for entire stream (mono/flux). E.g. if there’s a flux of 10 elements, and error happens on element 3, then rest 4,5,6… won’t be executed, instead the fallback value will be considered.
     * <p>
     * onErrorContinue: consumes (error,data) and does NOT split it over. It considers the consumer for the error elements, and leave the downstream chain as it for good elements. E.g. if there’s a flux of 10 elements, and error happens on element 3, then all elements (1 to 10) except 3 will have normal execution, but element 3 will have a different execution as mentioned in the consumer of onErrorContinue
     * <p>
     * doOnError: consumes error and spills it over. Stops execution for further elements in stream.
     * <p>
     * onErrorMap: cast one error into another. Stops execution for further elements in stream.
     */
//    @Test
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
//        Mono.fromRunnable(()->System.out.println("helllo"));
//        Flux.interval(Duration.of(300, ChronoUnit.MILLIS))->Run something every N timeunits
//                .subscribe(System.out::println);
        Scheduler scheduler = Schedulers.boundedElastic();
//        Flux<String>  fx = Flux.range(1, 20)
////                .log()
//                .map(i->{
//                    System.out.println(Thread.currentThread().getName());
//                    return 10*i;
//                })
//                .publishOn(scheduler)
//                .map(i->{
//                        System.out.println(Thread.currentThread().getName());
//
//        return "Value: "+i;
//    });
//        fx.subscribe(System.out::println);

//        Flux.just(10)
//                .map(this::doSomethingDangerous)
//                .onErrorReturn(e -> e.getMessage().equals("boom10"), "recovered10");//pass predicate ro determine on error output

//        Flux.just("key1", "key2")
//                .flatMap(k -> callExternalService(k)
//                        .onErrorResume(e -> getFromCache(k))//resume incase of error
//                );
//
//        Flux.just("timeout1")
//                .flatMap(k -> callExternalService(k))
//                .onErrorMap(original -> new BusinessException("oops, SLA exceeded", original)); ->throw CustomException
//        Stats stats = new Stats();
//        LongAdder statsCancel = new LongAdder();
//
//        Flux<String> flux =
//                Flux.just("foo", "bar")
//                        .doOnSubscribe(s -> stats.startTimer())
//                        .doFinally(type -> {
//                            stats.stopTimerAndRecordTiming();
//                            if (type == SignalType.CANCEL)
//                                statsCancel.increment();
//                        })
//                        .take(1);

        Predicate<Integer> predicate = a->a>18;
        predicate = predicate.and(b->b<100);
        System.out.println(predicate.test(106));

    }


    //onErrorReturn: return fallback value
    @Test
    public void onErrorReturnDirectly_Mono() {
        Mono.just(2)
                .map(i -> i/0) // will produce ArithmeticException
                .onErrorReturn(4)
                .subscribe(num -> log.info("Number: {}", num ));
    }

    @Test
    public void onErrorReturnIfArithmeticException_Mono() {
        Mono.just(2)
                .map(i -> i/0) // will produce ArithmeticException
                .onErrorReturn(ArithmeticException.class, 4)
                .subscribe(num -> log.info("Number: {}", num ));
    }

    @Test
    public void onErrorReturnIfPredicatePasses_Mono() {
        Mono.just(2)
                .map(i -> i/0) // will produce ArithmeticException
                .onErrorReturn(error -> error instanceof ArithmeticException, 4)
                .subscribe(num -> log.info("Number: {}", num ));
    }
    //onErrorResume: return fallback value in terms on Mono/Flux
    @Test
    public void onErrorResume_Mono() {
        Mono.just(2)
                .map(i -> i/0) // will produce ArithmeticException
                .onErrorResume(error -> Mono.just(4))
                .subscribe(num -> log.info("Number: {}", num ));
    }

    @Test
    public void onErrorResumeIfArithmeticException_Mono() {
        Mono.just(2)
                .map(i -> i/0) // will produce ArithmeticException
                .onErrorResume(
                        ArithmeticException.class,
                        error -> Mono.just(4)
                )
                .subscribe(num -> log.info("Number: {}", num ));
    }

    @Test
    public void onErrorResumeIfPredicatePasses_Mono() {
        Mono.just(2)
                .map(i -> i/0) // will produce ArithmeticException
                .onErrorResume(
                        error -> error instanceof ArithmeticException,
                        error -> Mono.just(4)
                )
                .subscribe(num -> log.info("Number: {}", num ));
    }
    //onErrorContinue: consumes (error,data) and does NOT split it over.
    @Test
    public void onErrorContinue_Mono() {
        Mono.just(2)
                .map(i -> i/0) // will produce ArithmeticException
                .onErrorContinue((error, obj) -> log.info("error:[{}], obj:[{}]", error, obj ))
                .subscribe(num -> log.info("Number: {}", num ));
    }

    @Test
    public void onErrorContinueIfArithmeticException_Mono() {
        Mono.just(2)
                .map(i -> i/0) // will produce ArithmeticException
                .onErrorContinue(
                        ArithmeticException.class,
                        (error, obj) -> log.info("error:[{}], obj:[{}]", error, obj )
                )
                .subscribe(num -> log.info("Number: {}", num ));
    }

    @Test
    public void onErrorContinueIfPredicatePasses_Mono() {
        Mono.just(2)
                .map(i -> i/0) // will produce ArithmeticException
                .onErrorContinue(
                        error -> error instanceof ArithmeticException,
                        (error, obj) -> log.info("error:[{}], obj:[{}]", error, obj )
                )
                .subscribe(num -> log.info("Number: {}", num ));
    }
    //doOnError: consumes error and spills it over
    @Test
    public void doOnError_Mono() {
        Mono.just(2)
                .map(i -> i/0) // will produce ArithmeticException
                .doOnError(error -> log.info("caught error"))
                .subscribe(num -> log.info("Number: {}", num ));
    }

    @Test
    public void doOnErrorIfArithmeticException_Mono() {
        Mono.just(2)
                .map(i -> i/0) // will produce ArithmeticException
                .doOnError(
                        ArithmeticException.class,
                        error -> log.info("caught error")
                )
                .subscribe(num -> log.info("Number: {}", num ));
    }

    @Test
    public void doOnErrorIfPredicatePasses_Mono() {
        Mono.just(2)
                .map(i -> i/0) // will produce ArithmeticException
                .doOnError(
                        error -> error instanceof ArithmeticException,
                        error -> log.info("caught error")
                )
                .subscribe(num -> log.info("Number: {}", num ));
    }
    //onErrorMap: cast one error into another
    @Test
    public void OnErrorMap_Mono() {
        Mono.just(2)
                .map(i -> i/0) // will produce ArithmeticException
                .onErrorMap(error -> new RuntimeException("SomeMathException"))
                .subscribe(num -> log.info("Number: {}", num ));
    }

    @Test
    public void OnErrorMapIfArithmeticException_Mono() {
        Mono.just(2)
                .map(i -> i/0) // will produce ArithmeticException
                .onErrorMap(
                        ArithmeticException.class,
                        error -> new RuntimeException("SomeMathException")
                )
                .subscribe(num -> log.info("Number: {}", num ));
    }

    @Test
    public void OnErrorMapIfPredicatePasses_Mono() {
        Mono.just(2)
                .map(i -> i/0) // will produce ArithmeticException
                .onErrorMap(
                        error -> error instanceof ArithmeticException,
                        error -> new RuntimeException("SomeMathException")
                )
                .subscribe(num -> log.info("Number: {}", num ));
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
