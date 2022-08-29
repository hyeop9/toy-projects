package study.board.web.login;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 로그인 데이터
 */
@Data
public class LoginForm {

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;
}
