package moviegoods.movie.configure;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSockConfig implements WebSocketMessageBrokerConfigurer {

    public void configureMessageBroker(MessageBrokerRegistry config){
        config.enableSimpleBroker("/sub"); //메세지 구독 요청
        config.setApplicationDestinationPrefixes("/pub");  //메세지 발행 요청


    }
    public void registerStompEndpoints(StompEndpointRegistry registry){
<<<<<<< HEAD
        registry.addEndpoint("/ws-stomp").setAllowedOriginPatterns("*").withSockJS();

    }

}
=======
        registry.addEndpoint("/ws-stomp")
                .setAllowedOriginPatterns("*")
                .withSockJS();

    }

}
>>>>>>> 57d200fd9a1e6ea7553b2cee71f8b89f46055647
