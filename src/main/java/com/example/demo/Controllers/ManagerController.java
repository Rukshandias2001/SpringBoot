package com.example.demo.Controllers;

import com.example.demo.Entities.*;
import com.example.demo.Service.ShoppingManagerService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/manager")
public class ManagerController {

    @Autowired
    ShoppingManagerService shoppingManagerService;

    @PostMapping("/addProducts/byClothing")
    public ResponseEntity<Object> addProductsBy_Clothing(
            @RequestParam MultipartFile file ,
            @RequestParam String productName,
            @RequestParam String quantity,
            @RequestParam String description,
            @RequestParam String categoryId,
            @RequestParam String type,
            @RequestParam String colour,
            @RequestParam String size,
            @RequestParam String price
    ) {
        Clothings product = new Clothings();
        product.setProductName(productName);
        product.setQuantity(Integer.parseInt(quantity));
        product.setDescription(description);
        product.setCategoryId(Integer.parseInt(categoryId));
        product.setType(type);
        product.setPrice(Double.parseDouble(price));
        product.setSize(size);
        product.setColour(colour);
        return shoppingManagerService.addProduct(product,file);
    }

    @PostMapping("/addProducts/byElectronics")
    public ResponseEntity<Object> addProductsBY_Electronics(
            @RequestParam MultipartFile file,
            @RequestParam String productName,
            @RequestParam String quantity,
            @RequestParam String description,
            @RequestParam String categoryId,
            @RequestParam String type,
            @RequestParam String warrenty,
            @RequestParam String brand,
            @RequestParam String price
    ){

        Electronics electronics = new Electronics();
        electronics.setProductName(productName);
        electronics.setQuantity(Integer.parseInt(quantity));
        electronics.setPrice(Double.parseDouble(price));
        electronics.setDescription(description);
        electronics.setCategoryId(Integer.parseInt(categoryId));
        electronics.setType(type);
        electronics.setWarrenty(warrenty);
        electronics.setBrand(brand);
        return shoppingManagerService.addProduct(electronics,file);

    }



    @PatchMapping("updateProductByElectronics")
    public ResponseEntity<Product> getUpdatedProductByIdElectronics(
            @RequestParam String id,
            @RequestPart(required = false) MultipartFile file,
            @RequestParam String productName,
            @RequestParam String quantity,
            @RequestParam String description,
            @RequestParam String categoryId,
            @RequestParam String type,
            @RequestParam String warrenty,
            @RequestParam String brand,
            @RequestParam String price
    ){
        Electronics electronics = new Electronics();
        int productId = Integer.parseInt(id);
        electronics.setId(productId);
        electronics.setProductName(productName);
        electronics.setQuantity(Integer.parseInt(quantity));
        electronics.setPrice(Double.parseDouble(price));
        electronics.setDescription(description);
        electronics.setCategoryId(Integer.parseInt(categoryId));
        electronics.setType(type);
        electronics.setWarrenty(warrenty);
        electronics.setBrand(brand);
        return shoppingManagerService.updateProductElectronicsProduct(electronics, productId, file);
    }


    @PatchMapping("updateProductByClothings")
    public ResponseEntity<Product> updateProductByClothings(
            @RequestParam String id,
            @RequestPart (required = false) MultipartFile file,
            @RequestParam String productName,
            @RequestParam String quantity,
            @RequestParam String description,
            @RequestParam String categoryId,
            @RequestParam String type,
            @RequestParam String colour,
            @RequestParam String size,
            @RequestParam String price
    ){
        Clothings clothings = new Clothings();
        int productId= Integer.parseInt(id);
        clothings.setId(productId);
        clothings.setProductName(productName);
        clothings.setQuantity(Integer.parseInt(quantity));
        clothings.setPrice(Double.parseDouble(price));
        clothings.setDescription(description);
        clothings.setCategoryId(Integer.parseInt(categoryId));
        clothings.setType(type);
        clothings.setColour(colour);
        clothings.setSize(size);
        clothings.setPrice(Double.parseDouble(price));
        return shoppingManagerService.updateProductClothings(clothings, productId, file);
    }


    @DeleteMapping("deleteProductById/{id}")
    public ResponseEntity<Product> deleteProductById(@PathVariable("id") int id){
        return shoppingManagerService.deleteProducts(id);
    }





}