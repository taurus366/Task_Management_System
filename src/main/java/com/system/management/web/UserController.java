package com.system.management.web;

import com.system.management.model.binding.LoginUserBindingModel;
import com.system.management.model.binding.RegisterNewUserBindingModel;
import com.system.management.model.dto.UserInformationDTO;
import com.system.management.model.service.LoginUserServiceModel;
import com.system.management.model.service.RegisterNewUserServiceModel;
import com.system.management.model.user.CurrentUser;
import com.system.management.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UserController {

    private final AccountService accountService;
    private final ModelMapper modelMapper;
    private final CurrentUser currentUser;

    public UserController(AccountService accountService, ModelMapper modelMapper, CurrentUser currentUser) {
        this.accountService = accountService;
        this.modelMapper = modelMapper;
        this.currentUser = currentUser;
    }

    //    @PreAuthorize("!isLogged()")
    @PostMapping("/register")
    public ResponseEntity<Object> registerNewUser(@Valid @RequestBody RegisterNewUserBindingModel registerNewUserBindingModel
            , BindingResult bindingResult, RedirectAttributes redirectAttributes) {


        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("registerNewUserBindingModel", registerNewUserBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.registerNewUserBindingModel", bindingResult);

            return ResponseEntity.status(HttpStatus.CONFLICT).body(bindingResult.getAllErrors());
        }

        RegisterNewUserServiceModel mappedAccount = modelMapper.map(registerNewUserBindingModel, RegisterNewUserServiceModel.class);

        UserInformationDTO userInformationDTO = accountService
                .registerThenLogin(mappedAccount);

        return ResponseEntity.ok(userInformationDTO);
    }

    //    @PreAuthorize("!isLogged()")
    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@Valid @RequestBody LoginUserBindingModel loginUserBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("loginUserBindingModel", loginUserBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.loginUserBindingModel", bindingResult);

            return ResponseEntity.status(HttpStatus.CONFLICT).body(bindingResult.getAllErrors());
        }
        UserInformationDTO login = null;
        System.out.println(loginUserBindingModel.getEmail());
        try {
            login = accountService
                    .login(new LoginUserServiceModel().setEmail(loginUserBindingModel.getEmail()));
        } catch (UsernameNotFoundException ex) {

            String[] codes = new String[]{ex.getMessage()};
            String[] arguments = new String[]{""};
            bindingResult.addError(new FieldError("email", "email", loginUserBindingModel.getEmail(), false, codes, arguments, ex.getMessage()));
//            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(bindingResult.getAllErrors());
        }

        return ResponseEntity.ok(login);
    }

    //    @PreAuthorize("isLogged()")
    @PostMapping("/logout")
    public ResponseEntity<Object> logout() {
        accountService
                .logout();
        return ResponseEntity.ok().build();
    }

    //    IF USER RELOAD THE ANGULAR FRONT END PAGE , HERE I CAN SEND USER INFO TO BROWSER  TO DEACTIVATE ANGULAR's GUARDs
    @PreAuthorize("isLogged()")
    @GetMapping("/check")
    public ResponseEntity<Object> checkIfLogged() {

        UserInformationDTO userInformationDTO = null;

        try {
            userInformationDTO = this.accountService
                    .checkUserLogged(currentUser.getEmail());
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }

        return ResponseEntity.ok().body(userInformationDTO);
    }

}
