package org.example.Netty.Game.common.protocol;

/**
 * ClassName: NetHandler
 * Description:
 * date: 2023/9/2 0002 上午 11:43
 *
 * @author 丁宇
 * @since JDK 8
 */
public abstract class NetHandler {

    protected Packet packet;

    public void exexute(Packet packet){
        this.packet = packet;
        try {
            request();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public abstract void request() throws Exception;
}
