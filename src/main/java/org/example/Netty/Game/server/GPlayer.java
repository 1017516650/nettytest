package org.example.Netty.Game.server;

import com.google.protobuf.GeneratedMessage;
import io.netty.channel.Channel;
import org.example.Netty.Game.common.protocol.ProtoManager;

/**
 * ClassName: GPlayer
 * Description:
 * date: 2023/9/2 0002 上午 9:29
 *
 * @author 丁宇
 * @since JDK 8
 */
public class GPlayer {
    private String sid;
    private Channel channel;

    public void bind(Channel ch){
        this.channel = ch;
        channel.attr(GGame.PLAYER_KEY).set(this);
        GGame.getInstance().AddOnlinesPlayer(this);
    }

    public void send(String route, GeneratedMessage msg){
        int cmd = GGame.getInstance().getGS2CCmdMap().get(route);
        channel.writeAndFlush(ProtoManager.wrapBuffer(cmd, msg));
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
