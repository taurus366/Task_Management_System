package com.system.management.service.Impl;

import com.system.management.exception.NotOwnerException;
import com.system.management.model.dto.BoardInformationDTO;
import com.system.management.model.dto.UserInformationDTO;
import com.system.management.model.entity.AccountEntity;
import com.system.management.model.entity.BoardEntity;
import com.system.management.model.entity.TaskEntity;
import com.system.management.model.object.Members;
import com.system.management.model.service.CreateNewBoardServiceModel;
import com.system.management.model.user.CurrentUser;
import com.system.management.repository.AccountRepository;
import com.system.management.repository.BoardRepository;
import com.system.management.repository.TaskRepository;
import com.system.management.service.BoardService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BoardServiceImpl implements BoardService {

    private final AccountRepository accountRepository;
    private final BoardRepository boardRepository;
    private final TaskRepository taskRepository;
    private final CurrentUser currentUser;

    public BoardServiceImpl(AccountRepository accountRepository, BoardRepository boardRepository, TaskRepository taskRepository, CurrentUser currentUser) {
        this.accountRepository = accountRepository;
        this.boardRepository = boardRepository;
        this.taskRepository = taskRepository;
        this.currentUser = currentUser;
    }

    @Override
    public List<UserInformationDTO> getAllAccounts() {

        return this.accountRepository
                .findAll()
                .stream()
                .map(this::asDtoAccount)
                .collect(Collectors.toList());
    }

    @Override
    public void createNewBoard(CreateNewBoardServiceModel createNewBoardServiceModel, String ownerEmail) {
        List<Integer> collect = createNewBoardServiceModel
                .getMembers()
                .stream()
                .map(Members::getId)
                .collect(Collectors.toList());

        AccountEntity ownerAccount = accountRepository.getAccountEntityByEmail(ownerEmail).orElseThrow(() -> new UsernameNotFoundException("We couldn't find the email"));


        BoardEntity newBoard = new BoardEntity();
        newBoard
                .setName(createNewBoardServiceModel.getName())
                .setOwner(ownerAccount)
                .setCreatedAt(Instant.now())
                .setUpdateAt(Instant.now());

        BoardEntity savedBoardEntity = boardRepository
                .save(newBoard);

        List<AccountEntity> allByIds = accountRepository
                .getAllByIds(collect)
                .stream()
                .map(account -> {
                    account.getBoardLists().add(newBoard);
                    return account;
                })
                .collect(Collectors.toList());


        accountRepository
                .saveAll(allByIds);
    }

    @Override
    public List<UserInformationDTO> getAllBoardMembers(Integer boardId) {


        return Objects.requireNonNull(boardRepository
                        .findById(boardId).orElseThrow(() -> new NullPointerException("null board members")))
                .getBoardMember()
                .stream()
                .map(this::asDtoAccount)
                .collect(Collectors.toList());
    }

    @Override
    public List<BoardInformationDTO> getAllBoards() {

        return this.boardRepository
                .findAll()
                .stream()
                .map(this::asDtoBoard)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteBoard(Integer boardId, Integer ownerId) {

        BoardEntity boardEntity = boardRepository
                .getById(boardId);

        if (!Objects.equals(boardEntity.getOwner().getId(), ownerId)) {
            throw new NotOwnerException("");
        }

        if (Objects.equals(boardEntity.getOwner().getId(), ownerId)) {

            List<AccountEntity> allByBoardLists = accountRepository.findAllByBoardLists(boardEntity);
            for (AccountEntity allByBoardList : allByBoardLists) {
                List<BoardEntity> boardLists = allByBoardList.getBoardLists()
                        .stream()
                        .filter(board -> board.getId() != boardEntity.getId())
                        .collect(Collectors.toList());

                allByBoardList.setBoardLists(boardLists);
                accountRepository
                        .save(allByBoardList);

            }

            boardRepository
                    .delete(boardEntity);
        }

    }

    @Override
    public List<BoardInformationDTO> getAllParticipatedBoards(Integer accountId) {


        AccountEntity accountEntity = accountRepository.getById(accountId);

        List<BoardEntity> allByBoardMember = boardRepository
                .findAllByBoardMember(accountEntity);

//        allByBoardMember
//                .forEach(board -> {
//                    int taskSize = board.getTaskList().size();
//                    int memberSize = board.getBoardMember().size();
//
//                   Map<String,String> mapProjects = new HashMap<>();
//
//                    board.getTaskList()
//                            .forEach(taskEntity -> {
//                                if (!mapProjects.containsKey(taskEntity.getTitle())){
//                                    mapProjects.put(taskEntity.getTitle(),"t");
//                                }
//                            });
//                    int projectSize = mapProjects.size();
//
//                    map.put(board,new HashMap<>());
//                    map.get(board).put("taskSize",taskSize);
//                    map.get(board).put("memberSize",memberSize);
//                    map.get(board).put("projectSize",projectSize);
//                });

//        return map.entrySet().stream()
//                .map(this::asDtoBoardM)
//                .collect(Collectors.toList());
        return boards(allByBoardMember, accountId);
    }

    private List<BoardInformationDTO> boards(List<BoardEntity> allByBoardMember, Integer accountId) {
        Map<BoardEntity, Map<String, Integer>> map = new HashMap<>();

        allByBoardMember
                .forEach(board -> {
                    int taskSize = board.getTaskList().size();
                    int memberSize = board.getBoardMember().size();

                    Map<String, String> mapProjects = new HashMap<>();

                    board.getTaskList()
                            .forEach(taskEntity -> {

                                if (!mapProjects.containsKey(taskEntity.getProject().getTitle())) {
                                    mapProjects.put(taskEntity.getProject().getTitle(), "t");
                                }
                            });
                    int projectSize = mapProjects.size();

                    map.put(board, new HashMap<>());
                    map.get(board).put("taskSize", taskSize);
                    map.get(board).put("memberSize", memberSize);
                    map.get(board).put("projectSize", projectSize);
                });
        return map.entrySet().stream()
                .map(this::asDtoBoardM)
                .collect(Collectors.toList());
    }

    @Override
    public List<BoardInformationDTO> getAllOwnBoards(Integer accountId) {
        AccountEntity accountEntity = accountRepository.getById(accountId);

        List<BoardEntity> allByBoardMember = boardRepository
                .findAllByOwner(accountEntity);

        return boards(allByBoardMember, accountId);
    }

    @Override
    public BoardInformationDTO getInfoBoardForEdit(Integer boardId, Integer accountId) {

        AccountEntity accountEntity = accountRepository.getById(accountId);

        BoardEntity boardEntity = boardRepository.getById(boardId);

        return this.asDtoBoardForEdit(boardEntity, accountId);
    }

    @Override
    public void changeInfoBoard(CreateNewBoardServiceModel createNewBoardServiceModel, Integer boardId) {

        BoardEntity boardEntity = boardRepository.getById(boardId);

        List<Integer> collect = createNewBoardServiceModel
                .getMembers()
                .stream()
                .map(Members::getId)
                .collect(Collectors.toList());

        List<AccountEntity> toRemove = boardEntity.getBoardMember();

        boolean isNeedToTouch = false;
        for (Integer integer : collect) {

            boolean isFound = false;
            for (AccountEntity accountEntity : toRemove) {
                if (Objects.equals(integer, accountEntity.getId())) {
                    isFound = true;
                    break;
                }
            }
            if (!isFound) {
                isNeedToTouch = true;
                break;
            }
        }
        System.out.println();

        List<AccountEntity> toAdd = accountRepository
                .getAllByIds(collect);

        if (isNeedToTouch || collect.size() != toRemove.size()) {
            for (AccountEntity account : toRemove) {
                List<BoardEntity> collect1 = account.getBoardLists()
                        .stream()
                        .filter(board -> board.getId() != boardEntity.getId())
                        .collect(Collectors.toList());
                account.setBoardLists(collect1);
                accountRepository
                        .save(account);
            }

            for (AccountEntity account : toAdd) {
                account.getBoardLists().add(boardEntity);
                accountRepository
                        .save(account);
            }
        }

        boardEntity
                .setName(createNewBoardServiceModel.getName());

        boardRepository
                .save(boardEntity);


    }

    private BoardInformationDTO asDtoBoardForEdit(BoardEntity board, Integer accountId) {

        List<UserInformationDTO> boardMember = board.getBoardMember()
                .stream()
                .map(this::asDtoAccount)
                .collect(Collectors.toList());


        return new BoardInformationDTO()
                .setId(board.getId())
                .setOwner(board.getOwner().getId() == accountId)
                .setName(board.getName())
                .setMembers(boardMember);
    }

    private BoardInformationDTO asDtoBoardM(Map.Entry<BoardEntity, Map<String, Integer>> boardEntityMapEntry) {

        Integer currentUserId = currentUser.getId();

        return new BoardInformationDTO()
                .setName(boardEntityMapEntry.getKey().getName())
                .setOwner(boardEntityMapEntry.getKey().getOwner().getId() == currentUserId)
                .setMemberSize(boardEntityMapEntry.getValue().get("memberSize"))
                .setTaskSize(boardEntityMapEntry.getValue().get("taskSize"))
                .setProjectSize(boardEntityMapEntry.getValue().get("projectSize"))
                .setId(boardEntityMapEntry.getKey().getId());
    }

    private UserInformationDTO asDtoAccount(AccountEntity account) {
        return new UserInformationDTO()
                .setFirstName(account.getFirstName())
                .setLastName(account.getLastName())
                .setEmail(account.getEmail())
                .setId(account.getId());
    }

    private BoardInformationDTO asDtoBoard(BoardEntity board) {
        return new BoardInformationDTO()
                .setId(board.getId())
                .setName(board.getName());
    }
}
