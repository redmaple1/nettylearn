package com.xyren.netty.learn.delimiterbasedframedecoderdemo.client;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Echo 服务 - 客户端处理类
 *
 * @author : renxiaoya
 * @date : 2020-03-14
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter {
    int count = 0;
    static final String ECHO_REQ = "Hi, Ren Xiaoya. Welcome to Netty.$_";

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            ctx.writeAndFlush(Unpooled.copiedBuffer(ECHO_REQ.getBytes()));
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("This is " + ++count + " times receive server : [" + msg + "]");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
