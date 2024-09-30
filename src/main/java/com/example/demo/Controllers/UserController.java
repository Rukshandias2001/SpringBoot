package com.example.demo.Controllers;


import com.example.demo.DTO.AuthenticationRequest;
import com.example.demo.Auth.AuthenticationResponse;
import com.example.demo.Auth.AuthenticationService;
import com.example.demo.Auth.RegisterRequest;
import com.example.demo.Entities.Role;
import com.example.demo.Entities.User;
import com.example.demo.Service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/Users")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    AuthenticationService authService;


    @PostMapping("/user/registerByToken")
    public AuthenticationResponse registerByToken(@RequestBody String token){
        return authService.verifyGoogleToken(token);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return userService.getUsers();
    }

    @PostMapping("/user/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return   ResponseEntity.ok().body(authService.register(request)) ;
    }


    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok().body(authService.authenticate(request));

    }


    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        return  userService.saveRole(role);
    }

    @PostMapping("/role/addUser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) {
          userService.addRoleToUser(form.getEmail(), form.getRoleName());
          return ResponseEntity.ok().build();
    }

    @PostMapping("/findByEmail")
    public ResponseEntity<Integer> getAllSelectedProducts(@RequestBody String email) {
        int i = userService.findbyEmail(email);
        return ResponseEntity.ok().body(i);

    }

    @GetMapping("/getRoles/{user_id}")
    public ResponseEntity<ArrayList<String>> getRoles(@PathVariable("user_id") int user_id){
        return userService.getUserRoles(user_id);
    }





}

@Data
class RoleToUserForm{
    private String email;
    private String roleName;
}


