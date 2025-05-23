package com.accesodatos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accesodatos.dto.api.ApiResponseDto;
import com.accesodatos.dto.cartitem.CartItemRequestDto;
import com.accesodatos.dto.cartitem.CartItemResponseDto;
import com.accesodatos.service.impl.CartItemServiceImpl;

@RestController
@RequestMapping("api/v1")
@CrossOrigin(origins = "http://localhost:5173")
public class CartItemController {

	private static final String CARTITEM_RESOURCE = "/cartItems";
	private static final String CARTITEM_USER_ID= CARTITEM_RESOURCE +"/{userId}";
	private static final String CARTITEM_PRODUCT_ID = CARTITEM_RESOURCE + "/product";
	private static final String CARTITEM_USER_ID_PRODUCT_ID = CARTITEM_RESOURCE + "/{userId}/product/{productId}";
	
	@Autowired CartItemServiceImpl cartItemServiceImpl;
	
	/**
	 * This Java function retrieves all cart items and returns them in a ResponseEntity with an
	 * ApiResponseDto.
	 * 
	 * @return The method `getAllCartItems()` is returning a ResponseEntity containing an `ApiResponseDto`
	 * with a message indicating that all items were fetched successfully, an HTTP status code of 200
	 * (OK), and a list of `CartItemResponseDto` objects.
	 */
	@GetMapping(value = CARTITEM_RESOURCE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<List<CartItemResponseDto>>> getAllCartItems() {
		List<CartItemResponseDto> items = cartItemServiceImpl.getAllCartItems();
		
		ApiResponseDto<List<CartItemResponseDto>> response = new ApiResponseDto<List<CartItemResponseDto>>("All items were fetched successfuly", HttpStatus.OK.value(), items);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * This Java function retrieves all cart items for a specific user ID and returns them in a JSON
	 * response.
	 * 
	 * @param userId The `userId` parameter in the `getAllCartItemsByUserId` method represents the unique
	 * identifier of the user for whom you want to retrieve all cart items. This method fetches all cart
	 * items associated with the specified user ID and returns them in the response.
	 * @return This method returns a ResponseEntity object containing an ApiResponseDto with a list of
	 * CartItemResponseDto objects. The ApiResponseDto includes a message indicating that all user items
	 * were fetched successfully, an HTTP status code of 200 (OK), and the list of cart items retrieved
	 * for the specified user ID.
	 */
	@GetMapping(value = CARTITEM_USER_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<List<CartItemResponseDto>>> getAllCartItemsByUserId(@PathVariable Long userId) {
		List<CartItemResponseDto> items = cartItemServiceImpl.getAllCartItemsByUserId(userId);
		
		ApiResponseDto<List<CartItemResponseDto>> response = new ApiResponseDto<List<CartItemResponseDto>>("All user items were fetched successfuly", HttpStatus.OK.value(), items);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	
	/**
	 * This Java function adds a product to a cart and returns a response with the added item details.
	 * 
	 * @param dto The `dto` parameter in the `addProductToCart` method is of type `CartItemRequestDto`. It
	 * is annotated with `@RequestBody`, indicating that the method expects the request body to be
	 * converted to this object. This object likely contains information about the product that is being
	 * added to the cart
	 * @return The method is returning a `ResponseEntity` object containing an `ApiResponseDto` with a
	 * message indicating that the item was added successfully, an HTTP status code of 200 (OK), and the
	 * `CartItemResponseDto` object representing the added item.
	 */
	@PostMapping(value = CARTITEM_PRODUCT_ID, 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<CartItemResponseDto>> addProductToCart(
			@RequestBody CartItemRequestDto dto) {
		
		CartItemResponseDto items = cartItemServiceImpl.addproductToCart(dto);
		
		ApiResponseDto<CartItemResponseDto> response = new ApiResponseDto<CartItemResponseDto>("Item added successfuly", HttpStatus.OK.value(), items);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * This Java function updates a cart item and returns a response with the updated item details.
	 * 
	 * @param dto The `dto` parameter in the `updateCartItem` method is of type `CartItemRequestDto`. It
	 * is used to pass the data of the cart item that needs to be updated. This data is consumed in JSON
	 * format and contains information such as the product ID, quantity, and any other details related
	 * @return The method `updateCartItem` is returning a `ResponseEntity` object containing an
	 * `ApiResponseDto` with a message "Item added successfully", an HTTP status code of 200 (OK), and the
	 * updated `CartItemResponseDto` object.
	 */
	@PutMapping(value = CARTITEM_PRODUCT_ID, 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<CartItemResponseDto>> updateCartItem(@RequestBody CartItemRequestDto dto) {
		CartItemResponseDto updated = cartItemServiceImpl.updateCartItem(dto);
		
		ApiResponseDto<CartItemResponseDto> response = new ApiResponseDto<CartItemResponseDto>("Item added successfuly", HttpStatus.OK.value(), updated);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
/**
 * This Java function handles the deletion of a cart item for a specific user and product.
 * 
 * @param userId The `userId` parameter in the `deleteCartItem` method represents the unique identifier
 * of the user whose cart item is being deleted.
 * @param productId The `productId` parameter in the `deleteCartItem` method represents the unique
 * identifier of the product that is associated with the cart item to be deleted.
 * @return The method is returning a ResponseEntity object containing an ApiResponseDto<Boolean> object
 * with a message indicating that the item/s were deleted successfully, along with an HTTP status code
 * of 200 (OK) and a boolean value indicating whether the deletion was successful or not.
 */
	@DeleteMapping(value = CARTITEM_USER_ID_PRODUCT_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<Boolean>> deleteCartItem(@PathVariable Long userId, @PathVariable Long productId) {
		Boolean updated = cartItemServiceImpl.deleteCartItem(userId, productId);
		
		ApiResponseDto<Boolean> response = new ApiResponseDto<Boolean>("Item/s deleted successfuly", HttpStatus.OK.value(), updated);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
