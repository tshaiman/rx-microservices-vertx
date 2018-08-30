package rx.basics.samples;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SingleUnitTest {

    @Test
    public void givenSingleObservable_whenSuccess_thenGetMessage() throws InterruptedException {
        String[] result = {""};
        Single<String> single = Observable.just("Hello")
                .singleOrError()
                .doOnSuccess(i -> result[0] += i)
                .doOnError(error -> {
                    throw new RuntimeException(error.getMessage());
                });

        single.map(String::toLowerCase)
                .subscribe();

        assertTrue(result[0].equals("Hello"));
    }


    @Test
    public void givenObservable_GetSinglewhenSuccess_thenGetMessage() throws InterruptedException {
        String[] result = {""};
        Single<String> single = Observable.just("Hello","world")
                .elementAtOrError(1)
                .doOnSuccess(i -> result[0] += i)
                .doOnError(error -> {
                    throw new RuntimeException(error.getMessage());
                });
        single.subscribe();
        assertTrue(result[0].equals("world"));
    }
}