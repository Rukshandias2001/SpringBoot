package com.example.demo.Service;

import com.example.demo.DTO.CustomerDtoRequest;
import com.example.demo.DTO.DashboardDTORequest;
import com.example.demo.DTO.PieChartDTO;
import com.example.demo.Entities.*;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public interface ShoppingManagerService {

    public ResponseEntity<Product> deleteProducts(int productId);

    public ResponseEntity<Object> addProduct(Product product, MultipartFile file);

    public ResponseEntity<String> updateProduct(Product product, int productId);

    public ResponseEntity<Product> getProducts(int productId);



    public Response getProductsByCategory(int categoryId);

    public ResponseEntity<List<Product>> getElectronics();

    public ResponseEntity<List<Product>> getClothings();

    public ResponseEntity<List<Product>> getProducts();

    ResponseEntity<Product> updateProductElectronicsProduct(Electronics electronics, int id, MultipartFile file);

    public ResponseEntity<Product> updateProductClothings(Clothings clothings, int id, MultipartFile file);

    public ResponseEntity<List<Country>> listOfCountries();

    public ResponseEntity<List<State>> listOfStates(int id);

    public ResponseEntity<List<CustomerDtoRequest>> listOfUsers();

    public ResponseEntity<List<DashboardDTORequest>> getData();

    public ResponseEntity<List<PieChartDTO>> getSoldPercentage();

    public ResponseEntity<List<Orders>> getListOfOrders();

    public ResponseEntity<String> getEmailBYOrderId(int order_id);

    public ResponseEntity<HashMap<String,Double>> getTotalAmountOfElectronicsAndClothings();

    public ResponseEntity<HashMap<String,Double>> getTotalAmountOfElectronics();

    public ResponseEntity<HashMap<String ,Double>> getTotalAmountOfClothing();
}
