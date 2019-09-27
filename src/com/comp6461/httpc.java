package com.comp6461;

public class httpc {

    public static void main(String[] args) {
        httpc httpc = new httpc();
        String action = args[0];
        Request request = null;
        switch (action) {
            case Constants.getMethod:
                request = httpc.setRequestObject(args);
                if (httpc.validateRequest(request))
                    request.executeRequest(request.getUrl());
                else
                    System.out.println("Please enter a valid request");
                break;
            case Constants.postMethod:
                request = httpc.setRequestObject(args);
                if (httpc.validateRequest(request))
                    request.executeRequest(request.getUrl());
                else
                    System.out.println("Please enter a valid request");
                break;
            case Constants.help:
                httpc.showHelpMessage(args);
                break;
        }
    }

    private boolean validateRequest(Request request) {
        String method = request.getMethod();
        boolean validateFlag = true;
        switch (method.toLowerCase()) {
            case Constants.getMethod:
                validateFlag = !request.isFileFlag() && !request.isInlineDataFlag();
                break;
            case Constants.postMethod:
                validateFlag = !request.isFileFlag() || !request.isInlineDataFlag();
                break;
            default:
                break;
        }
        return validateFlag;
    }

    private void showHelpMessage(String[] args) {
        HelperCommandInformation helperCommandInformation = new HelperCommandInformation();
        helperCommandInformation.executeHelpCommand(args);
    }

    private Request setRequestObject(String[] args) {
        Request request = new Request();
        request.setMethod(args[0]);
        for (int i = 1; i < args.length; i++) {
            switch (args[i]) {
                case "-v":
                    request.setVerbose();
                    break;
                case "-h":
                    request.setHeader();
                    i = i + 1;
                    request.setHeaderValue(args[i]);
                    break;
                case "-d":
                    request.setInlineDataFlag();
                    i = i + 1;
                    String inlineData = args[i];
                    if (inlineData.startsWith("\"") || inlineData.startsWith("'")) {
                        inlineData = inlineData.substring(1, inlineData.length() - 1);
                    }
                    request.setInlineDataValue(inlineData);
                    break;
                case "-f":
                    request.setFileFlag();
                    i = i + 1;
                    String fileName = args[i];
                    if (fileName.startsWith("\"") || fileName.startsWith("'")) {
                        fileName = fileName.substring(1, fileName.length() - 1);
                    }
                    request.setFileName(fileName);
                    break;
                case "-o":
                    request.setWriteToFile(true);
                    i = i + 1;
                    String outputFileName = args[i];
                    if (outputFileName.startsWith("\"") || outputFileName.startsWith("'")) {
                        outputFileName = outputFileName.substring(1, outputFileName.length() - 1);
                    }
                    request.setOutputFileName(outputFileName);
                    break;
                default:
                    String url = args[i];
                    if (url.startsWith("\"") || url.startsWith("'")) {
                        url = url.substring(1, url.length() - 1);
                    }
                    request.setUrl(url);
                    break;
            }
        }
        return request;
    }
}
