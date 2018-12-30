package letbo.interview.kruart.repository;

import letbo.interview.kruart.AbstractTest;
import letbo.interview.kruart.model.Status;
import letbo.interview.kruart.to.GameInfoTo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
        assertEquals("Requires at least 1 player! Please register!", response.getMessage());
        assertEquals(0, gamePlay.getPlayers().size());
    }

    @Test
    void testStartGameIsStarted() {
        gamePlay.register(doe.getName());
        GameInfoTo response = gamePlay.start();
        assertEquals("The Game is started!", response.getMessage());
        assertEquals(1, response.getPlayerNames().size());
        assertEquals(Status.STARTED.getValue(), response.getGameStatus());
        assertEquals("Unknown", response.getWinner());
    }

    @Test
    void testStartGameIsAlreadyStarted() {
        gamePlay.register(doe.getName());
        gamePlay.start();
        assertEquals("The Game is already started!", gamePlay.start().getMessage());
    }

    @Test
    void testStartGameIsAlreadyFinished() {
        gamePlay.register(doe.getName());
        gamePlay.start();
        winTheGame();
        GameInfoTo response = gamePlay.start();

        assertEquals("The Game is already finished!", response.getMessage());
        assertEquals("John Doe", response.getWinner());
    }

    @Test
    void testNewGame() {
        gamePlay.register(doe.getName());
        GameInfoTo response = gamePlay.start();
        assertEquals("The Game is started!", response.getMessage());
        assertEquals(Status.STARTED.getValue(), response.getGameStatus());

        response = gamePlay.newGame();
        assertEquals("The New Game is created! Please register!", response.getMessage());
        assertEquals(Status.NOT_STARTED.getValue(), response.getGameStatus());
        assertEquals(0, response.getPlayerNames().size());
        assertEquals("None", response.getCurrentMove());
        assertEquals("None", response.getWord());
        assertEquals("Unknown", response.getWinner());
    }

    @Test
    void testRegisterNewPlayer() {
        assertEquals(0, gamePlay.getPlayers().size());
        assertEquals("John Doe has just registered!", gamePlay.register(doe.getName()).getMessage());
    }

    @Test
    void testRegisterPlayerWithThisNameAlreadyExists() {
        gamePlay.register(doe.getName());
        assertEquals("A player with this name already exists!", gamePlay.register(doe.getName()).getMessage());
    }

    @Test
    void testRegisterGameIsStarted() {
        gamePlay.register(doe.getName());
        gamePlay.start();
        assertEquals("The Game is already started!", gamePlay.register(doe.getName()).getMessage());
    }

    @Test
    void testRegisterGameIsFinished() {
        gamePlay.register(doe.getName());
        gamePlay.start();

        winTheGame();
        assertEquals("The Game is already finished!", gamePlay.register("John Doe").getMessage());
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
        assertEquals("None", gamePlay.getWord());   // Game Not Started yet
    }

    @Test
    void testMoveSucceed() {
        gamePlay.register(doe.getName());
        gamePlay.start();
        assertEquals("Wow! You've guessed! Move again.", gamePlay.move(doe).getMessage());
    }

    @Test
    void testMoveFailedGameNotStarted() {
        gamePlay.register(roe.getName());
        assertEquals("The Game has not started yet!", gamePlay.move(roe).getMessage());
    }

    @Test
    void testMoveMissed() {
        gamePlay.register(roe.getName());
        gamePlay.register(doe.getName());
        assertEquals(roe.getName(), gamePlay.start().getCurrentMove());

        GameInfoTo info = gamePlay.move(roe);
        assertEquals("You've missed :/", info.getMessage());
        assertEquals(doe.getName(), info.getCurrentMove());
        assertEquals(2, info.getPlayerNames().size());
    }

    @Test
    void testMoveTheSamePlayerRepeatAfterMissed() {
        gamePlay.register(roe.getName());
        gamePlay.register(doe.getName());
        gamePlay.start();

        assertEquals("You've missed :/", gamePlay.move(roe).getMessage());
        assertEquals("Now John Doe is moving, not you!", gamePlay.move(roe).getMessage());
    }

    @Test
    void testWinTheGame() {
        gamePlay.register(doe.getName());
        gamePlay.start();
        GameInfoTo response = winTheGame();

        assertEquals("John Doe is WINNER!", response.getMessage());
        assertEquals("John Doe", response.getWinner());
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