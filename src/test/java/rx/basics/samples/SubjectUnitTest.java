package rx.basics.samples;

import io.reactivex.subjects.PublishSubject;
import org.junit.Test;
import rx.basic.samples.SubjectImp;

import static org.junit.Assert.assertTrue;


public class SubjectUnitTest {

    @Test
    public void givenSubjectAndTwoSubscribers_whenSubscribeOnSubject_thenSubscriberBeginsToAdd() {
        PublishSubject<Integer> subject = PublishSubject.create();

        subject.subscribe(SubjectImp.getFirstObserver());
        subject.onNext(1);
        subject.onNext(2);
        subject.onNext(3);

        subject.subscribe(SubjectImp.getSecondObserver());
        subject.onNext(4);
        subject.onComplete();

        assertTrue(SubjectImp.subscriber1 + SubjectImp.subscriber2 == 14);
    }
}
