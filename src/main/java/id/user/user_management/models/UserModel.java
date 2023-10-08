package id.user.user_management.models;

import id.user.user_management.entity.User;
import lombok.Data;


@Data
public class UserModel extends User {

    String newPassword;
}
