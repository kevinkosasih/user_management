package id.user.user_management.responseException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFound extends RuntimeException{
    public UserNotFound() {
        super("User not found.");
    }
}
