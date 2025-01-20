package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resourses.APIResources;
import resourses.PayLoad;
import resourses.Utils;
import static io.restassured.RestAssured.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

import POJO.LoginPage.LoginResponse;
import POJO.LoginPage.OrderDetailsResponse;
import POJO.LoginPage.OrderResponse;
import POJO.LoginPage.ProductResponse;

public class LoginSteps extends Utils {
	
	public RequestSpecification requestCall;
	public Response responseCall;
	private static String token;
	private static String userID;
	private static ArrayList<String> productIds = new ArrayList<>();
	private static List<String> orderIds = new ArrayList<>();
	
	PayLoad getPayloadData = new PayLoad();
	
	@Given("^the user provides valid '(.*)','(.*)' as login credentials$")
	public void the_user_provides_valid_login_credentials(String userName, String password) throws IOException {
		requestCall = given().spec(requestSpecification()).body(getPayloadData.userCredentials(userName, password));
	}
	
	@Given("^the user provides invalid '(.*)','(.*)' as login credentials$")
	public void the_user_provides_invalid_login_credentials(String userName, String password) throws IOException {
		requestCall = given().spec(requestSpecification()).body(getPayloadData.userCredentials(userName, password));
	}
	
	@When("^the user sends a '(.*)' request to the '(.*)' endpoint$")
	public void the_user_sends_a_login_request(String methodType, String resourse) throws IOException {
		APIResources resourceAPI = APIResources.valueOf(resourse);
		if(methodType.equalsIgnoreCase("POST")) {
			responseCall = requestCall.when().post(resourceAPI.getResource()).then().spec(responseSpecification()).extract().response();
		}
		if(methodType.equalsIgnoreCase("DELETE") && resourse.equalsIgnoreCase("deleteProductAPI")) {
			for(int i=0;i<productIds.size();i++) {
				requestCall = given().spec(requestDeleteOrderSpecification(token)).pathParam("productId", productIds.get(i));
				responseCall = requestCall.when().delete(resourceAPI.getResource()).then().spec(responseSpecification()).extract().response();
			}
		}
		if(methodType.equalsIgnoreCase("DELETE") && resourse.equalsIgnoreCase("deleteOrderAPI")) {
			for(int i=0;i<orderIds.size();i++) {
				requestCall = given().spec(requestDeleteOrderSpecification(token)).pathParam("orderId", orderIds.get(i));
				responseCall = requestCall.when().delete(resourceAPI.getResource()).then().spec(responseSpecification()).extract().response();
			}
		}
		if(methodType.equalsIgnoreCase("GET") && resourse.equalsIgnoreCase("orderDetailsAPI")) {
			for(int i=0;i<orderIds.size();i++) {
				requestCall = given().spec(requestDeleteOrderSpecification(token)).queryParam("id", orderIds.get(i));
				responseCall = requestCall.when().get(resourceAPI.getResource()).then().spec(responseSpecification()).extract().response();
			}
		}
	}
	
	@Then("^the response status should be '(.*)'$")
	public void the_response_status_should_be(Integer statusCode) {
		Assert.assertEquals(responseCall.getStatusCode(), statusCode);
	}
	
	@Then("^the response body '(.*)' should be '(.*)'$")
	public void the_response_body_should_be(String key, String value) {
	   Assert.assertEquals(getJsonPath(responseCall,key), value);
	}
	
	@Then("^the response should contain a '(.*)'$")
	public void the_response_should_contain_a_token(String value) {
		if(value.equalsIgnoreCase("token")) {
			LoginResponse loginResponse = responseCall.as(LoginResponse.class);
			this.token = loginResponse.getToken();
			this.userID = loginResponse.getUserId();
		}
		if(value.equalsIgnoreCase("productId")) {
//			ProductResponse productResponse = responseCall.as(ProductResponse.class);
//			this.productId = productResponse.getProductId();	
			this.productIds.add(getJsonPath(responseCall,value));
		}
		if(value.equalsIgnoreCase("orderId")) {
//			this.orderId.add(getJsonPath(responseCall,value));
			OrderResponse orderResponse = responseCall.as(OrderResponse.class);
			this.orderIds = orderResponse.getOrders();
		}
	}
	
	@Given("^the user provides valid product details '(.*)', '(.*)', '(.*)', '(.*)', '(.*)', '(.*)', '(.*)'$")
	public void the_user_provides_valid_product_details(String productName, String productCategory, String productSubCategory, String productPrice, String productDescription, String productFor, String productImage) throws IOException {
		requestCall = given().spec(requestAddProductSpecification(token))
		.param("productName", productName)
		.param("productAddedBy", userID)
		.param("productCategory", productCategory)
		.param("productSubCategory", productSubCategory)
		.param("productPrice", productPrice)
		.param("productDescription", productDescription)
		.param("productFor", productFor)
		.multiPart("productImage", new File(System.getProperty("user.dir")+"//src//test//resources//products//"+productImage+".jpg"));	
	}
	
	@Given("^the user provides a valid '(.*)' for placing the order$")
	public void the_user_provides_a_valid_country_for_placing_the_order(String country) throws IOException {
		requestCall = given().spec(requestCreateOrderSpecification(token)).body(getPayloadData.placeOrder(country, productIds));	
	}
	
	@Given("^the user provides a valid '(.*)' for a product that does not belong to them$")
	public void the_user_provides_a_valid_productId_for_a_product_that_does_not_belong_to_them(String productId) {
		this.productIds.clear();
		this.productIds.add(productId);
	}
	
	@And("^the order details should include the email '(.*)'$")
	public void the_order_details_should_include_the_email(String email) {
		OrderDetailsResponse orderDetailsResponse = responseCall.as(OrderDetailsResponse.class);
		Assert.assertEquals(email, orderDetailsResponse.getData().getOrderBy());
	}
	

	
	
}
