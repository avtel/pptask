package rav.to;

import lombok.Data;
import lombok.NonNull;

@Data
public class AccountDto {
    @NonNull
    private String login;
    @NonNull
    private String birthday;
    @NonNull
    private String password;
}
