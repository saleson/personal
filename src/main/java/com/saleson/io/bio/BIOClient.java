package com.saleson.io.bio;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author saleson
 * @date 2022-03-02 00:34
 */
public class BIOClient {

    public static void main(String[] args) {
        try {
            connect("127.0.0.1", 8888);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void connect(String hostname, int port) throws IOException {
//        创建socket
        Socket socket = new Socket();
//        连接
        socket.connect(new InetSocketAddress(hostname, port));
//      输出流
//      BufferedWriter是缓冲输出流，OutputStreamWriter是转换输出流，OutputStream是字节输出流
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

//       标准输入流，从键盘输入
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String str = null;
        while ((str = br.readLine()) != null) {
//            如果从键盘输入quit，则退出循环
            if ("quit".equals(str)) {
                break;
            }
            bw.write(str);
//            需要写换行，因为server端每次读取一行
            bw.newLine();
            bw.flush();
        }

//        关闭流
        br.close();
//        关闭socket
        socket.close();

    }

}
