package flab.just10minutes.member.dto;

import flab.just10minutes.member.Entity.Member;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDto {

    private String id;
    private String name;
    private String address;
    private Long balance;

    @Builder
    public MemberDto(String id, String name, String address, Long balance) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.balance = balance;
    }

    public static MemberDto to(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .name(member.getName())
                .address(member.getAddress())
                .balance(member.getBalance())
                .build();
    }

}
