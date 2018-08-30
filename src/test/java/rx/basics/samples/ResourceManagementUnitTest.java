package rx.basics.samples;

import io.reactivex.Observable;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;


public class ResourceManagementUnitTest {

    @Test
    public void givenResource_whenUsingOberservable_thenCreatePrintDisposeResource() throws InterruptedException {

        String[] result = {""};
        Observable<Character> values = Observable.using(
                () -> "MyResource",
                r -> Observable.create(o -> {
                    for (Character c : r.toCharArray())
                        o.onNext(c);
                    o.onComplete();
                }),
                r -> System.out.println("Disposed: " + r)
        );

        values.subscribe(
                v -> result[0] += v,
                e -> result[0] += e
        );
        assertTrue(result[0].equals("MyResource"));
    }
}
