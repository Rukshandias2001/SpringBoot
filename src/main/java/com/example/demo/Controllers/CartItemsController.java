package com.example.demo.Controllers;

import com.example.demo.DTO.OrderRequest;
import com.example.demo.Entities.*;
import com.example.demo.Service.OrderService;
import com.example.demo.Service.ShoppingManagerService;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Struct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/Cart")
public class CartItemsController {

    @Autowired
    ShoppingManagerService shoppingManagerService;

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;




    @GetMapping("/getProducts")
    public ResponseEntity<List<Product>> getProducts() {
        return shoppingManagerService.getProducts();
    }

    @GetMapping("/getProductBYCategory/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable("category")String category){
        if(category.equals("Clothings")){
            return  shoppingManagerService.getClothings();
        }else if(category.equals("Electronics")){
            return shoppingManagerService.getElectronics();
        }else{
            return shoppingManagerService.getProducts();
        }

    }

    @GetMapping("/getProductById/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") int id){
        return shoppingManagerService.getProducts(id);

    }


    @GetMapping("getCountries")
    public ResponseEntity<List<Country>> getCountries(){
        return shoppingManagerService.listOfCountries();
    }

    @GetMapping("getCities/{id}")
    public ResponseEntity<List<State>> getCities(@PathVariable("id") int id){
        return shoppingManagerService.listOfStates(id);
    }


    @PostMapping("saveOrder")
    public ResponseEntity saveOrder(@RequestBody  OrderRequest orderRequest) throws ParseException {

        Orders order = new Orders();
        order.setDate(orderRequest.getExpiryDate());
        order.setPrice(orderRequest.getPrice());
        order.setCvv(orderRequest.getCvv());
        order.setCardType(orderRequest.getCardType());
        order.setPaidDate(orderRequest.getDate());
        // Create a SimpleDateFormat for the desired format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // Get today's date and format it to "yyyy-MM-dd" format
        String formattedDate = dateFormat.format(new Date());
        // Parse the formatted date string back into a Date object
        Date parsedDate = dateFormat.parse(formattedDate);
        order.setDate(parsedDate);



        CreditCards creditCards = new CreditCards();
        creditCards.setExpiryDate(String.valueOf(orderRequest.getExpiryDate()));
        creditCards.setName(orderRequest.getNameOfCard());
        creditCards.setCardNumber(orderRequest.getCreditCardNumber());

        User user = new User();
        user.setUserName(orderRequest.getUserName());
        user.setLastName(orderRequest.getLastName());
        user.setEmail(orderRequest.getEmail());
        return orderService.saveOrder(order, (ArrayList<Product>) orderRequest.getListOfProducts(),orderRequest.getCreditCardNumber(),user, (ArrayList<OrderedList>) orderRequest.getOrderList());

    }

    @GetMapping("/createInvoice/{id}")
    public ResponseEntity<Orders> genarateReciept(@PathVariable("id") int id,@RequestParam("userId") int userId){
        return orderService.findOrderByIdAndUserId(id,userId);
    }



    @PostMapping("/selectedList")
    public ResponseEntity<SelectedItems> saveFavourites(@RequestBody SelectedItems selectedItems){
        return userService.selectedProduct(selectedItems,selectedItems.getEmail());
    }



    @GetMapping("/getSelectedList")
    public ResponseEntity<ArrayList<SelectedItems>> getFavourites( @RequestParam("name") String userName,
                                                                   @RequestParam("email") String userEmail){
        User user = new User();
        user.setEmail(userEmail);
        user.setUserName(userName);
        return userService.getSelectedProducts(user);
    }

    @DeleteMapping("/deleteSelectedList/{id}")
    public  ResponseEntity<SelectedItems> deleteSelectedItems(@PathVariable("id") String productId){
        int id = Integer.parseInt(productId);
        return userService.deleteSelectedProducts(id);
    }

    @GetMapping("/getOrdersBYUserId/{id}")
    public ResponseEntity<List<Orders>> getOrders(@PathVariable("id") int userId){
        return  orderService.findOrdersByUser(userId);

    }

    @GetMapping("/getOrderById/{id}")
    public ResponseEntity<Orders>gedOrderReciept(@PathVariable("id")String id){
        Long orderId = Long.parseLong(id);
        return orderService.findBYOrderId(orderId);
    }









}
