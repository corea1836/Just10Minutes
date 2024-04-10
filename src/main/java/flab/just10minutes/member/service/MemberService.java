package flab.just10minutes.member.service;

import flab.just10minutes.member.domain.Member;
import flab.just10minutes.member.dto.SignUpRequestDto;
import flab.just10minutes.member.repository.MemberDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class MemberService{

    private final MemberDao memberDao;

    public void save(SignUpRequestDto signupDto) {
        findById(signupDto.getId()).ifPresent(m -> {throw new RuntimeException("already exist id");});
        int insertCount = memberDao.save(SignUpRequestDto.to(signupDto));
        if (insertCount != 1) {
            throw new RuntimeException("Fail Command. Please Retry!");
        }
    }

    public Optional<Member> findById(String id) {
        Assert.notNull(id, "id must not be null");
        return Optional.ofNullable(memberDao.findById(id));
    }


//    public void existMemberValidate(String id, String password) {
//        Member member = findMemberById(id);
//        if (!member.getPassword().equals(password)) {
//            throw new IllegalStateException("아이디 혹은 비밀번호를 다시 확인해주세요.");
//        }
//    }
}