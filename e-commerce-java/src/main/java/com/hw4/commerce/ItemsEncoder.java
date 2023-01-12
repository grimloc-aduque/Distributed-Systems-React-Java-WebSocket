package com.hw4.commerce;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.util.ArrayList;

public class ItemsEncoder implements Encoder.Text<ArrayList<Item>> {

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }

    @Override
    public String encode(ArrayList<Item> items){
        String JSON = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            JSON = mapper.writeValueAsString(items);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return JSON;
    }
}