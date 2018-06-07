package com.mcy.netty.time;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * @author zkzc-mcy create at 2018/6/6.
 */
public class TimeClientHandler1 extends ChannelInboundHandlerAdapter {

    private ByteBuf buffer;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

        buffer = ctx.alloc().buffer(4);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

        buffer.release();
        buffer = null;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf buf = (ByteBuf) msg;

        buffer.writeBytes(buf);
        buf.release();

        if(buffer.readableBytes() >= 4) {
            long time = (buf.readUnsignedInt() - 2208988800L) * 1000L;
            System.out.println(new Date(time));
            ctx.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        cause.printStackTrace();
        ctx.close();
    }
}
