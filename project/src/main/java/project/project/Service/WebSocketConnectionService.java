package project.project.Service;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class WebSocketConnectionService {

    private final Map<String, String> userConnections = new HashMap<>();

    public void registerUserConnection(String username, String sessionId) {
        userConnections.put(username, sessionId);
    }


    public String getUserConnection(String username) {
        return userConnections.get(username);
    }

    public void removeUserConnection(String username) {
        userConnections.remove(username);
    }


    public boolean isUserOnline(String username) {
        return userConnections.containsKey(username);
    }
}
