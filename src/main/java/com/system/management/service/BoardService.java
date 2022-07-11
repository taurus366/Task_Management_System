package com.system.management.service;

import com.system.management.model.dto.UserInformationDTO;
import com.system.management.model.service.CreateNewBoardServiceModel;

import java.util.List;

public interface BoardService {

    List<UserInformationDTO> getAllAccounts();
    void createNewBoard(CreateNewBoardServiceModel createNewBoardServiceModel,String ownerEmail);

}
