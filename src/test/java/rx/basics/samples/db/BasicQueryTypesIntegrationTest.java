package rx.basics.samples.db;

import io.reactivex.Flowable;
import org.davidmoten.rx.jdbc.Database;
import org.junit.After;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BasicQueryTypesIntegrationTest {

    private Database db = Database.test(2);

    private Flowable<Integer> create;

    @Test
    public void whenCreateTableAndInsertRecords_thenCorrect() {
        create = db.update("CREATE TABLE EMPLOYEE_TABLE(id int primary key, name varchar(255))")
                .counts();

        Flowable<Integer> insert1 = db.update("INSERT INTO EMPLOYEE_TABLE(id, name) VALUES(1, 'John')")
                .dependsOn(create)
                .counts();
        Flowable<Integer> update = db.update("UPDATE EMPLOYEE_TABLE SET name = 'Alan' WHERE id = 1")
                .dependsOn(insert1)
                .counts();
        Flowable<Integer> insert2 = db.update("INSERT INTO EMPLOYEE_TABLE(id, name) VALUES(2, 'Sarah')")
                .dependsOn(update)
                .counts();
        Flowable<Integer> insert3 = db.update("INSERT INTO EMPLOYEE_TABLE(id, name) VALUES(3, 'Mike')")
                .dependsOn(insert2)
                .counts();
        Flowable<Integer> delete = db.update("DELETE FROM EMPLOYEE_TABLE WHERE id = 2")
                .dependsOn(insert3)
                .counts();


        List<String> names = db.select("select name from EMPLOYEE_TABLE where id <= ?")
                .parameter(3)
                .dependsOn(delete)
                .getAs(String.class)
                .toList()
                .blockingGet();



        assertEquals(Arrays.asList("Alan","Mike"), names);

        String firstName = db.select("select name from EMPLOYEE_TABLE")
                .getAs(String.class)
                .blockingFirst();

        assertEquals(firstName,"Alan");


    }

    @After
    public void close() {
        db.update("DROP TABLE EMPLOYEE_TABLE")
                .dependsOn(create);
    }
}