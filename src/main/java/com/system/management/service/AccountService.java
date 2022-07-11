package com.system.management.service;

import com.system.management.model.dto.UserInformationDTO;
import com.system.management.model.service.LoginUserServiceModel;
import com.system.management.model.service.RegisterNewUserServiceModel;

public interface AccountService {

    UserInformationDTO login(LoginUserServiceModel loginUserServiceModel);
    UserInformationDTO registerThenLogin(RegisterNewUserServiceModel registerNewUserServiceModel);
    boolean isEmailExists(String email);
    void logout();
    UserInformationDTO checkUserLogged(String email);

}
