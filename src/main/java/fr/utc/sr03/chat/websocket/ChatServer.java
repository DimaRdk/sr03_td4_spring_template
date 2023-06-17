package fr.utc.sr03.chat.websocket;

import fr.utc.sr03.chat.dao.ChatRepository;
import fr.utc.sr03.chat.dao.UserRepository;
import fr.utc.sr03.chat.model.Chat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

@Component
@ServerEndpoint(value="/chatserver/{chatid}/{pseudo}", configurator= ChatServer.EndpointConfigurator.class)
public class ChatServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatServer.class);

    private static ChatServer singleton = new ChatServer();
    private final Hashtable<String, Session> sessions = new Hashtable<>();


    private ChatServer() {}

    public static class EndpointConfigurator extends ServerEndpointConfig.Configurator {
        @Override
        @SuppressWarnings("unchecked")
        public <T> T getEndpointInstance(Class<T> endpointClass) {
            return (T) ChatServer.getInstance();
        }
    }

    public static ChatServer  getInstance() {
        return ChatServer.singleton;
    }


    @OnOpen
    public void open(Session session, @PathParam("chatid") long chatid,
                     @PathParam("pseudo") String pseudo ) {

        sendMessage("Admin >>> Connection established for " + pseudo,
                chatid );
        session.getUserProperties().put( "pseudo", pseudo );
        session.getUserProperties().put( "chatid", chatid );
        sessions.put( session.getId(), session );

        List<String> pseudoList = new ArrayList<String>();
        for( Session s : sessions.values() ) {
            long id = (long) s.getUserProperties().get( "chatid" );
            if(chatid == id) {
                String pseudoUsers = (String) s.getUserProperties().get( "pseudo" );
                if (!pseudoList.contains(pseudoUsers)) {
                    pseudoList.add(pseudoUsers);
                }
            }
        }
        sendMessage("[UserConnectedList]> " + pseudoList.toString(),
                chatid);
        pseudoList.clear();
    }


    @OnClose
    public void close(Session session) {
        String pseudo = (String) session.getUserProperties().get( "pseudo"
        );
        long chatid = (long) session.getUserProperties().get( "chatid" );
        sessions.remove( session.getId() );
        sendMessage("Admin >>> Connection closed for " + pseudo, chatid );
        /*...*/
    }

    @OnError
    public void onError(Throwable error) {
        LOGGER.error(error.getMessage());
    }

    @OnMessage
    public void handleMessage(String message, Session session) {
        String pseudo = (String) session.getUserProperties().get( "pseudo"
        );
        long chatid = (long) session.getUserProperties().get( "chatid" );
        String fullMessage = pseudo + " >>> " + message;
        sendMessage(fullMessage, chatid);
    }

    private void sendMessage(String fullMessage, long chatid ) {
// Affichage sur la console du server Web.
        System.out.println( fullMessage );
// On envoie le message a tout le monde.
        for( Session session : sessions.values() ) {
            try {
                long id = (long) session.getUserProperties().get( "chatid" );
                if(chatid == id ) { //&& pseudo != userPseudo
                    session.getBasicRemote().sendText(fullMessage);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
