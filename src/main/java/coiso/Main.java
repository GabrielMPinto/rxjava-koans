package coiso;

import io.reactivex.Single;
import io.vertx.reactivex.core.RxHelper;
import io.vertx.reactivex.core.Vertx;

public class Main {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        PingVerticle pingVerticle = new PingVerticle();
        PongVerticle pongVerticle = new PongVerticle();

        Single<String> ping = RxHelper.deployVerticle(vertx, pingVerticle);
        Single<String> pong = RxHelper.deployVerticle(vertx, pongVerticle);

        Single.zip(pong, ping, (pongId, pingId) -> {
            System.out.println(pongId);
            System.out.println(pingId);
            return "Both deployed.";
        })
                .subscribe(System.out::println);
    }
}