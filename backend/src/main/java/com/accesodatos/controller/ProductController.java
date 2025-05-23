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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.accesodatos.dto.api.ApiResponseDto;
import com.accesodatos.dto.product.ProductRequestDto;
import com.accesodatos.dto.product.ProductResponseDto;
import com.accesodatos.service.impl.ProductServiceImpl;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {

	private static final String PRODUCT_RESOURCE = "/products";
	private static final String PRODUCT_PATH_ID = PRODUCT_RESOURCE + "/{productId}";
	private static final String PRODUCT_ID_STATE = PRODUCT_PATH_ID +"/state";
	
	@Autowired ProductServiceImpl productServiceImpl;
	
	/**
	 * The ping function returns a "Pong ....producto" message as a ResponseEntity with HTTP status OK.
	 * 
	 * @return The method is returning a ResponseEntity with the body "Pong ....producto" and an HTTP
	 * status of OK (200).
	 */
	@GetMapping(value = PRODUCT_RESOURCE+"/ping", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> ping(){
		return new ResponseEntity<>("Pong ....producto", HttpStatus.OK);
	}
	
	/**
	 * This Java function retrieves all products and returns them in a ResponseEntity with an
	 * ApiResponseDto.
	 * 
	 * @return This method returns a ResponseEntity object containing an ApiResponseDto object with a list
	 * of ProductResponseDto objects. The ApiResponseDto object includes a success message, HTTP status
	 * code, and the list of products fetched from the getAllProducts method in the productServiceImpl.
	 */
	@GetMapping(value = PRODUCT_RESOURCE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<List<ProductResponseDto>>> getAllProducts(){
		List<ProductResponseDto> products = productServiceImpl.getAllProducts();
		
		ApiResponseDto<List<ProductResponseDto>> response = 
				new ApiResponseDto<List<ProductResponseDto>>("All products fetched successfuly",
						HttpStatus.OK.value(), products);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * This Java function creates a new product by accepting a JSON request and returning a JSON response
	 * with the result.
	 * 
	 * @param dto The `dto` parameter in the `createProduct` method is of type `ProductRequestDto`. It is
	 * annotated with `@RequestBody`, indicating that the method expects the request body to be converted
	 * to this `ProductRequestDto` object. This object likely contains the data necessary to create a new
	 * product
	 * @return The method `createProduct` is returning a `ResponseEntity` containing an `ApiResponseDto`
	 * with a message indicating the successful creation of a product, an HTTP status code of 201
	 * (CREATED), and a boolean value indicating whether the product was successfully created.
	 */
	@PostMapping(value = PRODUCT_RESOURCE, 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<Boolean>> createProduct(@RequestBody ProductRequestDto dto) {
		Boolean created = productServiceImpl.createProduct(dto);
		
		ApiResponseDto<Boolean> response = new ApiResponseDto<Boolean>(
				"Product created successfuly.", HttpStatus.CREATED.value(), created);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	/**
	 * This Java function updates a product with the provided ID using the information from the request
	 * body and returns a response indicating the success of the update.
	 * 
	 * @param productId The `productId` is a path variable representing the unique identifier of the
	 * product that needs to be updated. It is extracted from the URL path of the request.
	 * @param dto The `dto` parameter in the `updateProduct` method is of type `ProductRequestDto`. It is
	 * annotated with `@RequestBody`, which means that the data for this parameter will be obtained from
	 * the request body of the HTTP request. This parameter is used to pass the details of the product
	 * that
	 * @return The method is returning a ResponseEntity object containing an ApiResponseDto<Boolean>
	 * object with a message "Product updated successfully", an HTTP status code of 201
	 * (HttpStatus.CREATED.value()), and a boolean value indicating whether the product was updated
	 * successfully.
	 */
	@PutMapping(value = PRODUCT_PATH_ID, 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<Boolean>> updateProduct(@PathVariable Long productId,
			@RequestBody ProductRequestDto dto) {
		Boolean updated = productServiceImpl.updateProduct(dto, productId);
		
		ApiResponseDto<Boolean> response = new ApiResponseDto<Boolean>(
				"Product updated successfuly.", HttpStatus.CREATED.value(), updated);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	/**
	 * This Java function handles the deletion of a product by its ID and returns a response indicating
	 * whether the deletion was successful.
	 * 
	 * @param productId The `productId` parameter in the `deleteProduct` method is a `Long` type variable
	 * representing the unique identifier of the product that needs to be deleted from the system.
	 * @return The method is returning a `ResponseEntity` containing an `ApiResponseDto` with a message
	 * indicating whether the product was deleted successfully, the HTTP status code, and a boolean value
	 * indicating if the product was deleted.
	 */
	@DeleteMapping(value = PRODUCT_PATH_ID, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<Boolean>> deleteProduct(@PathVariable Long productId) {
		Boolean deleted = productServiceImpl.deleteProduct(productId);
		
		ApiResponseDto<Boolean> response = new ApiResponseDto<Boolean>(
				"Product deleted successfuly.", HttpStatus.OK.value(), deleted);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * This function manages the state of a product identified by its ID and returns a response indicating
	 * the success of the state update.
	 * 
	 * @param productId The `productId` parameter in the `manageProductSate` method is a path variable of
	 * type `long`. It represents the unique identifier of the product whose state is being managed.
	 * @param value The `value` parameter in the `@PutMapping` annotation represents the endpoint path
	 * where this method will be accessible. In this case, it is defined as `PRODUCT_ID_STATE`, which is
	 * likely a constant or variable holding the endpoint path value.
	 * @return The method `manageProductSate` is returning a `ResponseEntity` object containing an
	 * `ApiResponseDto` with a message, HTTP status code, and a boolean value indicating whether the
	 * product state was successfully updated.
	 */
	@PutMapping(value = PRODUCT_ID_STATE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<Boolean>> manageProductSate(@PathVariable long productId, @RequestParam String value) {
		Boolean changed = productServiceImpl.manageProductSate(productId, value);
		ApiResponseDto<Boolean> response = new ApiResponseDto<Boolean>(
				"Product state updated successfuly.", HttpStatus.OK.value(), changed);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
