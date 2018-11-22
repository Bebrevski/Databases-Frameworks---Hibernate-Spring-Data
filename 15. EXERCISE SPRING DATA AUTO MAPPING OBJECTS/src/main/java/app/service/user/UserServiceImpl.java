package app.service.user;

import app.domain.dtos.GameAddDTO;
import app.domain.dtos.UserLoginDTO;
import app.domain.dtos.UserLogoutDTO;
import app.domain.dtos.UserRegisterDTO;
import app.domain.entities.User;
import app.domain.entities.enums.Role;
import app.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private ModelMapper modelMapper;
    private Validator validator;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;

        this.modelMapper = new ModelMapper();
        this.validator = Validation
                .byDefaultProvider()
                .configure()
                .buildValidatorFactory()
                .getValidator();
    }

    @Override
    public String registerUser(UserRegisterDTO userRegisterDTO) {
        StringBuilder sb = new StringBuilder();

        if (!userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword())) {

            sb.append("Passwords do not match!").append(System.lineSeparator());

        } else if (this.validator.validate(userRegisterDTO).size() > 0) {

            for (ConstraintViolation<UserRegisterDTO> violation : this.validator.validate(userRegisterDTO)) {
                sb.append(violation.getMessage()).append(System.lineSeparator());
            }

        } else {
            User entity = this.userRepository.findByEmail(userRegisterDTO.getEmail()).orElse(null);

            if (entity != null) {
                sb.append("User already exists!").append(System.lineSeparator());
                return sb.toString();
            }

            entity = this.modelMapper.map(userRegisterDTO, User.class);

            if (this.userRepository.count() == 0) {
                entity.setRole(Role.ADMIN);
            } else {
                entity.setRole(Role.USER);
            }

            this.userRepository.saveAndFlush(entity);

            sb
                    .append(String.format("%s was registered", entity.getFullName()))
                    .append(System.lineSeparator());
        }

        return sb.toString().trim();
    }

    @Override
    public String loginUser(UserLoginDTO userLoginDTO) {
        Set<ConstraintViolation<UserLoginDTO>> violations = this.validator.validate(userLoginDTO);

        StringBuilder sb = new StringBuilder();

        if (violations.size() > 0) {
            for (ConstraintViolation<UserLoginDTO> violation : violations) {
                sb.append(violation.getMessage()).append(System.lineSeparator());
            }
        } else {
            User entity = this.userRepository.findByEmail(userLoginDTO.getEmail())
                    .orElse(null);

            if (entity == null) {
                return sb.append("User does not exist!").append(System.lineSeparator()).toString();
            } else if (!entity.getPassword().equals(userLoginDTO.getPassword())) {
                return sb.append("Wrong password").append(System.lineSeparator()).toString();
            }

            sb.append(String.format("Successfully logged in %s", entity.getFullName())).append(System.lineSeparator());

        }

        return sb.toString();
    }

    @Override
    public String logoutUser(UserLogoutDTO userLogoutDTO) {
        User entity = this.userRepository.findByEmail(userLogoutDTO.getEmail())
                .orElse(null);

        StringBuilder sb = new StringBuilder();
        if (entity == null) {
            return sb.append("User does not exist!").append(System.lineSeparator()).toString();
        }

        sb.append(String.format("User %s successfully logged out", entity.getFullName()));

        return sb.toString();
    }

    @Override
    public boolean isAdmin(String email) {
        User entity = this.userRepository.findByEmail(email).orElse(null);

        if(entity != null) {
            return entity.getRole().equals(Role.ADMIN);
        }

        return false;
    }

    public String addGame(GameAddDTO gameAddDTO){
return null;
    }
}
