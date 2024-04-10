package flab.just10minutes.member.controller;

import flab.just10minutes.member.dto.MemberDto;
import flab.just10minutes.member.dto.SignUpRequestDto;
import flab.just10minutes.member.service.MemberService;
import flab.just10minutes.util.EntityToUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
@Slf4j
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<HttpStatus> signUp(@RequestBody @Valid SignUpRequestDto signUpDto) {
        memberService.save(signUpDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberDto> getMemberProfile(@PathVariable String id) {
        MemberDto memberDto = EntityToUtil.toDto(memberService.findById(id)
                .orElseThrow(() -> new RuntimeException("not exist member!")));
        return new ResponseEntity<MemberDto>(memberDto, HttpStatus.OK);
    }


}
