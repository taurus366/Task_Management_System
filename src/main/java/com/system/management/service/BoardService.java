package com.system.management.service;

import com.system.management.model.dto.BoardInformationDTO;
import com.system.management.model.dto.UserInformationDTO;
import com.system.management.model.service.CreateNewBoardServiceModel;

import java.util.List;

public interface BoardService {

    List<UserInformationDTO> getAllAccounts();
    void createNewBoard(CreateNewBoardServiceModel createNewBoardServiceModel,String ownerEmail);
    List<UserInformationDTO> getAllBoardMembers(Integer boardId);
    List<BoardInformationDTO> getAllBoards();
    void deleteBoard(Integer boardId,Integer ownerId);
    List<BoardInformationDTO> getAllParticipatedBoards(Integer accountId);
    List<BoardInformationDTO> getAllOwnBoards(Integer accountId);
    BoardInformationDTO getInfoBoardForEdit(Integer boardId,Integer accountId);
    void changeInfoBoard(CreateNewBoardServiceModel createNewBoardServiceModel,Integer boardId);


}
