package com.comp6461;

public class GetRequestFormatter {

    private String method = "";
    private boolean verbose = false;
    private boolean header =  false;
    private String url = "";
    private String data = "";

    public GetRequestFormatter() {
    }

    public GetRequestFormatter(boolean verbose, boolean header, String url, String data,String method) {
        this.verbose = verbose;
        this.method = method;
        this.header = header;
        this.url = url;
        this.data = data;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public boolean isHeader() {
        return header;
    }

    public void setHeader(boolean header) {
        this.header = header;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "";
    }
}
