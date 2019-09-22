package com.comp6461;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class GetRequest {

    private String method = "";
    private boolean verbose = false;
    private boolean header = false;
    private String url = "";
    private String headerValue = "";

    GetRequest() {
    }

    public String getMethod() {
        return method;
    }

    void setMethod() {
        this.method = Constants.getMethod;
    }

    private boolean isVerbose() {
        return verbose;
    }

    void setVerbose() {
        this.verbose = true;
    }

    private boolean isHeader() {
        return header;
    }

    void setHeader() {
        this.header = true;
    }

    private String getUrl() {
        return url;
    }

    void setUrl(String url) {
        this.url = url;
    }

    private String getHeaderValue() {
        return headerValue;
    }

    void setHeaderValue(String headerValue) {
        this.headerValue = headerValue;
    }

    void executeRequest() {
        try {
            boolean printFromStart = this.isVerbose();
            URL url = new URL(this.getUrl());
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
            if (this.isHeader() && !this.getHeaderValue().equalsIgnoreCase("")) {
                bufferedWriter.write(this.getHeaderValue() + "\r\n");
            }
            bufferedWriter.write("\r\n");
            bufferedWriter.flush();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                if (printFromStart) {
                    System.out.println(line);
                } else {
                    printFromStart = line.isEmpty();
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
