package org.example.Netty.Game.common.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * ClassName: ProtobufDecoder
 * Description:
 * date: 2023/9/1 0001 下午 9:44
 *
 * @author 丁宇
 * @since JDK 8
 */
public class ProtoDecoder extends ByteToMessageDecoder {

    private static final int limit = 2048;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 7){
            return;
        }

        in.markReaderIndex();

        //包头
        byte head = in.readByte();

        //长度
        short length = in.readShort();
        if (length < 0 || length > limit){
            throw new IllegalArgumentException();
        }

        //协议
        int cmd = in.readInt();
        if (in.readableBytes() < length - 4){
            in.resetReaderIndex();
            return;
        }

        byte[] bytes = new byte[length - 4];
        in.readBytes(bytes);
        out.add(new Packet(head, length, cmd, bytes, ctx.channel()));
    }
}
