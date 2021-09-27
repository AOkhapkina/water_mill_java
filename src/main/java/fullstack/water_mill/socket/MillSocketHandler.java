package fullstack.water_mill.socket;

import com.google.gson.Gson;
import fullstack.water_mill.service.MillService;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MillSocketHandler extends TextWebSocketHandler {

    private final Gson gson = new Gson();

    private final MillService millService;

    public MillSocketHandler(MillService millService) {
        this.millService = millService;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        TextMessage msg = new TextMessage(gson.toJson(millService.getState()));
        session.sendMessage(msg);
    }
}
