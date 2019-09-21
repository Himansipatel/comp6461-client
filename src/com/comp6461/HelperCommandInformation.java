package com.comp6461;

class HelperCommandInformation {

    void executeHelpCommand(String[] args) {
        String command = fetchHelpCommandFromArr(args);
        switch (command.trim()) {
            case "":
                showAppInstruction();
                break;
            case "get":
                showGetInstruction();
                break;
            case "post":
                showPostInstruction();
                break;
            default:
                System.err.println("Please make sure you see httpc manual below");
                showAppInstruction();
                break;
        }
    }

    private void showAppInstruction() {
        System.out.println("httpc is a curl-like application but supports HTTP protocol only.");
        System.out.println("Usage:\n\thttpc command [arguments]");
        System.out.println("The commands are:");
        System.out.println("get\t\texecutes a HTTP GET request and prints the response.");
        System.out.println("post\texecutes a HTTP POST request and prints the response.");
        System.out.println("help\tprints this screen.");
        System.out.println("\nUse \"httpc help [command]\" for more information about a command.");
    }

    private void showGetInstruction() {
        System.out.println("usage: httpc get [-v] [-h key:value] URL");
        System.out.println("Get executes a HTTP GET request for a given URL.");
        System.out.println("-v\tPrints the detail of the response such as protocol, status, and headers.");
        System.out.println("-h\tkey:value\tAssociates headers to HTTP Request with the format 'key:value'.");
    }

    private void showPostInstruction() {
        System.out.println("usage: httpc post [-v] [-h key:value] [-d inline-data] [-f file] URL");
        System.out.println("Post executes a HTTP POST request for a given URL with inline data or from file.");
        System.out.println("-v\tPrints the detail of the response such as protocol, status, and headers.");
        System.out.println("-h\tkey:value\tAssociates headers to HTTP Request with the format 'key:value'.");
        System.out.println("-d\tstring\tAssociates an inline data to the body HTTP POST request.");
        System.out.println("-f\tfile\tAssociates the content of a file to the body HTTP POST request.");
        System.out.println("Either [-d] or [-f] can be used but not both");
    }

    private String fetchHelpCommandFromArr(String[] args) {
        StringBuilder helpCommand = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            helpCommand.append(args[i]);
        }
        return helpCommand.toString();
    }
}
