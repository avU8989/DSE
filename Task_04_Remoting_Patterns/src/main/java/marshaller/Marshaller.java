package marshaller;

import client.protocol.RPCRequest;

import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class Marshaller implements MarshalOperations {
    private static final String DELIMITER = ";";
    private static final String ESCAPE_CHARACTER = "\\";
    private static final Logger logger = Logger.getLogger(Marshaller.class.getName());

    @Override
    public byte[] marshal(String method, String message) {
        if(method.equals("")){
            return message.getBytes(StandardCharsets.UTF_8);
        }
        String parameterName = message.replace(DELIMITER, ESCAPE_CHARACTER + DELIMITER );
        String finalMessage = method + DELIMITER + parameterName;
        return finalMessage.getBytes(StandardCharsets.UTF_8);
    }
    @Override
    public RPCRequest unmarshal(byte[] data) {
        String responseString = new String(data, StandardCharsets.UTF_8);
        String[] parts = responseString.split(DELIMITER, 2);
        RPCRequest response = null;

        if (parts.length > 1) {
            String methodName = parts[0];
            String parameterName = (parts.length == 2) ? parts[1] : "";

            parameterName = parameterName.replace(ESCAPE_CHARACTER + DELIMITER, DELIMITER);
            parameterName = parameterName.replace(ESCAPE_CHARACTER + ESCAPE_CHARACTER, ESCAPE_CHARACTER);

            return new RPCRequest(methodName, parameterName);
        } else {
            if(responseString.equals("Error: Invalid method")){
                response = new RPCRequest();
                response.setErrorMessage(responseString);
                return response;
            }

            response = new RPCRequest(responseString);
            return response;
        }
    }

}
