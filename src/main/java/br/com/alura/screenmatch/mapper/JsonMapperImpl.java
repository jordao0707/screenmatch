package br.com.alura.screenmatch.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMapperImpl implements JsonMapper {
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T jsonToClass(String json, Class<T> classT) {
        try {
            return mapper.readValue(json, classT);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
