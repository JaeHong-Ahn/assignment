package com.example.thecommerce.controller;

import com.example.thecommerce.dto.*;
import com.example.thecommerce.entity.SortOption;
import com.example.thecommerce.entity.User;
import com.example.thecommerce.exception.CustomException;
import com.example.thecommerce.exception.ErrorCode;
import com.example.thecommerce.service.UserService;
import com.example.thecommerce.service.UserSetService;
import com.example.thecommerce.util.SessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.example.thecommerce.util.DefaultHttpResponse.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final UserSetService userSetService;

    /**
     * 회원 가입
     * 로그인
     * 로그아웃
     * 회원 목록 조회
     * 회원 정보 수정
     * 회원 비밀번호 수정
     * 회원 탈퇴
     */

    //회원 가입
    @PostMapping("/join")
    public ResponseEntity<?> join(@Validated @RequestBody UserRegisterForm form,
                                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()){
            return DEFAULT_BINDING_ERROR_RESPONSE(bindingResult);
        }

        userSetService.createUser(form);

        return CREATE_SUCCESS_RESPONSE;
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody UserLoginForm form,
                                   BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {

        if (bindingResult.hasErrors()){
            return DEFAULT_BINDING_ERROR_RESPONSE(bindingResult);
        }

        User loginUser = userService.login(form);

        if (loginUser == null) {
            return DEFAULT_ERROR_RESPONSE("아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        validationForIsDeletedUser(request);

        // 세션에 사용자 정보 저장
        HttpSession session = request.getSession();
        session.setAttribute("user", loginUser);

        // 쿠키 설정
        setCookie(response, loginUser);

        return OK_WITH_NO_DATA;
    }

    //로그 아웃
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        expireCookie(response, "userId");

        return OK_WITH_NO_DATA;
    }

    //회원 목록 조회
    @GetMapping("/list")
    public ResponseEntity<?> getUserList(HttpSession session,
                                         @RequestParam(name = "pg", defaultValue = "0") Long page,
                                         @RequestParam(name = "ps", defaultValue = "10") Long pageSize,
                                         @RequestParam(name = "option", defaultValue = "LATEST_JOIN") SortOption sortOption){

        SessionUtil.validateSession(session);

        Sort sort = getSort(sortOption);

        PageRequest pageRequest = PageRequest.of(page.intValue(), pageSize.intValue(), sort);

        Page<User> usersListPage = userService.findAllUsers(pageRequest);

        return DEFAULT_SUCCESS_RESPONSE(usersListPage);
    }

    //회원 정보 수정
    @PostMapping("/{identifier}")
    public ResponseEntity<?> update(@Validated @RequestBody UserUpdateForm form,
                         @PathVariable String identifier, HttpServletRequest request,
                         BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            return DEFAULT_BINDING_ERROR_RESPONSE(bindingResult);
        }

        return DEFAULT_SUCCESS_RESPONSE(
                userSetService.updateUserInfo(form, identifier, Long.valueOf(request.getCookies()[0].getValue())));
    }

    //회원 비밀번호 수정
    @PostMapping("/password/{id}")
    public ResponseEntity<?> updatePassword(@Validated @RequestBody UserUpdatePasswordForm form,
                         @PathVariable Long id,
                         BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return DEFAULT_BINDING_ERROR_RESPONSE(bindingResult);
        }

        userSetService.updateUserPassword(id, form);

        return OK_WITH_NO_DATA;
    }

    //회원 탈퇴
    @PostMapping("/withdrawal")
    public ResponseEntity<?> withdrawal(HttpServletRequest request){

        Cookie cookie = request.getCookies()[0];

        userSetService.withdrawal(Long.valueOf(cookie.getValue()));
        return OK_WITH_NO_DATA;
    }

    private static Sort getSort(SortOption sortOption) {
        Sort sort;

        switch (sortOption) {
            case EARLY_JOIN:
                sort = Sort.by(Sort.Direction.ASC, "createdAt");
                break;
            case LATEST_JOIN:
                sort = Sort.by(Sort.Direction.DESC, "createdAt");
                break;
            case NAME_DESC:
                sort = Sort.by(Sort.Direction.DESC, "name");
                break;
            case NAME_ASC:
                sort = Sort.by(Sort.Direction.ASC, "name");
                break;
            default:
                throw new CustomException(ErrorCode.SORT_NOT_FOUND);
        }
        return sort;
    }

    private static void setCookie(HttpServletResponse response, User loginUser) {
        Cookie idCookie = new Cookie("userId", String.valueOf(loginUser.getId()));
        response.addCookie(idCookie);
    }

    private void expireCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    private void validationForIsDeletedUser(HttpServletRequest request) {
        if (userService.findUserById(Long.valueOf(request.getCookies()[0].getValue())).getIsDeleted()){
            throw new CustomException(ErrorCode.NOT_AVAILABLE_USER);
        }
    }
}
