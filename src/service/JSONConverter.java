package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import exceptions.FileProcessingException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JSONConverter {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        //bloque estático que configura el objMapper para que pueda reconocer fechas
        objectMapper.registerModule(new JavaTimeModule());
    }

    public static <T> List<T> fromJsonArrayToList(String jsonString, Class<T> tClass) throws JsonProcessingException, IllegalArgumentException {
        List<Map<String, Object>> mapList;
        List<T> list = new ArrayList<>();
        try {
            mapList = objectMapper.readValue(jsonString, List.class);
            for (Map<String, Object> t : mapList) {
               list.add(objectMapper.convertValue(t, tClass));
             }
        } catch (JsonProcessingException | IllegalArgumentException | FileProcessingException e) {
            new PersistenceFile().saveUnserializableContent(tClass.getName(), jsonString);
            return list;
        }
        return list;
    }



    public static String toJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

}
