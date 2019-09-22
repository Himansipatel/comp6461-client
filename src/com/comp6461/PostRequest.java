package com.comp6461;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class PostRequest {

    private String method = "";
    private boolean verbose = false;
    private boolean header = false;
    private boolean inlineDataFlag = false;
    private String inlineDataValue = "";
    private boolean fileFlag = false;
    private String fileName = "";
    private String headerValue = "";
    private String url = "";
    private String data = "";

    PostRequest() {
    }

    public String getMethod() {
        return method;
    }

    void setMethod() {
        this.method = Constants.postMethod;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    private String getHeaderValue() {
        return headerValue;
    }

    void setHeaderValue(String headerValue) {
        this.headerValue = headerValue;
    }

    private boolean isInlineDataFlag() {
        return inlineDataFlag;
    }

    void setInlineDataFlag() {
        this.inlineDataFlag = true;
    }

    private boolean isFileFlag() {
        return fileFlag;
    }

    void setFileFlag() {
        this.fileFlag = true;
    }

    public String getInlineDataValue() {
        return inlineDataValue;
    }

    void setInlineDataValue(String inlineDataValue) {
        this.inlineDataValue = inlineDataValue;
    }

    public String getFileName() {
        return fileName;
    }

    void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "PostRequest{" +
                "method='" + method + '\'' +
                ", verbose=" + verbose +
                ", header=" + header +
                ", inlineDataFlag=" + inlineDataFlag +
                ", inlineDataValue='" + inlineDataValue + '\'' +
                ", fileFlag=" + fileFlag +
                ", fileName='" + fileName + '\'' +
                ", headerValue='" + headerValue + '\'' +
                ", url='" + url + '\'' +
                ", data='" + data + '\'' +
                '}';
    }

    void executeRequest() {
        try {
            boolean printFromStart = this.isVerbose();
            String contentPost = this.getDataBasedOnFlagSet();
            URL url = new URL(this.getUrl());
            String host = url.getHost();
            String queryString = "";
            if (url.getPath() != null && !url.getPath().equalsIgnoreCase(""))
                queryString += url.getPath();
            InetAddress inetAddress = InetAddress.getByName(host);
            Socket socket = new Socket(inetAddress, 80);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
            bufferedWriter.write("POST " + queryString + " HTTP/1.0\r\n");
            bufferedWriter.write("Host: " + host + "\r\n");
            bufferedWriter.write("Content-Length: " + contentPost.length() + "\r\n");
            if (this.isHeader() && !this.getHeaderValue().equalsIgnoreCase("")) {
                bufferedWriter.write("Content-Type: application/json\r\n");
            }
            bufferedWriter.write("\r\n");
            bufferedWriter.write(contentPost);
            bufferedWriter.write("\r\n");
            bufferedWriter.flush();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response = "";
            while ((response = bufferedReader.readLine()) != null) {
                if (printFromStart) {
                    System.out.println(response);
                } else {
                    printFromStart = response.isEmpty();
                }
            }
            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getDataBasedOnFlagSet() {
        if (this.isInlineDataFlag()) {
            return this.inlineDataValue;
        } else if (this.isFileFlag()) {
            return "File Value";
        }
        return "";
    }
}
