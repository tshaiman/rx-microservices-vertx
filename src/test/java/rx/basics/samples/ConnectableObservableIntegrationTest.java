package rx.basics.samples;

import io.reactivex.Observable;
import io.reactivex.observables.ConnectableObservable;
import org.junit.Test;
import java.util.concurrent.TimeUnit;


import static com.jayway.awaitility.Awaitility.await;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class ConnectableObservableIntegrationTest {


    @Test
    public void givenConnectableObservable_whenConnect_thenGetMessage() throws InterruptedException {
        String[] result = {""};

        ConnectableObservable<Long> connectable
                = Observable.interval(200, TimeUnit.MILLISECONDS).publish();
        connectable.subscribe(i -> result[0] += i);
        assertFalse(result[0].equals("01"));

        connectable.connect();
        await().until(() -> assertTrue(result[0].equals("012")));

        assertTrue(result[0].equals("012"));
    }
}
