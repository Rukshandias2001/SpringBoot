package com.example.demo.Repositories;

import com.example.demo.Entities.Orders;
import com.example.demo.Entities.User;
import jakarta.persistence.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface OrderRepository extends JpaRepository<Orders, Long> {

    @Query("SELECT o FROM Orders o WHERE o.orderId = :orderId AND o.user.id = :userId")
    Orders findOrderByOrderIdAndUserId(@Param("orderId") int orderId, @Param("userId") int userId);

    @Query("Select o From Orders o WHERE o.user.id=:userId")
    List<Orders> findOrdersByUserId(int userId);

    @Query("SELECT o,u.email from Orders o JOIN User u ON o.user.id = u.id order by o.orderId  Desc limit  3")
    List<Orders> findOrdersDesc();




}
