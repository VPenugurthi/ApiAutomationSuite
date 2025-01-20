package resourses;

import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
public class UtilsCopy {
	
	public RequestSpecification requestCall;
	public static ResponseSpecification responseCall;
	public PrintStream log;
	
	public RequestSpecification requestSpecification() throws IOException {
		if(requestCall==null) {
		log = new PrintStream(new FileOutputStream("logging.txt"));
		requestCall = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseUrl"))
				.addFilter(RequestLoggingFilter.logRequestTo(log))
				.addFilter(ResponseLoggingFilter.logResponseTo(log))
				.setContentType(ContentType.JSON).build();
		return requestCall;
		}
		return requestCall;
	}
	
	public RequestSpecification requestAddProductSpecification(String token) throws IOException {
		if(requestCall==null) {
		log = new PrintStream(new FileOutputStream("logging.txt"));
		requestCall = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseUrl")).addHeader("Authorization", token)
				.addFilter(RequestLoggingFilter.logRequestTo(log))
				.addFilter(ResponseLoggingFilter.logResponseTo(log))
				.setContentType(ContentType.MULTIPART).build();
		return requestCall;
		}
		return requestCall;
	}
	
	public RequestSpecification requestCreateOrderSpecification(String token) throws IOException {
		if(requestCall==null) {
		log = new PrintStream(new FileOutputStream("logging.txt"));
		requestCall = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseUrl")).addHeader("Authorization", token)
				.addFilter(RequestLoggingFilter.logRequestTo(log))
				.addFilter(ResponseLoggingFilter.logResponseTo(log))
				.setContentType(ContentType.JSON).build();
		return requestCall;
		}
		return requestCall;
	}
	
	public RequestSpecification requestDeleteOrderSpecification(String token) throws IOException {
		if(requestCall==null) {
		log = new PrintStream(new FileOutputStream("logging.txt"));
		requestCall = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseUrl")).addHeader("Authorization", token)
				.setContentType("application/json; charset=UTF-8")
				.addFilter(RequestLoggingFilter.logRequestTo(log))
				.addFilter(ResponseLoggingFilter.logResponseTo(log)).build();
		return requestCall;
		}
		return requestCall;
	}
	
	public ResponseSpecification responseSpecification() throws IOException {
		responseCall = new ResponseSpecBuilder().expectContentType(ContentType.JSON).build();
		return responseCall;
	}
	
	public static String getJsonPath(Response response,String key) {
		String res = response.asString();
		JsonPath js = new JsonPath(res);
		return js.get(key).toString();
	}

	public static String getGlobalValue(String key) throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream("C:\\Users\\vamsi\\eclipse-workspace\\ShopAutomate\\src\\test\\java\\resourses\\global.properties");
		prop.load(fis);
		return prop.getProperty(key);
	
	}

}
