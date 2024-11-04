package utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import exceptions.FileProcessingException;
import model.Match;
import model.Player;
import model.Round;
import model.Tournament;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JSONConverter {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private JSONConverter() {
    }

    static {
        // Static block that configures the objMapper to recognize dates
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
        //objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    }
    /*
    public static <T> List<T> fromJsonArrayToList(String jsonString, Class<T> tClass) throws JsonProcessingException {
        List<T> list = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, tClass);
            list = objectMapper.readValue(jsonString, type);
        } catch (JsonProcessingException e) {
            // Guarda el contenido no serializable en un archivo de respaldo
            PersistenceFile.saveUnserializableContent(tClass.getSimpleName(), jsonString);
        }
        return list;
    }
    */
    public static <T> List<T> fromJsonArrayToList(String jsonString, Class<T> tClass) throws JsonProcessingException {
        try {
            return objectMapper.readValue(jsonString, objectMapper.getTypeFactory().constructCollectionType(List.class, tClass));
        } catch (JsonProcessingException e) {
            PersistenceFile.saveUnserializableContent(tClass.getSimpleName(), jsonString);
            throw e; // Lanza la excepción para que el error se registre en el lugar que llama a este método
        }
    }

    /*public static <T> List<T> fromJsonArrayToList(String jsonString, Class<T> tClass) throws JsonProcessingException, IllegalArgumentException {
        JavaType tournamentListType = objectMapper.getTypeFactory().constructCollectionType(
                List.class,
                objectMapper.getTypeFactory().constructParametricType(Tournament.class, Round.class, Match.class, Player.class)
        );

        List<Map<String, Object>> mapList;
        List<T> list = new ArrayList<>();
        try {
            mapList = objectMapper.readValue(jsonString, List.class);
            for (Map<String, Object> t : mapList) {
                list.add(objectMapper.convertValue(t, tClass));
            }
        } catch (JsonProcessingException | IllegalArgumentException | FileProcessingException e) {
            //PersistenceFile.saveUnserializableContent(tClass.getSimpleName(), jsonString);
            return list;
        }
        return list;
    }
*/
    public static String toJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
