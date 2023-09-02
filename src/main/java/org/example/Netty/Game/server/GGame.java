package org.example.Netty.Game.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.netty.util.AttributeKey;
import org.example.Netty.Game.common.protocol.NetHandler;
import org.example.Netty.Game.common.protocol.Packet;

import java.io.File;
import java.io.FileInputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.jar.JarEntry;

/**
 * ClassName: GGame
 * Description:
 * date: 2023/9/1 0001 下午 9:36
 *
 * @author 丁宇
 * @since JDK 8
 */
public class GGame {

    public static final AttributeKey<GPlayer> PLAYER_KEY = AttributeKey.valueOf("PLAYER");

    private static final int __CAPACITY__ = 100000;

    private static final int __WARN_COUNT__ = __CAPACITY__ * 2 / 3;

    private static final String filePath = "src/main/java/org/example/Netty/Game/common/proto/netdefines.json";

    private Map<Integer, String> C2GSCmdMap = new HashMap<>();
    private Map<String, Integer> GS2CCmdMap = new HashMap<>();

    private Map<String, NetHandler> routesMap = new HashMap<>();

    private BlockingQueue<Packet> __QUEUE__ = new LinkedBlockingQueue(__CAPACITY__);

    private ConcurrentHashMap<String, GPlayer> onlinePlayers = new ConcurrentHashMap<>();

    private Thread thread;

    private static GGame instance;

    public static GGame getInstance(){
        if (instance == null){
            synchronized (GGame.class){
                instance = new GGame();
            }
        }
        return instance;
    }

    //初始化
    public void init(){
        //消息队列
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Packet packet = __QUEUE__.take();
                        String cmd = C2GSCmdMap.get(packet.getCmd());

                        if (cmd == null){
                            System.err.println("协议号错误，没有此协议：" + packet.getCmd());
                            continue;
                        }

                        NetHandler handler = routesMap.get(cmd);
                        handler.exexute(packet);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        });
        thread.start();
    }

    //制作C2GS映射表，GS2C映射表
    public void readNetDefines() throws Exception {
        File file = new File(filePath);
        FileInputStream in = new FileInputStream(file);
        FileChannel channel = in.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());
        channel.read(byteBuffer);
        String json = new String(byteBuffer.array());
        JSONObject jsonObject = JSON.parseObject(json);

        if (jsonObject.containsKey("client")){
            JSONArray array = (JSONArray) jsonObject.get("client");
            for (Object clientObj : array) {
                JSONObject jo = (JSONObject) clientObj;
                for (String key : jo.keySet()) {
                    Integer value = Integer.valueOf((String) jo.get(key));
                    C2GSCmdMap.put(value, key);
                }
            }
        }

        if (jsonObject.containsKey("server")){
            JSONArray array = (JSONArray) jsonObject.get("server");
            for (Object serverObj : array) {
                JSONObject jo = (JSONObject) serverObj;
                for (String key : jo.keySet()) {
                    Integer value = Integer.valueOf((String) jo.get(key));
                    GS2CCmdMap.put(key, value);
                }
            }
        }

        in.close();
    }

    public boolean AddPacket(Packet packet) {
        if (__QUEUE__.size() > __WARN_COUNT__){
            System.err.println("队列过大 -> " + __QUEUE__.size());
        }

        if (__QUEUE__.offer(packet)){
            return true;
        }else {
            System.err.println("队列过大丢弃了请求 -> " + packet.getCmd());
            return false;
        }
    }

    //注册接口
    public void searchPathAndRegister(){
        List<Class<?>> result = null;
        String scan = instance.getClass().getPackage().getName();
        scan = scan.replace(".", "/") + "/netcmd";

        try {
            Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(scan);
            while (dirs.hasMoreElements()){
                URL url = dirs.nextElement();
                if (url.getProtocol().equals("file")){
                    List<File> classes = new ArrayList<>();
                    listFiles(new File(url.getFile()), classes);
                    result = loadClasses(classes, scan);
                }else if(url.getProtocol().equals("jar")){
                    result = new ArrayList<>();
                    JarURLConnection urlConnection = (JarURLConnection) url.openConnection();
                    Enumeration<JarEntry> entries = urlConnection.getJarFile().entries();
                    while (entries.hasMoreElements()){
                        JarEntry entry = entries.nextElement();
                        if (entry.getName().endsWith(".class")){
                            String fPath = entry.getName().replaceAll("\\\\","/") ;
                            String packageName = fPath.substring(fPath.lastIndexOf(scan));
                            packageName = packageName.replace(".class","").replaceAll("/", ".");
                            result.add(Class.forName(packageName));
                        }
                    }
                }
            }

            for (Class<?> aClass : result) {
                Object handler = aClass.newInstance();
                routesMap.put(handler.getClass().getSimpleName(), (NetHandler) handler);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<Class<?>> loadClasses(List<File> classes, String scan) throws Exception {
        List<Class<?>> clazzes = new ArrayList<>();
        for (File file : classes) {
            String fPath = file.getAbsolutePath().replaceAll("\\\\", "/");
            String packageName = fPath.substring(fPath.lastIndexOf(scan));
            packageName = packageName.replace(".class", "").replace("/", ".");
            clazzes.add(Class.forName(packageName));
        }
        return clazzes;
    }

    private void listFiles(File dir, List<File> fileList){
        if (dir.isDirectory()){
            for (File file : dir.listFiles()) {
                listFiles(file, fileList);
            }
        }else {
            if (dir.getName().endsWith(".class")){
                fileList.add(dir);
            }
        }
    }

    //在线玩家（账号:玩家实例）
    public void AddOnlinesPlayer(GPlayer player){
        onlinePlayers.put(player.getSid(), player);
    }

    public Map<String, Integer> getGS2CCmdMap() {
        return GS2CCmdMap;
    }
}
