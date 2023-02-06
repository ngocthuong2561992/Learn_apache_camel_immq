package stb.com.vn.spring.processor;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HandleProcess implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        log.info("HandleProcess: {}", exchange.getIn().getBody(String.class));
    }
}