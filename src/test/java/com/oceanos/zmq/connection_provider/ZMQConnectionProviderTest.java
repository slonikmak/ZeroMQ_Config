package com.oceanos.zmq.connection_provider;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.*;

class ZMQConnectionProviderTest {

    static String configFile = "/connection.groovy";
    static ZMQConnectionProvider zmqConnectionProvider;
    static ExecutorService executorService = Executors.newCachedThreadPool();
    static TestMsg testMsg = new TestMsg("testMsg", 1);
    static String topic = "/test";
    static boolean working = true;
    static CountDownLatch lock;


    @BeforeAll
    static void setup() throws IOException {
        File file = new File(ZMQConnectionProviderTest.class.getResource(configFile).getFile());
        zmqConnectionProvider = new ZMQConnectionProvider(file, ConfigType.TEST);
        lock = new CountDownLatch(1);
    }

    @Test
    void funcSystemTest() throws JsonProcessingException, InterruptedException, ExecutionException {
        CompletableFuture<TestMsg> future = CompletableFuture.supplyAsync(() -> {

            try (ZMQSub<TestMsg> sub = zmqConnectionProvider.createZMQSub(topic, TestMsg.class)) {
                sub.connect();
                TestMsg msg = sub.receive();
                return msg;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });

        executorService.submit(() -> {

            try (ZMQPub<TestMsg> pub = zmqConnectionProvider.createZMQPub(topic)) {
                pub.bind();
                while (working && !Thread.currentThread().isInterrupted()) {
                    pub.send(testMsg);
                    Thread.sleep(50);
                }
            } catch (Exception e) {
                //e.printStackTrace();
            }
        });

        TestMsg result = future.get();
        Assertions.assertEquals(testMsg.number, result.number);
        Assertions.assertEquals(testMsg.str, result.str);
        System.out.println(result.number+" "+result.str);

    }

    @AfterAll
    static void tearDown() {
        System.out.println("stop");
        working = false;
        zmqConnectionProvider.close();
        executorService.shutdownNow();
    }


}