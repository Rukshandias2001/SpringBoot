package com.example.demo.Service;

import com.example.demo.Entities.OrderedList;
import com.example.demo.Entities.Role;
import com.example.demo.Entities.SelectedItems;
import com.example.demo.Entities.User;
import com.example.demo.Repositories.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.servlet.Servlet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Transactional
@Slf4j
@Qualifier
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private OrderListRepository orderListRepository;
    @Autowired
    private SelectedProductRepository selectedProductRepository;


    @PersistenceContext
    EntityManager entityManager;


//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user =userRepository.findByUserName(username);
//        if(user==null){
//            log.error("User not found");
//            throw new UsernameNotFoundException("User not found");
//        }else{
//            log.info("User found");
//        }
//        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        user.getRoles().forEach(role -> {
//            authorities.add(new SimpleGrantedAuthority(role.getName()));
//
//        });
//        return new org.springframework.security.core.userdetails.User(user.getUserName(),user.getPassword(),authorities);
//    }

    @Override
    public ResponseEntity<User> saveUser(User user) {
        log.info("Saving new User to the Database",user.getUsername());
//        URI uri  = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/user/save").toString());
        return ResponseEntity.ok().body(userRepository.save(user));
    }

    @Override
    public ResponseEntity<Role> saveRole(Role role) {
        log.info("Saving {Role} new Role to the Database",role.getName());
//        URI uri  = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/role/save").toString());
        return ResponseEntity.ok().body(roleRepository.save(role));
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        log.info("Adding new Role {} to the User {} and save user in the database",roleName,email);
        User user = userRepository.findByEmail(email).get();
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
        role.getListOfUsers().add(user);

    }

    @Override
    public ResponseEntity<User> getUser(String username) {
        log.info("Fetching new User to the Database");
        return ResponseEntity.ok().body(userRepository.findByUserName(username));
    }

    @Override
    public ResponseEntity<List<User>> getUsers() {
        log.info("Fetching all the Users from the Database");
        return ResponseEntity.ok().body(userRepository.findAll());
    }


    public int findbyEmail(String email){
        User user1 = userRepository.findByEmail(email).get();
        return user1.getId();
    }


    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public ResponseEntity<SelectedItems> selectedProduct(SelectedItems selectedItems, String email) {
        try {
            int id = findbyEmail(email);
            User user = userRepository.findById((long) id).orElseThrow(() -> new RuntimeException("User not found"));

            // Ensure the selectedItems has the user set correctly
            selectedItems.setUser(user);

            SelectedItems existingItem = selectedProductRepository.findByProductIdAndUserId(selectedItems.getProductId(), id);

            if (existingItem != null) {
                // Update the existing item
                existingItem.setQuantity(existingItem.getQuantity()+selectedItems.getQuantity());
                selectedItems = entityManager.merge(existingItem);
            } else {
                // Save the new item as a separate entry
                selectedItems = entityManager.merge(selectedItems);
            }

            // Add the item to the user's selected items list
            if (user.getSelectedItemsList() == null) {
                user.setSelectedItemsList(new ArrayList<>());
            }

            // Avoid duplicating the item in the user's list
            if (!user.getSelectedItemsList().contains(selectedItems)) {
                user.getSelectedItemsList().add(selectedItems);
            }

            log.info("Selecting the product from the Database");
            return ResponseEntity.ok().body(selectedItems);

        } catch (Exception e) {
            log.error("Error selecting product: {}", e.getMessage());
            return ResponseEntity.ok().body(null);
        }
    }

    @Override
    public ResponseEntity<ArrayList<SelectedItems>> getSelectedProducts( User user) {
        int userId = findbyEmail(user.getEmail());
        ArrayList<SelectedItems> listOfSelectedItems = (ArrayList<SelectedItems>) selectedProductRepository.findAllByUserId(userId);
        if(listOfSelectedItems == null){
            listOfSelectedItems = new ArrayList<>();
            return ResponseEntity.ok().body(listOfSelectedItems);
        }
        return ResponseEntity.ok().body(listOfSelectedItems);
    }

    public ResponseEntity<SelectedItems> deleteSelectedProducts(int id) {
        Optional<SelectedItems> byId = selectedProductRepository.findById(id);
        if(byId.isPresent()){
            SelectedItems byIdAndUserId =  byId.get();
            if(byIdAndUserId.getId()>0){
                selectedProductRepository.deleteById(id);
                return ResponseEntity.ok().body(byIdAndUserId);
            }else{
                return ResponseEntity.ok().body(null);
            }

        }
        return ResponseEntity.ok().body(null);
    }

    @Override
    public ResponseEntity<ArrayList<String>> getUserRoles(int user_id) {

        String sql = "select r.name from user_role u Inner join `role` r ON u.role_id = r.role_id where u.user_id = " + user_id;

        // Execute the query and retrieve the result list
        Query nativeQuery = entityManager.createNativeQuery(sql);
        List<Object> resultList = nativeQuery.getResultList(); // Return as List<Object>

        // Convert the result list to an ArrayList of Strings
        ArrayList<String> listOfRoles = new ArrayList<>();
        for (Object roleName : resultList) {
            listOfRoles.add((String) roleName); // Cast each result to a String and add to the list
        }

        // Return the list of roles as a ResponseEntity
        return ResponseEntity.ok().body(listOfRoles);

    }






}
