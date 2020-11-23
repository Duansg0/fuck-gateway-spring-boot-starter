package com.fuck.gateway.server;


import com.fuck.gateway.SpringBootGatewayPropertiesConfigutrtion;
import com.fuck.gateway.inbound.HttpInboundInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * GatewayInitServer
 * @author duansg
 * @version 1.0
 */
@Builder
@Slf4j
public class GatewayInitializedServer extends GatewayServer {

    @Autowired
    private SpringBootGatewayPropertiesConfigutrtion configutrtion;

    @Override
    public void run(String... args) throws Exception {
        ServerBootstrap bootstrap = new ServerBootstrap();
        EventLoopGroup bossGroup = new NioEventLoopGroup(configutrtion.getBossThreads());
        EventLoopGroup workerGroup = new NioEventLoopGroup(configutrtion.getWorkerThreads());
        try {
            bootstrap.option(ChannelOption.SO_BACKLOG, 128)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.SO_REUSEADDR, true)
                    .option(ChannelOption.SO_RCVBUF, 32 * 1024)
                    .option(ChannelOption.SO_SNDBUF, 32 * 1024)
                    .option(EpollChannelOption.SO_REUSEPORT, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(HttpInboundInitializer.builder()
                            .mappings(null)
                            .build());
            Channel ch = bootstrap.bind(configutrtion.getPort()).sync().channel();
            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
