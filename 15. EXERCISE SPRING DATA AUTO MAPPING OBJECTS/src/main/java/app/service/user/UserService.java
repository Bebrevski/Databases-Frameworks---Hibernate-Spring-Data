package app.service.user;

import app.domain.dtos.UserLoginDTO;
import app.domain.dtos.UserLogoutDTO;
import app.domain.dtos.UserRegisterDTO;

public interface UserService {

    String registerUser(UserRegisterDTO userRegisterDTO);

    String loginUser(UserLoginDTO userLoginDTO);

    String logoutUser(UserLogoutDTO userLogoutDTO);

    boolean isAdmin(String email);
}
