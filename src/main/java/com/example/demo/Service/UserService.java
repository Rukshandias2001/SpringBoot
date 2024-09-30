package com.example.demo.Service;

import com.example.demo.Entities.Role;
import com.example.demo.Entities.SelectedItems;
import com.example.demo.Entities.User;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public interface UserService {
    ResponseEntity<User>  saveUser(User user);

    ResponseEntity<Role> saveRole(Role role);

    void addRoleToUser(String username, String roleName);

    ResponseEntity<User> getUser(String username);

    ResponseEntity<List<User>> getUsers();

    ResponseEntity<SelectedItems> selectedProduct(SelectedItems selectedItems, String email);

    public ResponseEntity<ArrayList<SelectedItems>> getSelectedProducts(User user);

    public ResponseEntity<SelectedItems> deleteSelectedProducts(int id);

    public int findbyEmail(String email);

    public ResponseEntity<ArrayList<String>> getUserRoles(int user_id);




}
