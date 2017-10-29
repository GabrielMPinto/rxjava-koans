package solutions;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;
import rx.functions.Func1;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;


public class lessonD_Solutions {

    public String mReceived = "";
    public String _____;
    public Integer _______;

    private String mEvenNums = "";
    private String mOddNums = "";
    private Observable<Double> ________;
    private Func1<? super Double, ?> ____________;

    /*
    So far everything has been pretty linear. Our pipelines all took the form:
    "do this, then do this, then do this, then end". In reality we can combine pipelines. We can take two streams
    and turn them into a single stream.

    Now its worth nothing this is different from what we did when we nested Observables. In that case we always had one stream.
    Lets take a stream of integers and a stream of strings and join them.
    */
    public void merging() {
        Observable<Object> you = Observable.just(1, 2, 3);
        Observable<String> me = Observable.just("A", "B", "C");

        you.mergeWith(me).subscribe(string -> mReceived += string + " ");

        assertThat(mReceived).isEqualTo("1 2 3 A B C");
    }

    /*
    We can also split up a single stream into two streams. We are going to to use the groupBy() action.
    This action can be a little tricky because it emits an observable of observables. So we need to subscribe to the
    "parent" observable and each emitted observable.

    We encourage you to read more from the wiki: http://reactivex.io/documentation/operators/groupby.html

    Lets split up a single stream of integers into two streams: even and odd numbers.
    */
    public void splittingUp() {
        Observable.range(1, 9)
                .groupBy(integer -> {
                    if (integer % 2 == 0) {
                        return "even";
                    } else {
                        return "odd";
                    }
                })
                .subscribe(group -> group.subscribe(integer -> {
                    String key = group.getKey();
                    if (Objects.equals(key, "even")) {
                        mEvenNums = mEvenNums + integer;
                    } else if (Objects.equals(key, "odd")) {
                        mOddNums = mOddNums + integer;
                    }
                }));

        assertThat(mEvenNums).isEqualTo("2468");
        assertThat(mOddNums).isEqualTo("13579");
    }
}