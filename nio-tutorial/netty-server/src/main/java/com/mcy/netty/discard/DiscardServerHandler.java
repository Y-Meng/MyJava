package com.mcy.netty.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * 写一个类继承 ChannelInboundHandlerAdapter 处理服务端的 channel
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter{

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        // 丢掉所有数据
        // ((ByteBuf)msg).release();

        ByteBuf in = (ByteBuf) msg;
        try {
            // 输出接收内容
            System.out.println(in.toString(io.netty.util.CharsetUtil.US_ASCII));

            // echo
            ctx.write(msg);
            ctx.flush();
        }finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        // 处理异常
        cause.printStackTrace();
        ctx.close();
    }
}
