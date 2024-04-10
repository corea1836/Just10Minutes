package flab.just10minutes.member.service;

import flab.just10minutes.member.domain.Member;
import flab.just10minutes.member.dto.AddMemberRequest;
import flab.just10minutes.member.repository.MemberDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService{

    private final MemberDao memberDao;

    public void saveMember(AddMemberRequest addRequest) {
        Member newMember = AddMemberRequest.to(addRequest);
        checkDuplicateId(newMember.getId());
        int insertCount = memberDao.save(newMember);
        if (insertCount != 1) {
            throw new IllegalStateException("회원가입 실패");
        }
    }

    public Optional<Member> findById(String id) {
        Assert.notNull(id, "id must not be null");
        return Optional.ofNullable(memberDao.findById(id));
    }

    public void checkDuplicateId(String id) {
        Optional.ofNullable(memberDao.findId(id))
                .ifPresent(action -> {throw new IllegalStateException("해당 아이디가 이미 존재 합니다.");});
    }

//    public void existMemberValidate(String id, String password) {
//        Member member = findMemberById(id);
//        if (!member.getPassword().equals(password)) {
//            throw new IllegalStateException("아이디 혹은 비밀번호를 다시 확인해주세요.");
//        }
//    }
}