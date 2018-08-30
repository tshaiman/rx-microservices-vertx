package rx.basics.samples;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import org.junit.Before;
import org.junit.Test;
import io.reactivex.Observable;

import static org.junit.Assert.assertTrue;
import static rx.basic.samples.ObservableImpl.getTitle;

public class ObservableTests {

    String result="";


    // Simple subscription to a fix value
    @Test
    public void returnAValue(){
        Observable<String> observer = Observable.just("Hello"); // provides datea
        observer.subscribe(s -> result=s); // Callable as subscriber
        assertTrue(result.equals("Hello"));
    }

    @Test
    public void objservableFrom() {
        String[] letters = {"a", "b", "c", "d", "e", "f", "g"};
        Observable<String> observable = Observable.fromArray(letters);
        observable.subscribe(
                i -> result += i,  //OnNext
                Throwable::printStackTrace, //OnError
                () -> result += "_Completed" //OnCompleted
        );
        assertTrue(result.equals("abcdefg_Completed"));
    }


    @Test
    public void givenArray_whenMapAndSubscribe_thenReturnCapitalLetters() {

        String[] letters = {"a", "b", "c", "d", "e", "f", "g"};
        Observable.fromArray(letters)
                .map(String::toUpperCase)
                .subscribe(lt-> result += lt);

        assertTrue(result.equals("ABCDEFG"));
    }


    @Test
    public void givenArray_whenConvertsObservabletoBlockingObservable_thenReturnFirstElement() {
        String[] letters = {"a", "b", "c", "d", "e", "f", "g"};
        Observable<String> observable = Observable.fromArray(letters);
        String blockingObservable = observable.blockingFirst();

        observable.subscribe(
                i -> result += i,
                Throwable::printStackTrace,
                () -> result += "_Completed"
        );
        assertTrue(String.valueOf(result.charAt(0)).equals(blockingObservable));
    }

    @Test
    public void flatMap() {
        Observable.just("book1", "book2","book3")
                .flatMap(s -> getTitle())
                .subscribe(l -> result += l);

        assertTrue(result.equals("titletitletitle"));
    }



    @Test
    public void givenArray_whenScanAndSubscribe_thenReturnTheSumOfAllLetters() {
        String[] letters = {"a", "b", "c"};

        Observable.fromArray(letters)
                .scan(new StringBuilder(), StringBuilder::append)
                .subscribe(total -> result += total.toString());

        assertTrue(result.equals("aababc"));
    }

    @Test
    public void givenArrayOfNumbers_whenGroupBy_thenCreateTwoGroupsBasedOnParity() {
        Integer[] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        String[] EVEN = {""};
        String[] ODD = {""};

        Observable.fromArray(numbers)
                .groupBy(i -> 0 == (i % 2) ? "EVEN" : "ODD")
                .subscribe(group ->
                        group.subscribe((number) -> {
                            if (group.getKey().equals("EVEN")) {
                                EVEN[0] += number;
                            } else {
                                ODD[0] += number;
                            }
                        })
                );

        assertTrue(EVEN[0].equals("0246810"));
        assertTrue(ODD[0].equals("13579"));
    }

    @Test
    public void givenArrayOfNumbers_whenFilter_thenGetAllOddNumbers() {
        Integer[] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        Observable.fromArray(numbers)
                .filter(i -> (i % 2 == 1))
                .subscribe(i -> result += i);

        assertTrue(result.equals("13579"));
    }

    @Test
    public void givenEmptyObservable_whenDefaultIfEmpty_thenGetDefaultMessage() {

        Observable.empty()
                .defaultIfEmpty("Observable is empty")
                .subscribe(s -> result += s);

        assertTrue(result.equals("Observable is empty"));
    }

    @Test
    public void givenObservableFromArray_whenDefaultIfEmptyAndFirst_thenGetFirstLetterFromArray() {
        String[] letters = {"a", "b", "c", "d", "e", "f", "g"};

        Observable.fromArray(letters)
                .defaultIfEmpty("Observable is empty")
                .firstOrError()
                .subscribe(s -> result += s);

        assertTrue(result.equals("a"));
    }

    @Test
    public void givenObservableFromArray_whenTakeWhile_thenGetSumOfNumbersFromCondition() {
        Integer[] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        final Integer[] sum = {0};


        Observable.fromArray(numbers)
                .takeWhile(i -> i < 5)
                .subscribe(s -> sum[0] += s);

        assertTrue(sum[0] == 10);
    }









    @Before
    public void before() {
        result= "";
    }

}
