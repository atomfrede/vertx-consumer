package com.github.atomfrede.vertx.consumer;

import com.hazelcast.config.Config;
import com.hazelcast.config.NetworkConfig;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;

public class Consumer extends AbstractVerticle {

    @Override
    public void start() {

        HazelcastClusterManager mgr = new HazelcastClusterManager();

        Config conf = new Config();

        NetworkConfig networkConfig = new NetworkConfig();
        networkConfig.setPort(5701);
        networkConfig.setPortAutoIncrement(false);

        conf.setNetworkConfig(networkConfig);
        mgr.setConfig(conf);

        mgr.setConfig(conf);

        VertxOptions options = new VertxOptions().setClusterManager(mgr);

        Vertx.clusteredVertx(options, res -> {
            if (res.succeeded()) {
                Vertx vertx = res.result();

                vertx.eventBus().consumer("test.ping", objectMessage -> {

                    System.out.println("Received Message: " + objectMessage.body());
                });
            } else {
                // failed!
            }
        });
    }
}
