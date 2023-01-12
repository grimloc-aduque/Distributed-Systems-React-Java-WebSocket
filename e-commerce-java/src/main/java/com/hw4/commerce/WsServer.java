package com.hw4.commerce;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.List;

@ServerEndpoint(
        value="/items",
        decoders={ItemsDecoder.class},
        encoders={ItemsEncoder.class}
)

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)

public class WsServer {

    private static List<Session> sessions = new ArrayList<>();

    private Session session;

    @OnOpen
    public void whenOpening(Session session) throws Exception {
        System.out.println("\n------------- NEW CONNECTION -------------\n");
        this.session = session;
        WsServer.sessions.add(this.session);
        List<Item> items = DataStore.getItems();
        this.session.getBasicRemote().sendObject(items);
    }

    @OnMessage
    public void whenGettingItems(ArrayList<Item> items) throws Exception {
        System.out.println("\n------------- UPDATE ITEMS -------------\n");
        DataStore.updateItems(items);
        this.pushToOtherSessions(items);
    }

    @OnClose
    public void whenClosing() {
        System.out.println("\n------------- CLOSE CONNECTION -------------\n");
        WsServer.sessions.remove(this.session);
    }


    private void pushToOtherSessions(List<Item> items) throws Exception{
        System.out.println("\n------------- PUSH ITEMS -------------\n");
        for(Session session: WsServer.sessions){
            if(this.session != session)
                session.getBasicRemote().sendObject(items);
        }
    }
}
