package swa.meet.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import swa.meet.JacksonConfig;

import java.util.Map;

public class FieldFilter {

    public static ObjectNode filterFields(Object source, Map<String, Boolean> fields){
        ObjectMapper mapper = new JacksonConfig().objectMapper();

        ObjectNode rootNode = mapper.valueToTree(source);

        ObjectNode filterNode = mapper.createObjectNode();

        fields.forEach((field, include) ->{
            if(include && rootNode.has(field)){
                filterNode.set(field, rootNode.get(field));
            }
        });

        return filterNode;
    }
}
