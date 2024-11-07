package telran.net;

public class Main {
    private static final int SERVER_PORT = 4000;

    static Protocol protocol = new Protocol() {
        @Override
        public Response getResponse(Request request) {
            String requestData = request.requestData();
            ResponseCode responseCode = ResponseCode.OK;
            String responseData = switch (request.requestType()) {
                case "reverse" -> new StringBuilder(requestData).reverse().toString();
                case "length" -> String.valueOf(requestData.length());
                default -> {
                    responseCode = ResponseCode.WRONG_TYPE;
                    yield "server doesn't understand your command";
                }
            };
            return new Response(responseCode, responseData);
        }
    };
    static TcpServer server = new TcpServer(protocol, SERVER_PORT);

    public static void main(String[] args) {
        server.run();
    }
}