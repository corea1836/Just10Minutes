package flab.just10minutes.member.service;

import flab.just10minutes.member.Entity.Member;
import flab.just10minutes.member.dto.SignUpRequestDto;
import flab.just10minutes.member.repository.MemberDao;
import flab.just10minutes.util.EntityTo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService{

    private final MemberDao memberDao;

    public void save(SignUpRequestDto signupDto) {
        findById(signupDto.getId()).ifPresent(m -> {throw new RuntimeException("already exist id");});
        int insertCount = memberDao.save(EntityTo.toEntity(signupDto));
        if (insertCount != 1) {
            throw new RuntimeException("Fail Command. Please Retry!");
        }
    }

    public Optional<Member> findById(String id) {
        Assert.notNull(id, "id must not be null");
        return Optional.ofNullable(memberDao.findById(id));
    }


}