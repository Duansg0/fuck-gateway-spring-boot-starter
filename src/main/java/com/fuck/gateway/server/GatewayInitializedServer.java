package com.fuck.gateway.server;


import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.Builder;

/**
 * GatewayInitServer
 * @author duansg
 * @version 1.0
 */
@Builder
public class GatewayInitializedServer extends GatewayServer {

    public int port;

    @Override
    public void run(String... args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(16);
    }
}
