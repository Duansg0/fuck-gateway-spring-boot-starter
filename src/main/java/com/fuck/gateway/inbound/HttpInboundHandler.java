package com.fuck.gateway.inbound;

import com.fuck.gateway.outbound.HttpOutboundHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;
import lombok.Builder;
import java.util.List;

/**
 * HttpInboundHandler
 * @author duansg
 * @version 1.0
 */
@Builder
public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    private HttpOutboundHandler handler;

    private List<String> mappings;

    public HttpInboundHandler(List<String> mappings) {
        handler = new HttpOutboundHandler();
        this.mappings = mappings;
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
