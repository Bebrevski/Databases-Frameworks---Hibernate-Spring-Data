package app.service.user;

import app.domain.dtos.UserRegisterDTO;
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

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public String registerUser(UserRegisterDTO userRegisterDTO) {
        Validator validator = Validation
                .byDefaultProvider()
                .configure()
                .buildValidatorFactory()
                .getValidator();

        StringBuilder sb = new StringBuilder();

        if (!userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword())) {

            sb.append("Passwords do not match!").append(System.lineSeparator());

        } else if (validator.validate(userRegisterDTO).size() > 0) {

            for (ConstraintViolation<UserRegisterDTO> violation : validator.validate(userRegisterDTO)) {
                sb.append(violation.getMessage()).append(System.lineSeparator());
            }

        }

        return sb.toString();
    }
}
