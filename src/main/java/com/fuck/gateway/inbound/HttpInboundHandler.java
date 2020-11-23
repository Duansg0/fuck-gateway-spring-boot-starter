package com.fuck.gateway.inbound;

import com.fuck.gateway.outbound.HttpOutboundHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;

/**
 * HttpInboundHandler
 *
 * @author duansg
 * @version 1.0
 * @date 2020/11/23 上午10:27
 */
public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    private HttpOutboundHandler handler;

    public HttpInboundHandler() {
        handler = new HttpOutboundHandler();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            System.out.println(msg);
            FullHttpRequest fullRequest = (FullHttpRequest) msg;
            fullRequest.headers().add("hello","world");
            // 通过filter过滤器
            handler.handle(fullRequest, ctx);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }
}
