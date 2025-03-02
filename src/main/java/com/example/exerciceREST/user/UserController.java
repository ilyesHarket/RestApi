package com.example.exerciceREST.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")

public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public ResponseEntity<Object> CreateUser(@RequestBody User user){
        return this.userService.newUser(user);
    }

    @GetMapping("/")
    public List<User> getAllUsers(){
        return this.userService.getUsers();
    }

    //get user by id
    @GetMapping("/{id}") // ? what is this id
    public ResponseEntity<Object> getUserById(@PathVariable Integer id){
        return this.userService.getUserById(id);
    }

    //assign product to user
    @PostMapping("/{userId}/products/{productId}")
    public ResponseEntity<User> assignProductToUser(@PathVariable Integer userId, @PathVariable Integer productId) {
        User updatedUser = userService.assignProduct(userId, productId);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
}
