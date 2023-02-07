package com.camelmultidb.routes;

import com.camelmultidb.process.ConsumerExceptionHandler;
import com.camelmultidb.process.ConsumerSuccessHandler;
import com.camelmultidb.service.ApiService;
import com.camelmultidb.service.ServiceBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConsumerRoute extends RouteBuilder {
	final ServiceBean serviceBean;
	final ApiService apiService;
	final ConsumerExceptionHandler consumerExceptionHandler;
	final ConsumerSuccessHandler consumerSuccessHandler;
//	final CityService cityService;
//	final ActorService actorService;

	@Value("${queues.queueDev1}")
	private String queueDev1;

	@Value("${queue.selector.key}")
	private String selectorKey;

	@Value("${queue.selector.camel}")
	private String selectorCamel;

	@Override
	public void configure() throws Exception {
//		df.setModuleClassNames("com.fasterxml.jackson.datatype.jsr310.JavaTimeModule");
//		JacksonDataFormat jsonDf = new JacksonDataFormat(ActorEntity.class);
//		jsonDf.setPrettyPrint(true);

		onException(Exception.class)
//				.maximumRedeliveries(2)
				.process(consumerExceptionHandler)
				.handled(true)
				.markRollbackOnlyLast()
				.end();

		String postfix = " 9";
//		from("ibmmq:queue:DEV.QUEUE.1?selector=ADAPTER='CAMEL'")
		from("ibmmq:queue:" + queueDev1)
				.transacted("txPolicyMariadb")
				.filter(header(selectorKey).isEqualTo(selectorCamel))
				.log("Receive DEV.QUEUE.1: ${body}")
//				.process(exchange -> cityService.saveCity("Ziguinchor" + postfix))
				.process(exchange -> {
					System.out.println("aaa: " + exchange.getIn().getBody().toString());
					int a = 1/0;
					exchange.getIn().setBody("success");
				})
				.process(consumerSuccessHandler)
				.log("CAMEL: ${body}");

	}

}
