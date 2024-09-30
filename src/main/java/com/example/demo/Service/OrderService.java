package com.example.demo.Service;

import com.example.demo.Entities.OrderedList;
import com.example.demo.Entities.Orders;
import com.example.demo.Entities.Product;
import com.example.demo.Entities.User;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public interface OrderService {
    public ResponseEntity<List<Orders>> findAllOrder();

    public int addOrder(Orders order);
    public int updateOrder(Orders order);
    public int deleteOrder(int id);
    public  ResponseEntity<List<Orders>> findOrdersByUser(int id);
    public ResponseEntity<Orders> saveOrder(Orders order, ArrayList<Product>listOfProductsIds, String creditCardId, User customer, ArrayList<OrderedList> orderedLists);
    public ResponseEntity<Orders> findOrderByIdAndUserId(int id,int userId);
    public ResponseEntity<Orders> findBYOrderId(Long id);

}
