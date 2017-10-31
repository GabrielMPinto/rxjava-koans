package coiso;

import io.reactivex.Observable;
import io.vertx.core.Future;
import io.vertx.reactivex.core.AbstractVerticle;

import java.util.Random;

public class PingVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        ping();
        startFuture.complete();
    }

    private void ping() {
        Observable.range(0, 1000)
                .map(i -> new Random().nextInt(100))
                .flatMapSingle(random -> vertx.eventBus().rxSend("pong", random))
                .subscribe(reply -> {
                    System.out.println(reply.body());
                });
    }
}
