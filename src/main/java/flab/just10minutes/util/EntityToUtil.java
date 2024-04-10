package flab.just10minutes.util;

import flab.just10minutes.member.Entity.Member;
import flab.just10minutes.member.dto.MemberDto;
import flab.just10minutes.member.dto.SignUpRequestDto;

public class EntityToUtil {

    public static Member toEntity(SignUpRequestDto signupDto) {
        return Member.builder()
                .id(signupDto.getId())
                .password(signupDto.getPassword())
                .name(signupDto.getName())
                .address(signupDto.getAddress())
                .build();
    }

    public static MemberDto toDto(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .name(member.getName())
                .address(member.getAddress())
                .balance(member.getBalance())
                .build();
    }
}
