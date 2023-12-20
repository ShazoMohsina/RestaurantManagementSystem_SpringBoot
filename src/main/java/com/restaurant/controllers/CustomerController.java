package com.restaurant.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.dtos.CategoryDto;
import com.restaurant.dtos.ProductDto;
import com.restaurant.dtos.ReservationDto;
import com.restaurant.services.customer.CustomerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {
	
	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	@GetMapping("/categories")
	public ResponseEntity<List<CategoryDto>> getAllCategories(){
		List<CategoryDto> categoryDtoList = customerService.getAllCategories();
		if(categoryDtoList == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(categoryDtoList);
	}
	
	@GetMapping("/categories/{title}")
	public ResponseEntity<List<CategoryDto>> getCategoriesByName(@PathVariable String title){
		List<CategoryDto> categoryDtoList = customerService.getCategoriesByName(title.trim());
		if(categoryDtoList == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(categoryDtoList);
	}
	
	@GetMapping("/{categoryId}/products")
	public ResponseEntity<List<ProductDto>> getProductsByCategory(@PathVariable Long categoryId){
		List<ProductDto> productDtoList = customerService.getProductsByCategory(categoryId);
		if(productDtoList == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(productDtoList);
	}
	
	@GetMapping("/{categoryId}/products/{productName}")
	public ResponseEntity<List<ProductDto>> getProductsByCategoryAndName(@PathVariable Long categoryId, @PathVariable String productName){
		List<ProductDto> productDtoList = customerService.getProductsByCategoryAndName(categoryId, productName);
		if(productDtoList == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(productDtoList);
	}
	
	@PostMapping("/reservation")
	public ResponseEntity<?> postReservation(@RequestBody ReservationDto reservationDto){
		ReservationDto postedReservationDto = customerService.postReservation(reservationDto);
		
		if(postedReservationDto == null) {
			return new ResponseEntity<>("Something went wrong.", HttpStatus.BAD_REQUEST);
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(postedReservationDto);
	}
	
	@GetMapping("/reservations/{customerId}")
	public ResponseEntity<List<ReservationDto>> getReservationsByUser(@PathVariable Long customerId){
		List<ReservationDto> reservationDtoList = customerService.getReservationsByUser(customerId);
		if(reservationDtoList == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(reservationDtoList);
	}

}
