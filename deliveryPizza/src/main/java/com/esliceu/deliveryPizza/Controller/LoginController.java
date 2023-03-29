package com.esliceu.deliveryPizza.Controller;

import com.esliceu.deliveryPizza.DTO.LoginForm;
import com.esliceu.deliveryPizza.DTO.RegisterForm;
import com.esliceu.deliveryPizza.DTO.UserDto;
import com.esliceu.deliveryPizza.Filter.JwtTokenUtil;
import com.esliceu.deliveryPizza.Model.Person;
import com.esliceu.deliveryPizza.Service.UserService;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    public LoginController(UserService userService, JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping(value = "/register" )
    public Map<String, String>  saveEmployee(@RequestBody RegisterForm registerForm) {
        // System.out.printf("*****************++Obtener datos registro form" + registerForm);
        // System.out.printf("************** " +  registerForm.getAvatar().getClass()  );
        //@RequestParam MultipartFile avatar,

        // System.out.printf("**********ARCHIVO: " + registerForm );
        //LO DE ARRIBA ERA PARA GUARDAR IMAGEN IMPORTANTE
        userService.newUser(registerForm);
        Map<String, String> resp = new HashMap<>();
        resp.put("message", "Nuevo usuario registrado");
        return resp;
    }
    /*
    @ModelAttribute  RegisterForm registerForm
     @PostMapping("/register")
     public Map<String, String> doRegister(@ModelAttribute RegisterForm registerForm ) {
        System.out.printf("*****************++Obtener datos registro form" + registerForm.getName());

        userService.newUser(registerForm);

        Map<String, String> resp = new HashMap<>();
        resp.put("message", "done");
        return resp;
    }*/

    //probar login Funciona
    // el login debe de ser 3 vecez si fallas
    // validar strings
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginForm loginForm) {
        System.out.printf("*****************++Obtener datos registro form" + loginForm);
        Map<String, Object> resp = new HashMap<>();
        Person user = userService.authenticate(loginForm);
        resp.put("token", jwtTokenUtil.generateAccessToken(user));
        resp.put("user", new UserDto(user));

        System.out.printf(String.valueOf(resp));
        System.out.printf("*************");
        return resp;
    }



}
