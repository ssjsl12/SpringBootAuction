package com.example.auctionshop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberFormDto {

    //NotEmpty 서버에서 확인

    @NotEmpty(message = "이름은 필수 입력 값입니다")
    private String name;


    @NotEmpty(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식으로 입력해주세요")
    private String email;

    @NotEmpty(message = "패스워드는 필수 입력 값입니다.")
    @Length(min = 4, max = 16 , message = "최소 4자이상 16자 이하입니다")
    private String password;

    @NotEmpty(message = "주소는 필수 입력 값입니다.")
    private String address;

    @NotEmpty(message = "휴대폰 번호는 필수 입력 값입니다")
    private String phone;

}
