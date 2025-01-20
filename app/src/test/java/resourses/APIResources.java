package resourses;

public enum APIResources {
	
	userLoginAPI("/api/ecom/auth/login"),
	addProductAPI("/api/ecom/product/add-product"),
	createOrderAPI("api/ecom/order/create-order"),
	deleteProductAPI("api/ecom/product/delete-product/{productId}"),
	deleteOrderAPI("api/ecom/order/delete-order/{orderId}"),
	orderDetailsAPI("api/ecom/order/get-orders-details");
	
	private String resource;
	
	APIResources(String resource) {
//		Load the data
		this.resource = resource;	
	}
	
	public String getResource() {
		return resource;
	}
	
	

}
