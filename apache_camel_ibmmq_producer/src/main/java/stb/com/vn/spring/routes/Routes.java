//package stb.com.vn.springDb2.routes;
//
//import org.apache.camel.builder.RouteBuilder;
//import org.springframework.stereotype.Component;
//
//@Component
//public class Routes extends RouteBuilder{
//
//
//	@Override
//	public void configure() throws Exception {
//		// TODO Auto-generated method stub
//		String offsetStorageFileName = "D:\\route\\apache-camel\\debezium-db2\\offset-file-1.dat";
//		String databaseHistoryFileFilename = "D:\\route\\apache-camel\\debezium-db2\\history-file-1.dat";
//		String host ="localhost";
//		String username ="db2inst1";
//		String password ="db2inst1-pwd";
//		String databaseServerName ="TESTDB2";
//
//		from("debezium-db2:TESTDB2?offsetStorageFileName= " + offsetStorageFileName
//				+ "&databaseHostname=" + host
//				+ "&databaseUser=" + username
//				+ "&databasePassword=" + password
//				+ "&databaseServerName=" + databaseServerName
//				+ "&databaseHistoryFileFilename=" + databaseHistoryFileFilename)
//
//
//	    .log("Event received from Debezium : ${body}")
//	    .log("    with this identifier ${headers.CamelDebeziumIdentifier}")
//	    .log("    with these source metadata ${headers.CamelDebeziumSourceMetadata}")
//	    .log("    the event occured upon this operation '${headers.CamelDebeziumSourceOperation}'")
//	    .log("    on this database '${headers.CamelDebeziumSourceMetadata[db]}' and this table '${headers.CamelDebeziumSourceMetadata[table]}'")
//	    .log("    with the key ${headers.CamelDebeziumKey}")
//	    .log("    the previous value is ${headers.CamelDebeziumBefore}");
//
////		from("debezium-db2:dbz:db2?offsetStorageFileName + " + offsetStorageFileName
////				+"&databaseHostName=" + host
////				+"&databaseUser=" + username
////				+"&databasePassword=" + password
////				+"&databaseServerName=" + db
////				+"&databaseDbname=" + db
////				+"&pluginName=pgoutput")
////		.log("EVENTO: {body}"); //decoderbufs
//	}
//
//
//}
