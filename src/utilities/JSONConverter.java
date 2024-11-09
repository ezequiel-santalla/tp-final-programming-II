package utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.List;

public class JSONConverter {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private JSONConverter() {
    }

    static {
        // Static block that configures the objMapper to recognize dates
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }
    public static <T> List<T> fromJsonArrayToList(String jsonString, Class<T> tClass) throws JsonProcessingException {
        try {
            return objectMapper.readValue(jsonString, objectMapper.getTypeFactory().constructCollectionType(List.class, tClass));
        } catch (JsonProcessingException e) {
            PersistenceFile.saveUnserializableContent(tClass.getSimpleName(), jsonString);
            throw e; // Lanza la excepción para que el error se registre en el lugar que llama a este método
        }
    }

    public static String toJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
