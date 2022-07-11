package com.system.management.web;

import com.system.management.model.binding.CreateNewBoardBindingModel;
import com.system.management.model.dto.UserInformationDTO;
import com.system.management.model.service.CreateNewBoardServiceModel;
import com.system.management.model.user.CurrentUser;
import com.system.management.service.BoardService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final ModelMapper modelMapper;
    private final CurrentUser currentUser;

    public BoardController(BoardService boardService, ModelMapper modelMapper, CurrentUser currentUser) {
        this.boardService = boardService;
        this.modelMapper = modelMapper;
        this.currentUser = currentUser;
    }


    @GetMapping("/create")
    @PreAuthorize("isLogged()")
    public ResponseEntity<Object> getAccountsForBoard() {


        List<UserInformationDTO> allAccounts = this.boardService
                .getAllAccounts();
        System.out.println(allAccounts.size());
        return ResponseEntity.ok().body(allAccounts);
    }

    @PostMapping("/create")
    @PreAuthorize("isLogged()")
    public ResponseEntity<Object> createBoard(@Valid @RequestBody CreateNewBoardBindingModel createNewBoardBindingModel, BindingResult bindingResult , RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("createNewBoardBindingModel",createNewBoardBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.createNewBoardBindingModel",bindingResult);

            return ResponseEntity.status(HttpStatus.CONFLICT).body(bindingResult.getAllErrors());
        }

        boardService
                .createNewBoard(modelMapper.map(createNewBoardBindingModel, CreateNewBoardServiceModel.class), currentUser.getEmail());

        return null;
    }
}
