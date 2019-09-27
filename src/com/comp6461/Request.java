package com.comp6461;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Request {

    private boolean verbose = false;
    private boolean header = false;
    private boolean inlineDataFlag = false;
    private boolean writeToFile = false;
    private boolean fileFlag = false;
    private String method = "";
    private String url = "";
    private String headerValue = "";
    private String inlineDataValue = "";
    private String fileName = "";
    private String data = "";
    private String outputFileName = "";


    public boolean isWriteToFile() {
        return writeToFile;
    }

    public void setWriteToFile(boolean writeToFile) {
        this.writeToFile = writeToFile;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    Request() {
    }

    public String getMethod() {
        return method;
    }

    void setMethod(String method) {
        this.method = method;
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

    public boolean isInlineDataFlag() {
        return inlineDataFlag;
    }

    void setInlineDataFlag() {
        this.inlineDataFlag = true;
    }

    public String getInlineDataValue() {
        return inlineDataValue;
    }

    void setInlineDataValue(String inlineDataValue) {
        this.inlineDataValue = inlineDataValue;
    }

    public boolean isFileFlag() {
        return fileFlag;
    }

    void setFileFlag() {
        this.fileFlag = true;
    }

    public String getFileName() {
        return fileName;
    }

    void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    void executeRequest() {
        try {
            String contentPost = "";
            boolean printFromStart = this.isVerbose();
            URL url = new URL(this.getUrl());
            String host = url.getHost();
            String queryString = this.getQueryString(url);
            InetAddress inetAddress = InetAddress.getByName(host);
            Socket socket = new Socket(inetAddress, 80);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
            bufferedWriter.write(this.getMethod().toUpperCase() + " " + queryString + " HTTP/1.0\r\n");
            bufferedWriter.write("Host: " + host + "\r\n");
            if (this.getMethod().equalsIgnoreCase("post")) {
                contentPost = this.getDataBasedOnFlagSet();
                bufferedWriter.write("Content-Length: " + contentPost.length() + "\r\n");
            }
            if (this.isHeader() && !this.getHeaderValue().equalsIgnoreCase("")) {
                bufferedWriter.write("Content-Type: " + this.getHeaderValue() + "\r\n");
            }
            if (this.getMethod().equalsIgnoreCase("post")) {
                bufferedWriter.write("\r\n");
                bufferedWriter.write(contentPost);
            }
            bufferedWriter.write("\r\n");
            bufferedWriter.flush();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response = "";
            StringBuilder responseData = new StringBuilder();
            while ((response = bufferedReader.readLine()) != null) {
                if (printFromStart) {
                    responseData.append(response).append("\n");
                } else {
                    printFromStart = response.isEmpty();
                }
            }
            System.out.println(responseData);
            if (this.isWriteToFile()) {
                writeResponseToFile(responseData.toString());
            }
            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeResponseToFile(String responseData) {
        String outputFile = this.getOutputFileName();
        File file = new File(outputFile);
        file.setWritable(true);
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(responseData);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getQueryString(URL url) {
        String queryString = "";
        if (url.getPath() != null && !url.getPath().equalsIgnoreCase(""))
            queryString += url.getPath();
        if (this.getMethod().equalsIgnoreCase("get")) {
            if (url.getQuery() != null && !url.getQuery().equalsIgnoreCase(""))
                queryString += "?" + url.getQuery();
        }
        return queryString;
    }

    private String getDataBasedOnFlagSet() throws IOException {
        String data = "";
        if (this.isInlineDataFlag()) {
            data = this.inlineDataValue;
        } else if (this.isFileFlag()) {
            data = this.readFileData(this.getFileName());
        }
        return data;
    }

    private String readFileData(String filePath) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        String line = "";
        StringBuilder data = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            data.append(line);
        }
        bufferedReader.close();
        return data.toString();
    }
}
