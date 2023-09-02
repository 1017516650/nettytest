package org.example.Netty.Game.server;

import org.example.Netty.Game.common.protocol.GServerBootsrtap;

/**
 * ClassName: GMain
 * Description:
 * date: 2023/9/1 0001 下午 10:58
 *
 * @author 丁宇
 * @since JDK 8
 */
public class GServer {
    public static void main(String[] args) throws Exception{
        GGame game = GGame.getInstance();
        game.init();
        game.readNetDefines();
        game.searchPathAndRegister();
        new GServerBootsrtap();
    }
}
