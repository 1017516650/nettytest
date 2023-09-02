package org.example.Netty.Game.common.protocol;

import com.google.protobuf.GeneratedMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.example.Netty.Game.server.GGame;

/**
 * ClassName: ProtoManager
 * Description:
 * date: 2023/9/2 0002 下午 10:59
 *
 * @author 丁宇
 * @since JDK 8
 */
public class ProtoManager {
    public static ByteBuf wrapBuffer(int cmd, GeneratedMessage msg){
        ByteBufAllocator alloc = ByteBufAllocator.DEFAULT;
        byte[] data = msg.toByteArray();
        int length = data.length + 4;
        ByteBuf buffer = alloc.buffer(length + 3);
        buffer.writeByte((byte)0x80);
        buffer.writeShort(length);
        buffer.writeInt(cmd);
        buffer.writeBytes(data);
        return buffer;
    }
}
