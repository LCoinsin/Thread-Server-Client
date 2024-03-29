package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MapperMessage {

    ObjectMapper objectMapper;

    public MapperMessage() {
        objectMapper = new ObjectMapper();
    }

    public Message jsonToObject(String s) {
        try {
            return objectMapper.readValue(s, Message.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String objectToJson(Message o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

}
