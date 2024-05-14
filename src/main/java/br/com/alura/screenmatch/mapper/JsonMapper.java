package br.com.alura.screenmatch.mapper;

public interface JsonMapper {
    public <T> T jsonToClass(String json, Class<T> classT);
}
