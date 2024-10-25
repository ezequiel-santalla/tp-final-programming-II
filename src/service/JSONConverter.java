package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JSONConverter {
    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
    }

    public static <T> List<T> fromJsonArrayToList(String jsonString, Class<T> tClass) throws JsonProcessingException, IllegalArgumentException {
        List<Map<String, Object>> mapList;
        mapList = objectMapper.readValue(jsonString, List.class);
        List<T> list = new ArrayList<>();

        for (Map<String, Object> t : mapList) {
            list.add(objectMapper.convertValue(t, tClass));
        }
        return list;
    }


    public static String toJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
