package api.endpoints;

/*
 *Base URL: https://petstore.swagger.io/v2/user
 * Create User: https://petstore.swagger.io/v2/user
 * Update User: https://petstore.swagger.io/v2/user/{username}
 * Get User: https://petstore.swagger.io/v2/user/{username}
 * Delete User: https://petstore.swagger.io/v2/user/{username} 
 */

public class Routes {
	
	public static String base_url="https://petstore.swagger.io/v2";
	
//	User module
	
	public static String post_url="https://petstore.swagger.io/v2/user";
	public static String get_url="https://petstore.swagger.io/v2/user/{username}";
	public static String update_url="https://petstore.swagger.io/v2/user/{username}";
	public static String delete_url="https://petstore.swagger.io/v2/user/{username}";

// Store module

	public static String store_inventory_url = base_url + "/store/inventory";
	public static String store_post_order_url = base_url + "/store/order";
	public static String store_get_order_url = base_url + "/store/order/{orderId}";
	public static String store_delete_order_url = base_url + "/store/order/{orderId}";

// Pet module
	
		//	Here you can create Store module URLs
}
