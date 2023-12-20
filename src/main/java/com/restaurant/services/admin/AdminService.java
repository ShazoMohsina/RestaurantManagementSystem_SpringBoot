package com.restaurant.services.admin;

import java.io.IOException;
import java.util.List;

import com.restaurant.dtos.CategoryDto;
import com.restaurant.dtos.ProductDto;
import com.restaurant.dtos.ReservationDto;

public interface AdminService {

	CategoryDto postCategory(CategoryDto categoryDto) throws IOException;

	List<CategoryDto> getAllCategories();

	List<CategoryDto> getAllCategoriesByTitle(String title);

	ProductDto postProduct(Long categoryId, ProductDto productDto) throws IOException;

	List<ProductDto> getAllProductsByCategory(Long categoryId);

	List<ProductDto> getProductsByCategoryAndTitle(Long categoryId,  String title);

	void deleteProduct(Long productId);

	ProductDto getProductById(Long productId);

	ProductDto updateProduct(Long productId, ProductDto productDto) throws IOException;

	List<ReservationDto> getReservations();

	ReservationDto changeReservationStatus(Long reservationId, String status);

}
