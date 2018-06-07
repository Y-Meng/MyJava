package com.mcy.netty.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * @author zkzc-mcy create at 2018/6/6.
 */
public class TimeEncoder extends ChannelOutboundHandlerAdapter{

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {

        UnixTime time = (UnixTime)msg;
        ByteBuf encoded = ctx.alloc().buffer(4);
        encoded.writeInt((int) time.value());
        ctx.write(encoded, promise);
    }
}
