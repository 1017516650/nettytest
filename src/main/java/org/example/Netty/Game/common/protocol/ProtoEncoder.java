package org.example.Netty.Game.common.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * ClassName: ProtobufEncoder
 * Description:
 * date: 2023/9/1 0001 下午 9:45
 *
 * @author 丁宇
 * @since JDK 8
 */
public class ProtoEncoder extends MessageToByteEncoder<Packet> {

    private static final int limit = 5120;

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf buf) throws Exception {

        if (packet.getBytes().length > limit){
            System.out.println("warning packet too big");
        }

        buf.writeByte(packet.getHead());
        buf.writeShort(packet.getLength());
        buf.writeInt(packet.getCmd());
        buf.writeBytes(packet.getBytes());
    }
}
