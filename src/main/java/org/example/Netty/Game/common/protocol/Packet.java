package org.example.Netty.Game.common.protocol;

import io.netty.channel.Channel;
import org.example.Netty.Game.server.GGame;
import org.example.Netty.Game.server.GPlayer;

/**
 * ClassName: Packet
 * Description:
 * date: 2023/9/1 0001 下午 9:34
 *
 * @author 丁宇
 * @since JDK 8
 */
public class Packet {
    public static final byte HEAD_TCP = -128;
    public static final byte HEAD_UDP = 0;
    public static final byte HEAD_NEED_ACK = 64;
    public static final byte HEAD_ACK = 44;
    public static final byte HEAD_PROTOCOL_MASK = 3;
    public static final byte PROTOCOL_PROTOBUF = 0;
    public static final byte PROTOCOL_JSON = 1;

    private final byte head;
    private final short length;
    private final int cmd;
    private final byte[] bytes;
    private final Channel channel;

    public Packet(byte head, int cmd, byte[] bytes) {
        this(head, (short) 0, cmd, bytes, null);
    }


    public Packet(byte head, short length, int cmd, byte[] bytes, Channel channel) {
        this.head = head;
        this.length = length;
        this.cmd = cmd;
        this.bytes = bytes;
        this.channel = channel;
    }

    public byte getHead() {
        return head;
    }

    public short getLength() {
        return length;
    }

    public int getCmd() {
        return cmd;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public Channel getChannel() {
        return channel;
    }

    public GPlayer getPlayer(){
        return channel.attr(GGame.PLAYER_KEY).get();
    }
}
