package coiso;

import io.vertx.core.Future;
import io.vertx.reactivex.RxHelper;
import io.vertx.reactivex.core.AbstractVerticle;

public class PongVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        pong();
        startFuture.complete();
    }

    private void pong() {
        this.vertx.eventBus().<Integer>consumer("pong")
                .toObservable()
                .filter(i -> i.body() % 2 == 0)
                .doOnNext(i -> i.reply("Got an even: " + i.body()))
                .subscribe(message -> {
                    System.out.println(message.body());
                });
    }
}
