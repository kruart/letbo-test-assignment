package letbo.interview.kruart.controller;

import letbo.interview.kruart.repository.GamePlay;
import letbo.interview.kruart.to.GameInfoTo;
import letbo.interview.kruart.to.PlayerTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/game")
public class GamePlayController {
    private final Logger logger = LoggerFactory.getLogger(GamePlayController.class);

    @Autowired
    private GamePlay gamePlay;

    @GetMapping("/word")
    public ResponseEntity<String> getWord() {
        logger.debug("calling 'getWord' method ['/game/word']");
        return ResponseEntity.ok(gamePlay.getWord());
    }

    @GetMapping("/players")
    public ResponseEntity<List<String>> getPlayers() {
        logger.debug("calling 'getPlayers' method ['/game/players']");
        return ResponseEntity.ok(gamePlay.getPlayers());
    }

    @PostMapping("/start")
    public GameInfoTo start() {
        logger.debug("calling 'start' method ['/game/start']");
        return gamePlay.start();
    }

    @PostMapping("/new")
    public GameInfoTo newgame() {
        logger.debug("calling 'newgame' method ['/game/new']");
        return gamePlay.newGame();
    }

    @PostMapping(value="/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public GameInfoTo register(@RequestBody PlayerTo player) {
        if (player.getName() == null) throw new NullPointerException("Name cannot be null");
        logger.debug("calling 'register' method ['/game/register']");
        return gamePlay.register(player.getName());
    }

    @PutMapping("/move")
    public GameInfoTo move(@Valid @RequestBody PlayerTo player) {
        logger.debug("calling 'move' method ['/game/move']");
        return gamePlay.move(player);
    }

    GamePlay getGamePlay() {
        return gamePlay;
    }
}
