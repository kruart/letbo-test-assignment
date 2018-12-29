package letbo.interview.kruart.repository;

import letbo.interview.kruart.config.AppConfig;
import letbo.interview.kruart.model.Game;
import letbo.interview.kruart.model.Status;
import letbo.interview.kruart.to.PlayerTO;
import letbo.interview.kruart.util.WordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * In memory realization
 */
@Repository
public class GamePlay {
    private final Logger logger = LoggerFactory.getLogger(GamePlay.class);

    private Game game;

    final AppConfig config;

    public GamePlay(AppConfig config) {
        this.config = config;
        this.game = new Game();
    }

    public String start() {
        logger.info("calling 'start()' method");
        if (game.getStatus() != Status.NOT_STARTED) {
            return "The Game is already " + game.getStatus().getValue() + "!";
        }
        else {
            return startGame();
        }
    }

    public String newGame() {
        logger.info("calling 'newGame()' method");
        this.game = new Game();
        return startGame();
    }

    private String startGame() {
        if (game.getPlayers().size() != 0) {
            String s = WordUtil.randomWord(config.getPathToFile());
            game.createWord(s, config.getMask());
            game.setStatus(Status.STARTED);
            return "The Game is started! Guess the letter: " + getWord();
        } else {
            return "Requires at least 1 player! Please register!";
        }
    }

    public String register(String player) {
        logger.info("calling 'register()' method");
        if (game.getStatus() == Status.NOT_STARTED) {
            if (!getPlayers().contains(player)) {
                game.setPlayer(player);
                return player + " has just registered! We're already have " + getPlayers().size() + " player(s)!";
            } else {
                return "A player with this name already exists!";
            }
        } else if (game.getStatus() == Status.STARTED) {
            return "The game is started!";
        }  else {
            return "The game is over!";  // Status == FINISHED
        }
    }

    public String getWord() {
        logger.info("calling 'getWord()' method");
        if (game.getStatus() == Status.STARTED || game.getStatus() == Status.FINISHED) {
            return String.valueOf(game.getWord().getHiddenLetters());
        } else {
            return "The game has not started yet!";
        }
    }

    public List<String> getPlayers() {
        logger.info("calling 'getPlayers()' method");
        return game.getPlayers();
    }

    public String move(PlayerTO player) {
        logger.info("calling 'move()' method");
        if (game.getStatus() == Status.STARTED) {
            String p = getCurrentPlayer();
            if (p.equals(player.getName())) {
                if (openLetters(player.getLetter().toCharArray()[0])) {
                    return isWin() ? getWinner() :  "Wow! You've guessed! Move again. Guess the letter: " + getWord();
                } else {
                    nextPlayer(p);
                    return "You've missed :(  Now " +  getCurrentPlayer() + " is moving!";
                }
            } else {
                return "Now " +  p + " is moving, not you!";
            }
        } else {
            return "The game has not started yet!";
        }
    }

    /**
     * player who must move now
     */
    private String getCurrentPlayer() {
        return game.getPlayers().get(0);
    }

    /**
     * When the player misses, he becomes at the end of the queue
     */
    private void nextPlayer(String p) {
        game.getPlayers().remove(p);    // remove from the head
        game.setPlayer(p);              // add to the tail
    }

    /**
     * True if player guessed letter(s)
     */
    private boolean openLetters(char letter) {
        boolean isOpened = false;
        for (int i = 0; i < game.getWord().getLetters().length; i++) {
            if (game.getWord().getLetters()[i] == letter) {
                game.getWord().getHiddenLetters()[i] = letter;
                isOpened = true;
            }
        }
        return isOpened;
    }

    private boolean isWin() {
        String actual = String.valueOf(game.getWord().getLetters());
        String hidden = String.valueOf(game.getWord().getHiddenLetters());
        return actual.equals(hidden);
    }

    private String getWinner() {
        game.setStatus(Status.FINISHED);
        return game.getPlayers().get(0) + " is WINNER!";
    }
}
