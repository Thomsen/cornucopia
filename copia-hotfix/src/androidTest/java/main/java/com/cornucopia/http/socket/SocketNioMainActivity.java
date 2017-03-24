package com.cornucopia.http.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.Selector;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.cornucopia.R;
import com.cornucopia.utils.ByteUtils;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class SocketNioMainActivity extends Activity implements OnClickListener  {
    
    private static final String TAG = "NioSocket";

    private static final int PORT = 8044;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_socket_main);
        
        findViewById(R.id.btn_socket_server).setOnClickListener(this);
        findViewById(R.id.btn_socket_client).setOnClickListener(this);
        findViewById(R.id.btn_socket_multi_client).setOnClickListener(this);
        
        listenAddres = new InetSocketAddress(getLocalIpAddress(), PORT);
        dataMapper = new HashMap<SocketChannel, List>();
    }
    
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_socket_server) {
            new Thread() {
                public void run() {
                    try {
                        startServer();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
            
            // 
            byte[] dd = ByteUtils.doubleToByteArray(108.23238193829);  // [64, 91, 14, -33, 88, 126, 72, -65]
            Log.i(TAG, "double byte test " + ByteUtils.byteArrayToDouble(dd));
            
            byte[] da = ByteUtils.longToByteArrayBig(10823238193829l);
            Log.i(TAG, "long big endian byte test " + ByteUtils.byteArrayToLongBig(da));
            
            byte[] dc = ByteUtils.longToByteArrayLittle(10823238193829l);
            Log.i(TAG, "long little endian byte test " + ByteUtils.byteArrayToLongLittle(dc));
            
            byte[] de = ByteUtils.charToByteArray('试');
            Log.i(TAG, "char byte test " + ByteUtils.byteArrayToChar(de));
            
        }
        if (v.getId() == R.id.btn_socket_client) {
            new Thread() {
                public void run() {
                    try {
                        clientRequest("client");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
            
        }
        if (v.getId() == R.id.btn_socket_multi_client) {
            new Thread("thread1") {
                public void run() {
                    try {
                        clientRequest("client1");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
            new Thread("thread2") {
                public void run() {
                    try {
                        clientRequest("client2");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
        
    }
    
    private Selector selector;
    private Map<SocketChannel, List> dataMapper;
    private InetSocketAddress listenAddres;

    protected void startServer() throws IOException {
        this.selector = Selector.open();
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        
        serverChannel.configureBlocking(false);
        serverChannel.socket().bind(listenAddres);
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        
        Log.i(TAG, "nio server starting");
        
        Iterator keys;
        SelectionKey key;
        while (true) {
            this.selector.select();
            
            keys = this.selector.selectedKeys().iterator();
            while (keys.hasNext()) {
                key = (SelectionKey) keys.next();
                
                keys.remove();
                
                if (!key.isValid()) {
                    continue;
                }
                
                if (key.isAcceptable()) {
                    this.serverAccept(key);
                } else if (key.isReadable()) {
                    this.serverRead(key);
                }
            }
        }
    }

    private void serverAccept(SelectionKey key) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel channel = serverChannel.accept();
        channel.configureBlocking(false);
        Socket socket = channel.socket();
        SocketAddress remoteAddr = socket.getRemoteSocketAddress();
        Log.i(TAG, "connected to: " + remoteAddr);
        
        dataMapper.put(channel, new ArrayList());
        channel.register(selector, SelectionKey.OP_READ);
    }
    
    private void serverRead(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int numRead = -1;
        numRead = channel.read(buffer);
        if (numRead == -1) {
            this.dataMapper.remove(channel);
            
            Socket socket = channel.socket();
            SocketAddress remoteAddr = socket.getRemoteSocketAddress();
            
            Log.i(TAG, "connection closed by client: " + remoteAddr);
            channel.close();
            key.cancel();
            return ;
        }
        
        byte[] data = new byte[numRead];
        System.arraycopy(buffer.array(), 0, data, 0, numRead);
        Log.i(TAG, "server received: " + new String(data));
        
    }

    private void clientRequest(String clientName) throws IOException, InterruptedException {
        SocketChannel client = SocketChannel.open(listenAddres);
        
        Log.i(TAG, "client " + clientName + " starting ...");
        
        String threadName = Thread.currentThread().getName();
        
        String[] msgs = new String[] {
                threadName + " : test1",
                threadName + " : test2",
                threadName + " : test3"
        };
        
        for (int i=0; i<msgs.length; i++) {
            byte[] msg = new String(msgs[i]).getBytes();
            ByteBuffer buffer = ByteBuffer.wrap(msg);
            client.write(buffer);
            
            buffer.clear();
            Thread.sleep(5000);
        }
        client.close();
    }
    
    private String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) { return inetAddress.getHostAddress().toString(); }
                }
            }
        } catch (SocketException ex) {
            Log.e("ServerActivity", ex.toString());
        }
        return null;
    }
}
