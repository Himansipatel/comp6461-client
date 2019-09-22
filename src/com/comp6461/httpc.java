package com.comp6461;

public class httpc {

    public static void main(String[] args) {
        httpc httpc = new httpc();
        String action = args[0];
        switch (action) {
            case Constants.getMethod:
                GetRequest getRequest = httpc.setGetRequestFormatterObject(args);
                getRequest.setMethod();
                getRequest.executeRequest();
                break;
            case Constants.postMethod:
                PostRequest postRequest = httpc.setPostRequestFormatterObject(args);
                postRequest.setMethod();
                postRequest.executeRequest();
                break;
            case Constants.help:
                httpc.showHelpMessage(args);
                break;
        }
    }

    private void showHelpMessage(String[] args) {
        HelperCommandInformation helperCommandInformation = new HelperCommandInformation();
        helperCommandInformation.executeHelpCommand(args);
    }

    private GetRequest setGetRequestFormatterObject(String[] args) {
        GetRequest getRequest = new GetRequest();
        for (int i = 1; i < args.length; i++) {
            switch (args[i]) {
                case "-v":
                    getRequest.setVerbose();
                    break;
                case "-h":
                    getRequest.setHeader();
                    i = i + 1;
                    getRequest.setHeaderValue(args[i]);
                    break;
                default:
                    String url = args[i];
                    if (url.startsWith("\"") || url.startsWith("'")) {
                        url = url.substring(1, url.length() - 1);
                    }
                    getRequest.setUrl(url);
            }
        }
        return getRequest;
    }

    private PostRequest setPostRequestFormatterObject(String[] args) {
        PostRequest postRequest = new PostRequest();
        for (int i = 1; i < args.length; i++) {
            switch (args[i]) {
                case "-v":
                    postRequest.setVerbose();
                    break;
                case "-h":
                    postRequest.setHeader();
                    postRequest.setHeaderValue(args[i + 1]);
                    break;
                case "-d":
                    postRequest.setInlineDataFlag();
                    String inlineData = args[i + 1];
                    if (inlineData.startsWith("\"") || inlineData.startsWith("'")) {
                        inlineData = inlineData.substring(1, inlineData.length() - 1);
                    }
                    postRequest.setInlineDataValue(inlineData);
                    break;
                case "-f":
                    postRequest.setFileFlag();
                    postRequest.setFileName(args[i + 1]);
                    break;
                default:
                    String url = args[i];
                    if (url.startsWith("\"") || url.startsWith("'")) {
                        url = url.substring(1, url.length() - 1);
                    }
                    postRequest.setUrl(url);
                    break;
            }
        }
        return postRequest;
    }
}
