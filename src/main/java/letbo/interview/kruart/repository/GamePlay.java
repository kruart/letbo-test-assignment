package letbo.interview.kruart.repository;

import letbo.interview.kruart.config.AppConfig;
import letbo.interview.kruart.model.Game;
import letbo.interview.kruart.model.Status;
import letbo.interview.kruart.to.GameInfoTo;
import letbo.interview.kruart.to.PlayerTo;
import letbo.interview.kruart.util.WordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentLinkedQueue;

import static letbo.interview.kruart.util.Messages.*;

/**
 * In memory realization
 */
@Repository
public class GamePlay {
    private final Logger logger = LoggerFactory.getLogger(GamePlay.class);

    private Game game;

    private final AppConfig config;

    public GamePlay(AppConfig config) {
        this.config = config;
        this.game = new Game();
    }

    public GameInfoTo start() {
        logger.info("calling 'start()' method");
        if (game.getStatus() != Status.NOT_STARTED) {
            return gameInfo(String.format(STARTED_OR_FINISHED_FMT, game.getStatus().getValue()));
        }
        else {
            return startGame();
        }
    }

    public GameInfoTo newGame() {
        logger.info("calling 'newGame()' method");
        this.game = new Game();
        return gameInfo(NEW_GAME);
    }

    private GameInfoTo startGame() {
        if (game.getPlayers().size() != 0) {
            String s = WordUtil.randomWord(config.getPathToFile());
            game.setWord(s, config.getMask());
            game.setStatus(Status.STARTED);
            return gameInfo(STARTED);
        } else {
            return gameInfo(NO_PLAYERS);
        }
    }

    public GameInfoTo register(String player) {
        logger.info("calling 'register()' method");
        if (game.getStatus() == Status.NOT_STARTED) {
            if (!getPlayers().contains(player)) {
                game.setPlayer(player);
                return gameInfo(String.format(PLAYER_REGISTERED_FMT, player));
            } else {
                return gameInfo(NAME_DUPLICATION);
            }
        } else  {
            return gameInfo(String.format(STARTED_OR_FINISHED_FMT, game.getStatus().getValue()));   // Status == STARTED or FINISHED
        }
    }

    public String getWord() {
        logger.info("calling 'getWord()' method");
        if (game.getStatus() == Status.STARTED || game.getStatus() == Status.FINISHED) {
            return String.valueOf(game.getWord().getHiddenLetters());
        } else {
            return NONE;
        }
    }

    public ConcurrentLinkedQueue<String> getPlayers() {
        logger.info("calling 'getPlayers()' method");
        return game.getPlayers();
    }

    public GameInfoTo move(PlayerTo player) {
        logger.info("calling 'move()' method");
        if (game.getStatus() == Status.STARTED) {
            String p = getCurrentPlayer();
            if (p.equals(player.getName())) {
                if (openLetters(player.getLetter().toCharArray()[0])) {
                    return isWin() ? getWinner() :  gameInfo(GUESSED);
                } else {
                    nextPlayer();
                    return gameInfo(MISSED);
                }
            } else {
                return gameInfo(String.format(NOT_YOUR_MOVE_FMT, p));
            }
        } else {
            return gameInfo(NOT_STARTED);
        }
    }

    /**
     *  Return an object which contains game info(state)
     */
    public GameInfoTo gameInfo(String message) {
        return new GameInfoTo(getWord(), getCurrentPlayer(), game.getStatus().getValue(), message, getPlayers(), game.getWinner());
    }

    /**
     * player who must move now
     */
    private String getCurrentPlayer() {
        return game.getPlayers().size() != 0 ? game.getPlayers().peek() : NONE;
    }

    /**
     * When the player misses, he becomes at the end of the queue
     */
    private void nextPlayer() {
        game.setPlayer(game.getPlayers().poll()); //take the head and add it to the tail
    }

    /**
     * True if player guessed letter(s)
     */
    private boolean openLetters(char letter) {
        boolean isOpened = false;
        synchronized (this) {
            for (int i = 0; i < game.getWord().getLetters().length; i++) {
                if (game.getWord().getLetters()[i] == letter
                        && String.valueOf(game.getWord().getHiddenLetters()[i]).equals(game.getWord().getMask())) { //if not yet open
                    game.getWord().getHiddenLetters()[i] = letter;
                    isOpened = true;
                }
            }
        }
        return isOpened;
    }

    /**
     * Return True if actual word equals to hidden word else false
     */
    private boolean isWin() {
        String actual = String.valueOf(game.getWord().getLetters());
        String hidden = String.valueOf(game.getWord().getHiddenLetters());
        return actual.equals(hidden);
    }

    private GameInfoTo getWinner() {
        game.setStatus(Status.FINISHED);
        game.setWinner(getCurrentPlayer());
        return gameInfo(String.format(WINNER_FMT, getCurrentPlayer()));
    }
}
