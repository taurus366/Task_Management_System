package com.system.management.service.Impl;

import com.system.management.model.dto.UserInformationDTO;
import com.system.management.model.entity.AccountEntity;
import com.system.management.model.entity.BoardEntity;
import com.system.management.model.object.Members;
import com.system.management.model.service.CreateNewBoardServiceModel;
import com.system.management.repository.AccountRepository;
import com.system.management.repository.BoardRepository;
import com.system.management.service.BoardService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardServiceImpl implements BoardService {

    private final AccountRepository accountRepository;
    private final BoardRepository boardRepository;

    public BoardServiceImpl(AccountRepository accountRepository, BoardRepository boardRepository) {
        this.accountRepository = accountRepository;
        this.boardRepository = boardRepository;
    }

    @Override
    public List<UserInformationDTO> getAllAccounts() {

        return  this.accountRepository
                .findAll()
                .stream()
                .map(this::asDto)
                .collect(Collectors.toList());
    }

    @Override
    public void createNewBoard(CreateNewBoardServiceModel createNewBoardServiceModel, String ownerEmail) {
        List<Integer> collect = createNewBoardServiceModel
                .getMembers()
                .stream()
                .map(Members::getId)
                        .collect(Collectors.toList());
//                .map(integer -> {
//                    return Integer.toString(integer);
//                })
//                .collect(Collectors.joining(","));

        List<AccountEntity> allByIds = accountRepository
                .getAllByIds(collect);
        System.out.println(allByIds.size());

        AccountEntity ownerAccount = accountRepository.getAccountEntityByEmail(ownerEmail).orElseThrow(() -> new UsernameNotFoundException("We couldn't find the email"));

        BoardEntity newBoard = new BoardEntity();
        newBoard
                .setBoardMember(allByIds)
                .setName(createNewBoardServiceModel.getName())
                .setOwner(ownerAccount)
                .setCreatedAt(Instant.now())
                .setUpdateAt(Instant.now());

            boardRepository
                    .save(newBoard);
    }

    private UserInformationDTO asDto(AccountEntity account) {
        return new UserInformationDTO()
                .setFirstName(account.getFirstName())
                .setLastName(account.getLastName())
                .setEmail(account.getEmail())
                .setId(account.getId());
    }
}
