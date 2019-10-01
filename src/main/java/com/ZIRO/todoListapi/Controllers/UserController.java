package com.ZIRO.todoListapi.Controllers;

import com.ZIRO.todoListapi.Entities.User;
import com.ZIRO.todoListapi.Repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private HttpStatus httpStatus;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/all")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = new ArrayList<>();
        try{
            users = userRepository.findAll();
            httpStatus = HttpStatus.OK;
        }catch(Exception e){
            LOGGER.info("Error in getting all users, please see below details");
            httpStatus = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(users, httpStatus);
    }

    @RequestMapping(value = "/current", method = RequestMethod.POST)
    public ResponseEntity<User> getOne(@RequestBody Map<String, String> mapObj){
        User user = new User();
        try{
            long id = Long.parseLong(mapObj.get("user_id"));
            user = userRepository.findOneByUserId(id);
            httpStatus = HttpStatus.OK;
        }catch(Exception e){
            LOGGER.info("Error in getting single user, please see below details");
            httpStatus = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(user, httpStatus);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> updateUser(@RequestBody User user){
        Map<String, Object> results = new HashMap<>();
        try{
            User dbUser = userRepository.findOneByUserId(user.getUserId());
            if(dbUser != null){
                dbUser.setFirstName(user.getFirstName());
                dbUser.setLastName(user.getLastName());
                dbUser.setEmail(user.getEmail());
                dbUser.setPhone(user.getPhone());
                userRepository.save(dbUser);
            }else{
                userRepository.save(user);
            }
            results.put("Success", "Yes");
            httpStatus = HttpStatus.OK;
        }catch(Exception E){
            LOGGER.info("Error in adding User, please see below details");
            results.put("Success", "No");
            results.put("error", E.getMessage());
            httpStatus = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(results, httpStatus);
    }
}
