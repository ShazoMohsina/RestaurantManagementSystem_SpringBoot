package com.restaurant.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.dtos.CategoryDto;
import com.restaurant.dtos.ProductDto;
import com.restaurant.dtos.ReservationDto;
import com.restaurant.services.admin.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	private final AdminService adminService;

	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}
	
	@PostMapping("/category")
	public ResponseEntity<CategoryDto> postCategory(@ModelAttribute CategoryDto categoryDto) throws IOException{
		
		CategoryDto createdCategoryDto = adminService.postCategory(categoryDto);
		if(createdCategoryDto == null) {
			return ResponseEntity.notFound().build();
		} 
		
		return ResponseEntity.ok(createdCategoryDto);
	}
	
	@GetMapping("/categories")
	public ResponseEntity<List<CategoryDto>> getAllCategories(){
		List<CategoryDto> categoryDtoList = adminService.getAllCategories();
		if(categoryDtoList == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(categoryDtoList);
	}
	
	@GetMapping("/categories/{title}")
	public ResponseEntity<List<CategoryDto>> getAllCategoriesByTitle(@PathVariable String title){
		List<CategoryDto> categoryDtoList = adminService.getAllCategoriesByTitle(title);
		if(categoryDtoList == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(categoryDtoList);
	}
	
	//Product Operations
	
	@PostMapping("/{categoryId}/product")
	public ResponseEntity<?> postProduct(@ModelAttribute ProductDto productDto,
												   @PathVariable Long categoryId) throws IOException{
		
		ProductDto createdProductDto = adminService.postProduct(categoryId, productDto);
		if(createdProductDto == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
		} 
		
		return ResponseEntity.status(HttpStatus.CREATED).body(createdProductDto);
	}
	
	@GetMapping("/{categoryId}/products")
	public ResponseEntity<List<ProductDto>> getAllProductsByCategory(@PathVariable Long categoryId){
		List<ProductDto> productDtoList = adminService.getAllProductsByCategory(categoryId);
		if(productDtoList == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(productDtoList);
	}
	
	@GetMapping("/{categoryId}/products/{title}")
	public ResponseEntity<List<ProductDto>> getProductsByCategoryAndTitle(@PathVariable Long categoryId, @PathVariable String title){
		List<ProductDto> productDtoList = adminService.getProductsByCategoryAndTitle(categoryId, title);
		if(productDtoList == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(productDtoList);
	}
	
	@DeleteMapping("/product/{productId}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long productId){
		adminService.deleteProduct(productId);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/product/{productId}")
	public ResponseEntity<ProductDto> getProductById(@PathVariable Long productId){
		ProductDto productDto = adminService.getProductById(productId);
		if(productDto == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(productDto);
	}
	
	@PutMapping("/product/{productId}")
	public ResponseEntity<?> updateProduct(@ModelAttribute ProductDto productDto,
			   @PathVariable Long productId) throws IOException{

		ProductDto updatedProductDto = adminService.updateProduct(productId, productDto);
		if(updatedProductDto == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
		} 
		
		return ResponseEntity.status(HttpStatus.OK).body(updatedProductDto);
	}
	
	@GetMapping("/reservations")
	public ResponseEntity<List<ReservationDto>> getReservations(){
		List<ReservationDto> reservationDtoList = adminService.getReservations();
		if(reservationDtoList == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(reservationDtoList);
	}
	
	@GetMapping("/reservation/{reservationId}/{status}")
	public ResponseEntity<ReservationDto> changeReservationStatus(@PathVariable Long reservationId,
																  @PathVariable String status){

		ReservationDto updatedReservationDto = adminService.changeReservationStatus(reservationId, status);
		if(updatedReservationDto == null) {
			return ResponseEntity.notFound().build();
		} 
		
		return ResponseEntity.status(HttpStatus.OK).body(updatedReservationDto);
	}
}
