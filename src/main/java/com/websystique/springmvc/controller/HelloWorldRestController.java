package com.websystique.springmvc.controller;

import com.websystique.springmvc.model.TblUser;
import com.websystique.springmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
 
@RestController
public class HelloWorldRestController {
 
    @Autowired
    UserService userService;  //Service which will do all data retrieval/manipulation work
 
    
    //-------------------Retrieve All Users--------------------------------------------------------
     
    @RequestMapping(value = "/user/", method = RequestMethod.GET)
    public ResponseEntity<List<TblUser>> listAllUsers() {
        List<TblUser> users = userService.findAllUsers();
        if(users.isEmpty()){
            return new ResponseEntity<List<TblUser>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<TblUser>>(users, HttpStatus.OK);
    }
 
 
    
    //-------------------Retrieve Single User--------------------------------------------------------
     
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TblUser> getUser(@PathVariable("id") long id) {
        System.out.println("Fetching User with id " + id);
        TblUser user = userService.findById(id);
        if (user == null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<TblUser>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<TblUser>(user, HttpStatus.OK);
    }
 
     
     
    //-------------------Create a User--------------------------------------------------------
     
    @RequestMapping(value = "/user/", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody TblUser user, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating User " + user.getLoginName());
 
        if (userService.isUserExist(user)) {
            System.out.println("A User with name " + user.getLoginName() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
 
        userService.saveUser(user);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
 
    
     
    //------------------- Update a User --------------------------------------------------------
     
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<TblUser> updateUser(@PathVariable("id") long id, @RequestBody TblUser user) {
        System.out.println("Updating User " + id);

        TblUser currentUser = userService.findById(id);
         
        if (currentUser==null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<TblUser>(HttpStatus.NOT_FOUND);
        }

        currentUser.setLoginName(user.getLoginName());
//        currentUser.(user.getAddress());
        currentUser.setEmail(user.getEmail());
         
        userService.updateUser(currentUser);
        return new ResponseEntity<TblUser>(currentUser, HttpStatus.OK);
    }
 
    
    
    //------------------- Delete a User --------------------------------------------------------
     
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<TblUser> deleteUser(@PathVariable("id") long id) {
        System.out.println("Fetching & Deleting User with id " + id);

        TblUser user = userService.findById(id);
        if (user == null) {
            System.out.println("Unable to delete. User with id " + id + " not found");
            return new ResponseEntity<TblUser>(HttpStatus.NOT_FOUND);
        }
 
        userService.deleteUserById(id);
        return new ResponseEntity<TblUser>(HttpStatus.NO_CONTENT);
    }
 
     
    
    //------------------- Delete All Users --------------------------------------------------------
     
    @RequestMapping(value = "/user/", method = RequestMethod.DELETE)
    public ResponseEntity<TblUser> deleteAllUsers() {
        System.out.println("Deleting All Users");
 
        userService.deleteAllUsers();
        return new ResponseEntity<TblUser>(HttpStatus.NO_CONTENT);
    }
 
}