package com.hw4.commerce;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.util.ArrayList;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ItemsDecoder implements Decoder.Text<ArrayList<Item>> {

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }

    @Override
    public ArrayList<Item> decode(String jsonInput){
        ArrayList<Item> items = null;
        ObjectMapper mapper = new ObjectMapper();
        TypeFactory typeFactory = mapper.getTypeFactory();
        CollectionType collectionType = typeFactory.constructCollectionType(ArrayList.class, Item.class);
        try {
            items = mapper.readValue(jsonInput, collectionType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return items;
    }


    @Override
    public boolean willDecode(String s) {
        return true;
    }
}