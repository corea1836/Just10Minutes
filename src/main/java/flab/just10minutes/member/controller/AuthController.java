package flab.just10minutes.member.controller;

import flab.just10minutes.aop.MemberLoginCheck;
import flab.just10minutes.member.dto.SignInRequestDto;
import flab.just10minutes.member.service.MemberService;
import flab.just10minutes.member.service.SessionSignInService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {


    private final SessionSignInService signInService;


    @PostMapping("/login")
    public ResponseEntity<HttpStatus> signIn(@RequestBody @Valid SignInRequestDto signInDto) {
        signInService.signIn(signInDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/logout")
    @MemberLoginCheck
    public ResponseEntity<HttpStatus> logout() {
        signInService.logOut();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}