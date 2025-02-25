package resourses;

import java.util.ArrayList;
import java.util.List;

import POJO.LoginPage.LoginRequest;
import POJO.LoginPage.OrderDetails;
import POJO.LoginPage.OrderRequest;

public class PayLoad {
	
	public LoginRequest userCredentials(String userName, String password) {
		
		LoginRequest loginrequest = new LoginRequest();
		loginrequest.setUserEmail(userName);
		loginrequest.setUserPassword(password);
		
		return loginrequest;
	}
	
	public OrderRequest placeOrder(String country, ArrayList<String> productId) {
		List<OrderDetails> orderDetailsList = new ArrayList<OrderDetails>();
		for(int i=0;i<productId.size();i++) {
			OrderDetails orderDetails = new OrderDetails();
			orderDetails.setCountry(country);
			orderDetails.setProductOrderedId(productId.get(i));
			orderDetailsList.add(orderDetails);
		}
		OrderRequest orders = new OrderRequest();
		orders.setOrders(orderDetailsList);
		return orders;		
	}

}
