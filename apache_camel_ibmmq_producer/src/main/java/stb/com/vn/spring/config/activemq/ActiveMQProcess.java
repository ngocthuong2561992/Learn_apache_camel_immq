package stb.com.vn.spring.config.activemq;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActiveMQProcess {
    private final Gson gson;

    public void executeMessage(ActiveMQTextMessage message) {
        try {
            log.info("Receiving a message: {}", message.getText());

            // TODO handle this message later...

        } catch (Exception ex) {
            log.error("Error when executing ActiveMQ Message: ", ex);
        }
    }
}
