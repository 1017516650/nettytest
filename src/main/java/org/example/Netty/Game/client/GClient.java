package org.example.Netty.Game.client;

import org.example.Netty.Game.common.proto.out.bind.BindProto;
import org.example.Netty.Game.common.protocol.GClientBootstrap;

/**
 * ClassName: GClient
 * Description:
 * date: 2023/9/2 0002 下午 11:24
 *
 * @author 丁宇
 * @since JDK 8
 */
public class GClient {
    public static void main(String[] args) {
        GClientBootstrap instance = GClientBootstrap.getInstance();
        BindProto.C2GSBindPlayer.Builder builder = BindProto.C2GSBindPlayer.newBuilder();
        builder.setAccount("1111111");
        //客户端也要实现路由功能，这里1000001暂时写死
        instance.send(1000001, builder.build());
    }
}
