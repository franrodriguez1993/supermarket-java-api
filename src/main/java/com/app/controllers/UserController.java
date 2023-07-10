package com.app.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.entities.User;
import com.app.services.user.UserServiceImpl;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/v1/user")
public class UserController extends BaseControllerImpl<User, UserServiceImpl> {

  @Override
  @PostMapping("")
  public ResponseEntity<?> save(User user, BindingResult bindingResult) {
    try {
      if (bindingResult.hasErrors()) {
        String errorMsg = bindingResult.getFieldError().getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMsg);
      }

      return ResponseEntity.status(HttpStatus.OK).body(service.register(user));
    } catch (Exception e) {
      switch (e.getMessage()) {
        case "EMAIL_IN_USE": {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("EMAIL_IN_USE");
        }
        case "DNI_IN_USE": {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("DNI_IN_USE");
        }
        default: {
          return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("SERVER_ERROR");
        }
      }

    }
  }
}
