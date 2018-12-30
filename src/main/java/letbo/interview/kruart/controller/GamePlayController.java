package letbo.interview.kruart.controller;

import letbo.interview.kruart.repository.GamePlay;
import letbo.interview.kruart.to.PlayerTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/game")
public class GamePlayController {
    private final Logger logger = LoggerFactory.getLogger(GamePlayController.class);

    @Autowired
    private GamePlay gamePlay;

    @GetMapping("/word")
    public String getWord() {
        logger.debug("calling 'getWord' method ['/game/word']");
        return gamePlay.getWord();
    }

    @GetMapping("/players")
    public List<String> getPlayers() {
        logger.debug("calling 'getPlayers' method ['/game/players']");
        return gamePlay.getPlayers();
    }

    @PostMapping("/start")
    public String start() {
        logger.debug("calling 'start' method ['/game/start']");
        return gamePlay.start();
    }

    @PostMapping("/new")
    public String newgame() {
        logger.debug("calling 'newgame' method ['/game/new']");
        return gamePlay.newGame();
    }

    @PostMapping(value="/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String register(@RequestBody PlayerTO player) {
        logger.debug("calling 'register' method ['/game/register']");
        return gamePlay.register(player.getName());
    }

    @PutMapping("/move")
    public String move(@RequestBody PlayerTO player) {
        logger.debug("calling 'move' method ['/game/move']");
        return gamePlay.move(player);
    }

    public GamePlay getGamePlay() {
        return gamePlay;
    }
}
