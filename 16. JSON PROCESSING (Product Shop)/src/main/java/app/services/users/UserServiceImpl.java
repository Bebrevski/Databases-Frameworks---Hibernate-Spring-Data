package app.services.users;

import app.domain.dtos.UserSeedDto;
import app.domain.entities.User;
import app.repositories.UserRepository;
import app.utils.validator.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ValidatorUtil validatorUtil, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedUsers(UserSeedDto[] userSeedDtos) {
        for (UserSeedDto userSeedDto : userSeedDtos) {
            if (!this.validatorUtil.isValid(userSeedDto)) {
                this.validatorUtil.violations(userSeedDto)
                        .forEach(violation -> System.out.println(violation.getMessage()));

                continue;
            }

            User entity = this.modelMapper.map(userSeedDto, User.class);

            this.userRepository.saveAndFlush(entity);
        }
    }
}
