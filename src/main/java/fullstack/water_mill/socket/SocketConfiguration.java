package fullstack.water_mill.socket;

import fullstack.water_mill.service.MillService;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

public class SocketConfiguration implements WebSocketConfigurer {
    private final MillService millService;

    public SocketConfiguration(MillService millService) {
        this.millService = millService;
    }


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(new MillSocketHandler(millService), "/websockets").setAllowedOrigins("*");
    }
}
