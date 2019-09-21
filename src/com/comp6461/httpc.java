package com.comp6461;

public class httpc {

    public static void main(String[] args) {
        httpc httpc = new httpc();
        HttpRequestExecutor httpRequestExecutor = new HttpRequestExecutor();
        String action = args[0];
        switch (action) {
            case Constants.getMethod:
                GetRequestFormatter getRequestFormatter = httpc.setGetRequestFormatterObject(args);
                httpRequestExecutor.executeGetRequest(getRequestFormatter);
                break;
            case Constants.postMethod:
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

    private GetRequestFormatter setGetRequestFormatterObject(String[] args) {
        GetRequestFormatter getRequestFormatter = new GetRequestFormatter();
        for (int i = 1; i < args.length; i++) {
            switch (args[i]) {
                case "-v":
                    getRequestFormatter.setVerbose(true);
                    break;
                case "-h":
                    getRequestFormatter.setHeader(true);
                    getRequestFormatter.setData(args[i + 1]);
                    break;
                default:
                    getRequestFormatter.setUrl(args[i]);
            }
        }
        return getRequestFormatter;
    }
}
