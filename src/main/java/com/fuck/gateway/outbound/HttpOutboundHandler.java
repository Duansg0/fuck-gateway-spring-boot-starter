package com.fuck.gateway.outbound;


import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.util.Map;
import java.util.concurrent.*;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;


/**
 * HttpOutboundHandler
 *
 * @author duansg
 * @version 1.0
 * @date 2020/11/4 11:42 下午
 */
public class HttpOutboundHandler {

    private ExecutorService proxyService;

    public HttpOutboundHandler(){
        int cores = Runtime.getRuntime().availableProcessors() * 2;
        long keepAliveTime = 1000;
        int queueSize = 2048;
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();
        proxyService = new ThreadPoolExecutor(cores, cores,
                keepAliveTime, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(queueSize),
                r -> new Thread(), handler);
        System.out.println("HttpInboundHandler 初始化 HttpOutboundHandler");
    }

    public void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx) {
        System.out.println("HttpInboundHandler 调用 HttpOutboundHandler");
        fetchGet(fullRequest, ctx);
        //proxyService.submit(()->fetchGet(fullRequest, ctx));
    }

    private void fetchGet(final FullHttpRequest inbound, final ChannelHandlerContext ctx) {
        CloseableHttpResponse callResponse;
        FullHttpResponse response = null;
        try {
             response.headers().set("Content-Type", "application/json");
             response.headers().setInt("Content-Length", response.content().readableBytes());
             ctx.write(response);
             System.out.println("ctx.write rtnResponse");
        } catch (Exception e) {
            e.printStackTrace();
            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
            exceptionCaught(ctx, e);
        } finally {
            if (inbound != null) {
                if (!HttpUtil.isKeepAlive(inbound)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    //response.headers().set(CONNECTION, KEEP_ALIVE);
                    ctx.write(response);
                }
            }
            ctx.flush();
            //ctx.close();
        }
    }
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
