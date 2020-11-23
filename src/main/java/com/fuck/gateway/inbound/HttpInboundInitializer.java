package com.fuck.gateway.inbound;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpServerExpectContinueHandler;
import lombok.Builder;

import java.util.List;

/**
 * HttpInboundInitializer
 *
 * @author duansg
 * @version 1.0
 * @date 2020/11/4 11:34 下午
 */
@Builder
public class HttpInboundInitializer extends ChannelInitializer<SocketChannel> {

    private List<String> mappings;

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();
        p.addLast(new HttpServerCodec());
        //p.addLast(new HttpServerExpectContinueHandler());
        p.addLast(new HttpObjectAggregator(1024 * 1024));
        p.addLast(new HttpInboundHandler(mappings));
    }

}
