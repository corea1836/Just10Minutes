package flab.just10minutes.member.dto;

import flab.just10minutes.member.Entity.Member;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SignUpRequestDto {


    private final String id;
    private final String password;
    private final String name;
    private final String address;

    public static Member to(SignUpRequestDto addMemberRequest) {
        return Member.builder()
                .id(addMemberRequest.id)
                .password(addMemberRequest.password)
                .name(addMemberRequest.name)
                .address(addMemberRequest.address)
                .build();
    }
}
