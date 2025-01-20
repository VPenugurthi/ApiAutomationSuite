Feature: PlaceOrder

  Scenario Outline: Successfully logging in with valid credentials
    Given the user provides valid '<UserName>','<Password>' as login credentials
    When the user sends a 'POST' request to the 'userLoginAPI' endpoint
    Then the response status should be '200'
    And the response body 'message' should be 'Login Successfully'
    And the response should contain a 'token'
    
  Examples:
    |   UserName                |  Password         |
    |   smoketester@gmail.com   |  PAssword!!12345  |

  Scenario Outline: Logging in with invalid credentials
    Given the user provides invalid '<UserName>','<Password>' as login credentials
    When the user sends a 'POST' request to the 'userLoginAPI' endpoint
    Then the response status should be '400'
    And the response body 'message' should be 'Incorrect email or password.'
    
  Examples:
    |   UserName                |  Password         |
    |   smoketester@gmail.com   |  PAssword         |
    
  Scenario Outline: Successfully adding a new product
    Given the user provides valid product details '<productName>', '<productCategory>', '<productSubCategory>', '<productPrice>', '<productDescription>', '<productFor>', '<productImage>'
    When the user sends a 'POST' request to the 'addProductAPI' endpoint
    Then the response status should be '201'
    And the response body 'message' should be 'Product Added Successfully'
    And the response should contain a 'productId'

    
  Examples:
    | productName       | productCategory  | productSubCategory | productPrice  | productDescription                                                    | productFor |  productImage  |
    | Zara  Blazer      | Womens Clothing  |      Clothing      | 79            | Stylish and elegant womens blazer for a professional or casual look.  | Women      |  Blazer        |
    | Zara  SweatShirt  | Womens Clothing  |      Clothing      | 120           | Stylish and elegant womens blazer for a professional or casual look.  | Women      |  SweatShirt    |

  Scenario Outline: User places an order with valid product details which is created above
     Given the user provides a valid '<country>' for placing the order
     When the user sends a 'POST' request to the 'createOrderAPI' endpoint
     Then the response status should be '201'
     And the response body 'message' should be 'Order Placed Successfully'
     And the response should contain a 'orderId'
     
     
  Examples:
     | country |
     | India   |
         
  Scenario: User deletes an product with valid product id
    Given the user sends a 'DELETE' request to the 'deleteProductAPI' endpoint
    Then the response status should be '200'
    And the response body 'message' should be 'Product Deleted Successfully'

  Scenario: User attempts to delete a product that does not belong to them
    Given the user provides a valid '<productId>' for a product that does not belong to them
    When the user sends a 'DELETE' request to the 'deleteProductAPI' endpoint
    Then the response status should be '403'
    And the response body 'message' should be 'You are not authorize to delete this product'
    
    Examples: 
    | productId                        |
    | 6581cade9fd99c85e8ee7ff5         |
    
    
  Scenario Outline: User verifies that the order details contain the logged email ID
    Given the user sends a 'GET' request to the 'orderDetailsAPI' endpoint
    And the response body 'message' should be 'Orders fetched for customer Successfully'
    And the order details should include the email '<UserName>'
    
  Examples:
    |   UserName                |  
    |   smoketester@gmail.com   | 
  
  Scenario: User deletes an order with valid order id
    Given the user sends a 'DELETE' request to the 'deleteOrderAPI' endpoint
    Then the response status should be '200'
    And the response body 'message' should be 'Orders Deleted Successfully'
    
  
  
  