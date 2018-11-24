package app.services.users;

import app.domain.dtos.UserSeedDto;

public interface UserService {
    void seedUsers(UserSeedDto[] userSeedDtos);
}
