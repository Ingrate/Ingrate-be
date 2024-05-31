package dompoo.Ingrate.member.dto;

import dompoo.Ingrate.member.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class SignUpResponse {

    private final Long id;
    private final String username;

    public SignUpResponse(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
    }
}
