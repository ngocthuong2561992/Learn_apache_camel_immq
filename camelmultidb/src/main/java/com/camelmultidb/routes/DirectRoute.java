package com.camelmultidb.routes;

import com.camelmultidb.process.RestExceptionHandler;
import com.camelmultidb.repository.mariaDB.CountryRepository;
import com.camelmultidb.repository.mssql.RentalNewRepository;
import com.camelmultidb.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.util.json.JsonObject;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DirectRoute extends RouteBuilder {
	final ServiceBean serviceBean;
	final ApiService apiService;
	final RestExceptionHandler restExceptionHandler;
	final CityService cityService;
	final RentalService rentalService;
	final RestService restService;
//	final ActorService actorService;
	final RentalNewRepository rentalNewRepository;
	final CountryRepository countryRepository;
	final StringEncryptor encryptorBean;

	@Override
	public void configure() throws Exception {
//		df.setModuleClassNames("com.fasterxml.jackson.datatype.jsr310.JavaTimeModule");
//		JacksonDataFormat jsonDf = new JacksonDataFormat(ActorEntity.class);
//		jsonDf.setPrettyPrint(true);

		onException(Exception.class)
//				.handled(true)
//				.maximumRedeliveries(2)
				.process(restExceptionHandler)
				.handled(true)
				.markRollbackOnlyLast()
				.end();

		from("direct:hello")
				.process(exchange -> {
//					exchange.getIn().setBody(encryptorBean.encrypt("A!123456"));
//					var data = rentalNewRepository.findAll();
					var country = countryRepository.findById(1).get();
					JsonObject json = new JsonObject();
//					json.put("rental", data);
					json.put("country", country);
					exchange.getIn().setBody(json);
				});
//				.transform(simple("Random number ${random(0,100)}"))
//				.transform().constant("Hello World direct")
//				.to("ibmmq:queue:DEV.QUEUE.1");

		from("direct:getActor")
				.bean(apiService, "getActor")
//				.bean(restService, "getUser")
				.process(exchange -> {
//					var body = exchange.getIn().getBody();
//					System.out.println(exchange.getIn().getBody());
				});
//				.unmarshal().json(Object.class);
//				.marshal(jsonDf);

		String postfix = " 11";
		int inventoryId = 11;
		from("direct:handleTransactional")
				.transacted("txPolicyMariadb")
//				.bean(apiService, "handleTransactional")
				.process(exchange -> cityService.saveCity("Ziguinchor" + postfix))
				.process(exchange -> rentalService.saveRental(inventoryId))
				.process(exchange -> {
					System.out.println("aaa: " + exchange.getIn().getBody().toString());
					int a = 1/0;
					exchange.getIn().setBody("success");
				});
//
		from("direct:importExcel")
//				.unmarshal().mimeMultipart()
//				.setHeader(Exchange.CONTENT_TYPE, constant("multipart/form-data"))
				.transacted("txPolicyMssql")
				.bean(apiService, "importExcel")
				.process(exchange -> {
					System.out.println("aaa: " + exchange.getIn().getBody().toString());
//					int a = 1/0;
					exchange.getIn().setBody("success");
				});

//		from("timer:timer1?period={{timer.period}}")
//				.process(myProcessor)
////			.to("direct:hello")
//			.log("${body}");

	}

}
