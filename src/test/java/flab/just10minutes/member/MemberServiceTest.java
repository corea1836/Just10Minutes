package flab.just10minutes.member;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @InjectMocks
    private MemberService target;

    @Mock
    private MemberDao memberDao;

    private final String id = "userId";
    private final String password = "userPassword";
    private final Member.MemberType memberType = Member.MemberType.PUBLIC;
    private final Long balance = 0L;

    public SignUpRequestDto signUpDto() {
        return SignUpRequestDto.builder()
                .id(id)
                .password(password)
                .build();
    }
    public Member member() {
        return Member.builder()
                .id(id)
                .password(password)
                .type(Member.MemberType.PUBLIC)
                .balance(0L)
                .build();
    }
    public MemberDto memberDto() {
        return MemberDto.builder()
                .id(id)
                .type(Member.MemberType.PUBLIC)
                .balance(0L)
                .build();
    }

    @Test
    public void 멤버등록실패_이미존재함() {
        doReturn(Member.builder().build()).when(memberDao).findById(id);

        final MemberException result = Assertions.assertThrows(MemberException.class, () -> target.signUp(signUpDto()));

        Assertions.assertEquals(result.getErrorResult(), MemberErrorResult.DUPLICATE_MEMBER_REGISTER);
    }

    @Test
    public void 멤버등록성공() {
        doReturn(null)
                .doReturn(member())
                .when(memberDao).findById(id);
        doReturn(1).when(memberDao).save(any(Member.class));


        final MemberDto response = target.signUp(signUpDto());


        verify(memberDao, times(2)).findById(id);
        verify(memberDao, times(1)).save(any(Member.class));
    }
    @Service
    @RequiredArgsConstructor
    static class MemberService {

        private final MemberDao memberDao;
        public MemberDto signUp(SignUpRequestDto signUpDto) {
            Member member = memberDao.findById(signUpDto.getId());
            if(member != null) {
                throw new MemberException(MemberErrorResult.DUPLICATE_MEMBER_REGISTER);
            }

            Member newMember = Member.builder()
                    .id(signUpDto.getId())
                    .password(signUpDto.getPassword())
                    .type(Member.MemberType.PUBLIC)
                    .balance(0L)
                    .build();

            memberDao.save(newMember);

            Member response = memberDao.findById(signUpDto.getId());
            return MemberDto.builder()
                    .id(response.id)
                    .type(response.type)
                    .balance(response.balance)
                    .build();
        }
    }

    @Repository
    interface MemberDao {
        public Member findById(final String id);
        public int save(final Member member);
    }

    @Builder
    static class Member {
        enum MemberType {
            PUBLIC,
            ADMIN
        }

        private String id;
        private String password;
        private MemberType type;
        private Long balance;

    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @EqualsAndHashCode
    static class SignUpRequestDto {
        @NotEmpty
        private String id;
        @NotEmpty
        private String password;
    }

    @Builder
    static class MemberDto {
        private String id;
        private Member.MemberType type;
        private Long balance;
    }

    @Getter
    @RequiredArgsConstructor
    enum MemberErrorResult {
        DUPLICATE_MEMBER_REGISTER(HttpStatus.BAD_REQUEST, "Duplicate member info for signUp"),
        UNKNOWN_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown Exception");

        private final HttpStatus httpStatus;
        private final String message;
    }

    @Getter
    @RequiredArgsConstructor
    static class MemberException extends RuntimeException {
        private final MemberErrorResult errorResult;
    }


}
