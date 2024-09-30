package com.example.demo.Repositories;

import com.example.demo.Entities.Clothings;
import com.example.demo.Entities.Electronics;
import com.example.demo.Entities.Orders;
import com.example.demo.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {


//    Boolean updateProductById(int id ,Product product);

    Boolean deleteProductById(int id);


    @Query("select  p From Product p where type(p)= Electronics ")
    List <Product> getAllElectronics();

    @Query("select  p from  Product p  where type(p)=Clothings ")
    List<Product>  getAllClothings();

    @Query("Select SUM(p.quantity) from Product p")
    int getTotalProductQuantity();

    Product getProductById(long id);

    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.ordersList = :ordersList WHERE p.id = :id")
    void updateProductOrdersList(@Param("ordersList") List<Orders> ordersList, @Param("id") Long id);




}
