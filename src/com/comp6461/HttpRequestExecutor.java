package com.comp6461;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;

class HttpRequestExecutor {

    private GetRequestFormatter getRequestFormatter;

    HttpRequestExecutor() {
    }

    void executeGetRequest(GetRequestFormatter getRequestFormatter) {
        this.getRequestFormatter = getRequestFormatter;
        processRequest();
    }

    private void processRequest() {
        try {
            URL url = new URL(this.getRequestFormatter.getUrl());
            String host = url.getHost();
            String queryString = "";
            if (url.getPath() != null && !url.getPath().equalsIgnoreCase(""))
                queryString += url.getPath();
            if (url.getQuery() != null && !url.getQuery().equalsIgnoreCase(""))
                queryString += "?" + url.getQuery();
            InetAddress inetAddress = InetAddress.getByName(host);
            Socket socket = new Socket(inetAddress, 80);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
            bufferedWriter.write("GET " + queryString + " HTTP/1.0\r\n");
            bufferedWriter.write("\r\n");
            bufferedWriter.flush();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            StringBuilder content = new StringBuilder();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n");
            }
            printVerboseContentOrNot(content.toString());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void printVerboseContentOrNot(String content) {
        if (!this.getRequestFormatter.isVerbose()) {
            content = content.split("\\n\\n",2)[1];
            System.out.println(content);
        } else {
            System.out.println(content);
        }
    }
}
