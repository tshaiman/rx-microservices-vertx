package rx.basic.samples;

import io.reactivex.Observable;

import java.util.Arrays;
import java.util.List;

public class ObservableImpl {
    private static Integer[] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

    private static String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h", "i"};
    private static String[] titles = {"title"};
    private static List<String> titleList = Arrays.asList(titles);

    public static Observable<String> getTitle() {
        return Observable.fromIterable(titleList);
    }

}
