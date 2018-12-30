package letbo.interview.kruart.repository;

import letbo.interview.kruart.AbstractTest;
import letbo.interview.kruart.model.Status;
import letbo.interview.kruart.to.GameInfoTo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static letbo.interview.kruart.util.Messages.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GamePlayTest extends AbstractTest {

    @Autowired
    protected GamePlay gamePlay;

    @BeforeEach
    void setUp() {
        gamePlay.newGame();
    }


    @Test
    void testStartGameHasNoRegisteredPlayers() {
        GameInfoTo response = gamePlay.start();
        assertEquals(NO_PLAYERS, response.getMessage());
        assertEquals(0, gamePlay.getPlayers().size());
    }

    @Test
    void testStartGameIsStarted() {
        gamePlay.register(doe.getName());
        GameInfoTo response = gamePlay.start();
        assertEquals(STARTED, response.getMessage());
        assertEquals(1, response.getPlayerNames().size());
        assertEquals(Status.STARTED.getValue(), response.getGameStatus());
        assertEquals("Unknown", response.getWinner());
    }

    @Test
    void testStartGameIsAlreadyStarted() {
        gamePlay.register(doe.getName());
        gamePlay.start();
        assertEquals(String.format(STARTED_OR_FINISHED_FMT, "started"), gamePlay.start().getMessage());
    }

    @Test
    void testStartGameIsAlreadyFinished() {
        gamePlay.register(doe.getName());
        gamePlay.start();
        winTheGame();
        GameInfoTo response = gamePlay.start();

        assertEquals(String.format(STARTED_OR_FINISHED_FMT, "finished"), response.getMessage());
        assertEquals("John Doe", response.getWinner());
    }

    @Test
    void testNewGame() {
        gamePlay.register(doe.getName());
        GameInfoTo response = gamePlay.start();
        assertEquals(STARTED, response.getMessage());
        assertEquals(Status.STARTED.getValue(), response.getGameStatus());

        response = gamePlay.newGame();
        assertEquals(NEW_GAME, response.getMessage());
        assertEquals(Status.NOT_STARTED.getValue(), response.getGameStatus());
        assertEquals(0, response.getPlayerNames().size());
        assertEquals(NONE, response.getCurrentMove());
        assertEquals(NONE, response.getWord());
        assertEquals("Unknown", response.getWinner());
    }

    @Test
    void testRegisterNewPlayer() {
        assertEquals(0, gamePlay.getPlayers().size());
        assertEquals(String.format(PLAYER_REGISTERED_FMT, "John Doe"), gamePlay.register(doe.getName()).getMessage());
    }

    @Test
    void testRegisterPlayerWithThisNameAlreadyExists() {
        gamePlay.register(doe.getName());
        assertEquals(NAME_DUPLICATION, gamePlay.register(doe.getName()).getMessage());
    }

    @Test
    void testRegisterGameIsStarted() {
        gamePlay.register(doe.getName());
        gamePlay.start();
        assertEquals(String.format(STARTED_OR_FINISHED_FMT, "started"), gamePlay.register(doe.getName()).getMessage());
    }

    @Test
    void testRegisterGameIsFinished() {
        gamePlay.register(doe.getName());
        gamePlay.start();

        winTheGame();
        assertEquals(String.format(STARTED_OR_FINISHED_FMT, "finished"), gamePlay.register(doe.getName()).getMessage());
    }

    @Test
    void testGetWord() {
        gamePlay.register(doe.getName());
        gamePlay.start();

        gamePlay.move(doe);   // *e*
        assertEquals("*e*", gamePlay.getWord());
    }

    @Test
    void testGetWordFailed() {
        gamePlay.register(doe.getName());
        assertEquals(NONE, gamePlay.getWord());   // Game Not Started yet
    }

    @Test
    void testMoveSucceed() {
        gamePlay.register(doe.getName());
        gamePlay.start();
        assertEquals(GUESSED, gamePlay.move(doe).getMessage());
    }

    @Test
    void testMoveFailedGameNotStarted() {
        gamePlay.register(roe.getName());
        assertEquals(NOT_STARTED, gamePlay.move(roe).getMessage());
    }

    @Test
    void testMoveMissed() {
        gamePlay.register(roe.getName());
        gamePlay.register(doe.getName());
        assertEquals("Richard Roe", gamePlay.start().getCurrentMove());

        GameInfoTo info = gamePlay.move(roe);
        assertEquals(MISSED, info.getMessage());
        assertEquals("John Doe", info.getCurrentMove());
        assertEquals(2, info.getPlayerNames().size());
    }

    @Test
    void testMoveTheSamePlayerRepeatAfterMissed() {
        gamePlay.register(roe.getName());
        gamePlay.register(doe.getName());
        gamePlay.start();

        assertEquals(MISSED, gamePlay.move(roe).getMessage());
        assertEquals(String.format(NOT_YOUR_MOVE_FMT, "John Doe"), gamePlay.move(roe).getMessage());
    }

    @Test
    void testWinTheGame() {
        gamePlay.register(doe.getName());
        gamePlay.start();
        GameInfoTo response = winTheGame();

        assertEquals(String.format(WINNER_FMT, doe.getName()), response.getMessage());
        assertEquals(doe.getName(), response.getWinner());
        assertEquals(Status.FINISHED.getValue(), response.getGameStatus());
    }

    GameInfoTo winTheGame() {
        gamePlay.move(doe);         // *e*
        doe.setLetter("d");
        gamePlay.move(doe);         // de*
        doe.setLetter("v");
        return gamePlay.move(doe);  // dev
    }
}