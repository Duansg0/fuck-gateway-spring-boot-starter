package com.fuck.gateway.server;

import org.springframework.boot.CommandLineRunner;

/**
 * GatewayServer
 * @author duansg
 * @version 1.0
 */
public abstract class GatewayServer implements CommandLineRunner{

    @Override
    abstract public void run(String... args) throws Exception;

}
