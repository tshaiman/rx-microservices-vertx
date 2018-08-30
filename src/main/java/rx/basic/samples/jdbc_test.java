package rx.basic.samples;

import io.reactivex.schedulers.Schedulers;
import org.davidmoten.rx.jdbc.Database;

import java.util.concurrent.Executors;

public class jdbc_test {

    public static void main(String[] args) {
        Schedulers.from(Executors.newFixedThreadPool(2));


        Database db = Database.test(2);
        db.select("select name from person")
                .getAs(String.class)
                .blockingForEach(System.out::println);
    }
}
