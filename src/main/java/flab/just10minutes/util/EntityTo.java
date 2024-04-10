package flab.just10minutes.util;

import flab.just10minutes.member.Entity.Member;
import flab.just10minutes.member.dto.SignUpRequestDto;

public class EntityTo {

    public static Member toEntity(SignUpRequestDto signupDto) {
        return Member.builder()
                .id(signupDto.getId())
                .password(signupDto.getPassword())
                .name(signupDto.getName())
                .address(signupDto.getAddress())
                .build();
    }
}
