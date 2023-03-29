package com.esliceu.deliveryPizza.Service;


import com.esliceu.deliveryPizza.DTO.LoginForm;
import com.esliceu.deliveryPizza.DTO.RegisterForm;
import com.esliceu.deliveryPizza.Model.Person;
import com.esliceu.deliveryPizza.Model.Role;
import com.esliceu.deliveryPizza.Repository.PersonRepo;
import com.esliceu.deliveryPizza.Repository.RoleRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    PersonRepo personRepo;
    RoleRepo roleRepo;

    PasswordEncoder passwordEncoder;
    AuthenticationManager authenticationManager;
    @Value("${base.url.images}")
    private String baseUrlImages;


    public UserService(PersonRepo personRepo, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,
                       RoleRepo roleRepo) {
        this.personRepo = personRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.roleRepo = roleRepo;

    }

    //GUARDAR EL AVATAR EN SETTINGS SE DEBE DE CREAR UNO POR DEFEFCTO  TIPO LA PRIMERA LETRA DEL NOMBRE
    //DE USUARIO CON UN COLOR DE FONDO
    public void newUser(RegisterForm registerForm) {
        Person user = new Person();
        user.setEmail(registerForm.getEmail());
        user.setName(registerForm.getName());
        user.setSurname(registerForm.getSurname());
        user.setBirthday(registerForm.getBirthDate());
        user.setPhone(registerForm.getPhone());
        user.setPassword(passwordEncoder.encode(registerForm.getPassword()));
        Role role = roleRepo.findRoleByName("admin");
        user.setRole(role);
        personRepo.save(user);
    }
    /*
    private Avatar createAvatar(String b64avatar) {
        String[] avatarParts = b64avatar.split(",");
        String avatarCode = UUID.randomUUID().toString().replaceAll("-", "");
        Avatar avatar = avatarRepo.findAvatarByCode(avatarCode);
        while (avatar != null){
            avatarCode = UUID.randomUUID().toString().replaceAll("-", "");
            avatar = avatarRepo.findAvatarByCode(avatarCode);
        }
        avatar = new Avatar();
        avatar.setCode(avatarCode);
        avatar.setPrefix(avatarParts[0]);
        avatar.setContent(Base64.getDecoder().decode(avatarParts[1]));
        avatar.setUrl(baseUrlImages + avatar.getCode());
        avatarRepo.save(avatar);
        return avatar;
    }

     */

    public Person authenticate(LoginForm loginForm) {
        Authentication authenticate = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginForm.getEmail(), loginForm.getPassword()));
        return (Person) authenticate.getPrincipal();
    }
}

