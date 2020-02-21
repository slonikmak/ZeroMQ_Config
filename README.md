# ZMQ Connection Provider 
Библиотека для простого создания ZeroMQ pub/sub с помощью конфигурации из groovy файла.  

## Включение
```$xml
<dependency>
    <groupId>com.oceanos.zmq</groupId>
    <artifactId>connection-provider</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```



## Пример конфигурации
```$groovy
/**
 * можно определять любые переменные и вызывать
 * любой код. Это чистый Groovy!
 */
def auvHost = "192.168.10.102"

/**
 * Конфигурация соединений.
 * Функция address(Closure closure) принимает выражение,
 * в котором вызываются функции debug, prod, test
 * в которые нужно передать String host, int port, String protocol соединения
 * название функций определяет тип конфигурации
 */
auvProxyTcpSub = address {
    debug ("host", 234, "tcp")
    prod (auvHost, 5555, "tcp")
    test ("localhost", 5555, "tcp")
}

auvProxyTcpPub = address {
    debug ("host", 234, "tcp")
    prod ("192.168.10.102", 5555, "tcp")
    test ("*", 5555, "tcp")
}

mkTcp = address {
    debug("host", 123, "tcp")
    prod("host", 123, "tcp")
    test("host", 123, "tcp")
}

/**
 * Конфигурация pub и sub с помощью соответствующих функций
 * которые принимают String topic и ссылку на сконфигурированный выше адрес
 */
sub "robot/state", auvProxyTcpSub
sub("/test", auvProxyTcpSub)

pub("robot/state", auvProxyTcpPub)
pub("/test", auvProxyTcpPub)
```
## Создание Поставщика

```$groovy
File file = new File(getClass().getResource("/connection.groovy").getFile());
ZMQConnectionProvider provider = new ZMQConnectionProvider(file, ConfigType.TEST);
```
## Создание Publisher

```$groovy
new Thread(()->{
    try (ZMQPub<TestMsg> pub = zmqConnectionProvider.createZMQPub("topic/test")){
        pub.bind();
        while (!Thread.currentThread().isInterrupted()){
            pub.send(new TestMsg());
            Thread.sleep(50);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}).start();

```  
## Создание Subscriber

```$groovy
new Thread(()->{
    try (ZMQSub<TestMsg> sub = connectionProvider.createZMQSub("topic/test", TestMsg.class)){
        sub.connect();
        while (!Thread.currentThread().isInterrupted()){
            TestMsg msg = sub.receive();
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}).start();

```