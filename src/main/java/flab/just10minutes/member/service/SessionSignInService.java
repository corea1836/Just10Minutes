package flab.just10minutes.member.service;

import flab.just10minutes.member.Entity.Member;
import flab.just10minutes.member.dto.SignInRequestDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessionSignInService {

    private final MemberService memberService;
    private final HttpSession httpSession;


    public void signIn(SignInRequestDto signInDto) {
        checkSignInInfo(signInDto);
        httpSession.setAttribute("signInMember", signInDto.getId());
    }


    public void logOut() {
//        boolean isLogin = isLogin();
//        if (!isLogin) {
//            throw new IllegalStateException("로그인 되어있지 않습니다. 로그인 해주세요.");
//        }
        httpSession.invalidate();
    }

    public boolean isLogin() {
        return Optional
                .ofNullable(httpSession.getAttribute("loginMember"))
                .isPresent();
    }

    private void checkSignInInfo(SignInRequestDto signInDto) {
        Member member = memberService.findById(signInDto.getId())
                .orElseThrow(() -> new RuntimeException("not exist member!"));

        if (!StringUtils.equals(member.getPassword(), signInDto.getPassword())) {
            throw new RuntimeException("invalid id or password");
        }
    }




}

