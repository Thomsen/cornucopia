package com.cornucopia.http.socket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Enumeration;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.cornucopia.R;
import com.cornucopia.utils.ByteUtils;

public class SocketMainActivity extends Activity implements OnClickListener {
    
    private ServerSocket serverSocket;
    
    private static final int PORT = 8033;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_socket_main);
        
        findViewById(R.id.btn_socket_server).setOnClickListener(this);
        findViewById(R.id.btn_socket_client).setOnClickListener(this);
        findViewById(R.id.btn_socket_multi_client).setOnClickListener(this);
        
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_socket_server) {
            new Thread() {
                public void run() {
                    startServer();
                }
            }.start();
            
        }
        if (v.getId() == R.id.btn_socket_client) {
            new Thread() {
                public void run() {
                    clientRequest("client");
                }
            }.start();
            
        }
        if (v.getId() == R.id.btn_socket_multi_client) {
            new Thread("thread1") {
                public void run() {
                    clientRequest("client1");
                }
            }.start();
            new Thread("thread2") {
                public void run() {
                    clientRequest("client2");
                }
            }.start();
        }
        
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != serverSocket) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void startServer() {
        try {
           if (null != serverSocket && !serverSocket.isClosed()) {
               return ;
           }
           serverSocket = new ServerSocket(PORT);
            
            while (true) {
                Socket server = serverSocket.accept();  // @0 blocking
                
                Log.i("Socket", "Connected");  // @2s
                
                long start = System.currentTimeMillis();
                
                InputStream serverInputStream = server.getInputStream();
                
//                String filePath = getFilesDir().getPath().toString() + "/output.jpg";
                String filePath = getExternalFilesDir(null).getAbsolutePath() + "/output.png";
                
                FileOutputStream fileOutputStream = new FileOutputStream(filePath);
                
                byte[] msgType = new byte[4];
                byte[] msgSize = new byte[4];
                
                serverInputStream.read(msgType);  // @4
                int type = ByteUtils.byteArrayToInt(msgType);
                Log.i("Socket", "server receive client msg type: " + type);
                
                serverInputStream.read(msgSize);
                int size = ByteUtils.byteArrayToInt(msgSize);
                Log.i("Socket", "server receive client msg size: " + size);
                
                byte[] data = new byte[size];
                
//                while (serverInputStream.read(data) != -1) {
//                    fileOutputStream.write(data);  // blocking  文件输出流阻塞了socket 
//                    // -- socket在close后，才会发送给另一端结束符EOF，从而才会read到流结尾信息而返回-1。 这里由于客户端有一个接收，所以不会得到-1
//                }
                
                // 一次性读
                if (serverInputStream.read(data) != -1) {
                    fileOutputStream.write(data);
                } else {
                    Log.i("Socket", server.getRemoteSocketAddress() + " client is close.");
                }
                
                fileOutputStream.flush();
                fileOutputStream.close();
                
                long end = System.currentTimeMillis();
                Log.i("Socket", "server receiver the file time(mills): " + (end - start));  // @6
                
                // response                
                int buffer = 512;
                OutputStream serverOutputStream = server.getOutputStream();
                byte[] respType = new byte[4];
                byte[] respStatus = new byte[4];
                byte[] respSize = new byte[4];
                byte[] respKeyLength = new byte[4];
                byte[] respKeyValue = new byte[buffer];
                byte[] respValueLength = new byte[4];
                byte[] respValueBody = new byte[buffer];
                
                respType = ByteBuffer.allocate(4).putInt(201).array();
                respStatus = ByteBuffer.allocate(4).putInt(200).array();
                respSize = ByteBuffer.allocate(4).putInt(1).array();
                respKeyLength = ByteBuffer.allocate(4).putInt(ByteUtils.intToByteArray(1).length).array();
                respKeyValue = ByteBuffer.allocate(4).putInt(1).array();
//                String body = "response success";  // no "UTF-8" also receive success
                String body = "响应成功";            // 
                respValueBody = body.getBytes("UTF-8");
                respValueLength = ByteBuffer.allocate(4).putInt(respValueBody.length).array();
                
                serverOutputStream.write(respType);
                serverOutputStream.write(respStatus);
                serverOutputStream.write(respSize);
                serverOutputStream.write(respKeyLength);
                serverOutputStream.write(respKeyValue);
                serverOutputStream.write(respValueLength);
                serverOutputStream.write(respValueBody);
                
                serverOutputStream.flush();
                
                serverInputStream.close();
                serverOutputStream.close();
            }
            
           
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    private void clientRequest(String clientName) {
        try {
   
            Socket client = new Socket(getLocalIpAddress(), PORT);   // @1
            
            // send
            InputStream fileInputStream = getAssets().open("cordova.png");  // @2c
            
            byte[] msgType = new byte[4];
            byte[] msgSize = new byte[4];
            
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            
            OutputStream clientOutputStream = client.getOutputStream();
            
            msgType = ByteBuffer.allocate(4).putInt(101).array();
            Log.i("Socket", clientName + " send msg type: " + 101);
            clientOutputStream.write(msgType);
            
//            long fileSize = getAssets().openFd("cordova.png").getLength();
//             This file can not be opened as a file descriptor; it is probably compressed
//            Android compresses all assets, except for the following types:
//
//                ".jpg", ".jpeg", ".png", ".gif",
//                ".wav", ".mp2", ".mp3", ".ogg", ".aac",
//                ".mpg", ".mpeg", ".mid", ".midi", ".smf", ".jet",
//                ".rtttl", ".imy", ".xmf", ".mp4", ".m4a",
//                ".m4v", ".3gp", ".3gpp", ".3g2", ".3gpp2",
//                ".amr", ".awb", ".wma", ".wmv"
            int fileSize = fileInputStream.available();
            msgSize = ByteBuffer.allocate(4).putInt(fileSize).array();
            Log.i("Socket", clientName + " send msg type: " + fileSize);
            clientOutputStream.write(msgSize);  // sendto failed: ECONNRESET (Connection reset by peer)  一端socket被关闭，另一端发送第一个数据包会发生异常
            
            byte[] transferData = new byte[fileSize];
            
            int read_count = 0;
            while ((read_count = bufferedInputStream.read(transferData, 0, transferData.length)) != -1) {
                clientOutputStream.write(transferData, 0, read_count);  // @3  如果server是一次性接收stream，就不能多次write，合并byte
            }
            clientOutputStream.flush();  // @5
//            client.shutdownOutput();  // 1、关闭输入流，这样server端read可以得到-1 2、约定结束标志 3、服务端设置超时 4、头部传输长度
            
            if ("client2".equals(clientName)) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
            // receive          
            InputStream clientInputStream = client.getInputStream();
            byte[] respType = new byte[4];
            byte[] respStatus = new byte[4];
            byte[] respSize = new byte[4];
            
            while (true) {
                clientInputStream.read(respType);
                clientInputStream.read(respStatus);
                clientInputStream.read(respSize);
                
                int type = ByteUtils.byteArrayToInt(respType);
                int status = ByteUtils.byteArrayToInt(respStatus);
                int size = ByteUtils.byteArrayToInt(respSize);
                
                Log.i("Socket", clientName + " receive response: type " + type);
                Log.i("Socket", clientName + " receive response: status " + status);
                
                for (int i=0; i<size; i++) {
                    byte[] respKeyLength = new byte[4];
                    clientInputStream.read(respKeyLength);
                    int keyLength = ByteUtils.byteArrayToInt(respKeyLength);
                    byte[] respKeyValue = new byte[keyLength];
                    clientInputStream.read(respKeyValue);
                    int keyValue = ByteUtils.byteArrayToInt(respKeyValue);
                    
                    byte[] respValueLength = new byte[4];
                    clientInputStream.read(respValueLength);
                    int bodyLength = ByteUtils.byteArrayToInt(respValueLength);
                    byte[] respBodyValue = new byte[bodyLength];
                    clientInputStream.read(respBodyValue);
                    String bodyValue = new String(respBodyValue);
                    
                    Log.i("Socket", clientName + " receive response: " + keyValue + " - " + bodyValue);
                }
                break;
            }

            fileInputStream.close();
            bufferedInputStream.close();
            clientOutputStream.close();
            clientInputStream.close();
            client.close();
        
        } catch (UnknownHostException e) {
        e.printStackTrace();
        } catch (IOException e) {
        e.printStackTrace();
        }
        
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

    
//    Option A:
//
//    InputStream in = socket.getInputStream();
//    OutputStream out = socket.getOutputStream();
//
//    Option B:
//
//    BufferedInputStream in = new BufferedInputStream(socket.getInputStream());
//    BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
//
//    I've read that performance takes a hit when writing over 8 KiB to OutputStream, 
//    it was recommended that it'd be written to it in small chunks not pver 8 KiB at a time.
//    8 KiB is the default buffer size of a BufferedOutputStream.
//    
//    public BufferedOutputStream(OutputStream out) {
//        this(out, 8192);
//    }

}
