package com.xyren.netty.learn.delimiterbasedframedecoderdemo.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Echo 服务 - 服务端处理类
 *
 * @author : renxiaoya
 * @date : 2020-03-14
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    int count = 0;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;
        System.out.println("This is" + ++count + "times receive client : [" + body + "]");
        //由于设置了 DelimiterBasedFrameDecoder 过滤掉了分隔符，所以返回给客户端时结尾要加上 "$_"
        body += "$_";
        ByteBuf echo = Unpooled.copiedBuffer(body.getBytes());
        ctx.writeAndFlush(echo);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
