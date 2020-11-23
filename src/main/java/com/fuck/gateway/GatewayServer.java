package com.fuck.gateway;

import lombok.Builder;
import org.springframework.boot.CommandLineRunner;

/**
 * GatewayServer
 * @author duansg
 * @version 1.0
 */
@Builder
public class GatewayServer implements CommandLineRunner {

    private int port;

    @Override
    public void run(String... args) throws Exception {

    }

}
