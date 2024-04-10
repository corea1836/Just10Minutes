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


    private final String sessionKey = "signInMember";
    private final MemberService memberService;
    private final HttpSession httpSession;


    public void signIn(SignInRequestDto signInDto) {
        checkSignInInfo(signInDto);
        httpSession.setAttribute(sessionKey, signInDto.getId());
    }


    public void signOut() {
        httpSession.invalidate();
    }

    public boolean isSignIn() {
        return httpSession.getAttribute(sessionKey) != null ? true : false;
    }

    private void checkSignInInfo(SignInRequestDto signInDto) throws RuntimeException{
        Member member = memberService.findById(signInDto.getId())
                .orElseThrow(() -> new RuntimeException("not exist member!"));

        if (!StringUtils.equals(member.getPassword(), signInDto.getPassword())) {
            throw new RuntimeException("invalid id or password");
        }
    }




}

