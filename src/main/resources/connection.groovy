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
