package app.service.game;

import app.domain.dtos.GameAddDTO;

public interface GameService {

    String addGame(GameAddDTO gameAddDTO);
}
