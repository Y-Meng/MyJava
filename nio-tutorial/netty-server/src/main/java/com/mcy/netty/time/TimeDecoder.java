package com.mcy.netty.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author zkzc-mcy create at 2018/6/6.
 */
public class TimeDecoder extends ByteToMessageDecoder{

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if(byteBuf.readableBytes() < 4){
            return;
        }
        // list.add(byteBuf.readBytes(4));
        list.add(new UnixTime(byteBuf.readUnsignedInt()));
    }
}
