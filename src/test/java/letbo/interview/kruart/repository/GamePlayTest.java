package letbo.interview.kruart.repository;

import letbo.interview.kruart.AbstractTest;
import letbo.interview.kruart.to.PlayerTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GamePlayTest extends AbstractTest {

    @Autowired
    protected GamePlay gamePlay;

    PlayerTO doe = new PlayerTO("John Doe", "e");
    PlayerTO roe = new PlayerTO("Richard Roe", "f");

    @BeforeEach
    void setUp() {
        gamePlay.newGame();
    }


    @Test
    void testStartGameHasNoRegisteredPlayers() {
        assertEquals("Requires at least 1 player! Please register!", gamePlay.start());
        assertEquals(0, gamePlay.getPlayers().size());
    }

    @Test
    void testStartGameIsStarted() {
        gamePlay.register(doe.getName());
        String response = gamePlay.start();
        assertEquals("The Game is started! Guess the letter: " + gamePlay.getWord(), response);
        assertEquals(1, gamePlay.getPlayers().size());
    }

    @Test
    void testStartGameIsAlreadyStarted() {
        gamePlay.register(doe.getName());
        gamePlay.start();
        assertEquals("The Game is already started!", gamePlay.start());
    }

    @Test
    void testStartGameIsAlreadyFinished() {
        gamePlay.register(doe.getName());
        gamePlay.start();

        winTheGame();
        assertEquals("The Game is already finished!", gamePlay.start());
    }

    @Test
    void testRegisterNewPlayer() {
        assertEquals(0, gamePlay.getPlayers().size());
        String response = "John Doe has just registered! We're already have 1 player(s)!";
        assertEquals(response, gamePlay.register(doe.getName()));
    }

    @Test
    void testRegisterPlayerWithThisNameAlreadyExists() {
        gamePlay.register(doe.getName());
        assertEquals("A player with this name already exists!", gamePlay.register(doe.getName()));
    }

    @Test
    void testRegisterGameIsStarted() {
        gamePlay.register(doe.getName());
        gamePlay.start();
        assertEquals("The game is started!", gamePlay.register(doe.getName()));
    }

    @Test
    void testRegisterGameIsFinished() {
        gamePlay.register(doe.getName());
        gamePlay.start();

        winTheGame();
        assertEquals("The game is over!", gamePlay.register("John Doe"));
    }

    @Test
    void testGetWord() {
        gamePlay.register(doe.getName());
        gamePlay.start();

        gamePlay.move(doe);   // *e*
        assertEquals("*e*", gamePlay.getWord());
    }

    @Test
    void testGetWordFailedGameNotStarted() {
        gamePlay.register(doe.getName());
        assertEquals("The game has not started yet!", gamePlay.getWord());
    }

    @Test
    void testMoveSucceed() {
        gamePlay.register(doe.getName());
        gamePlay.start();
        assertEquals("Wow! You've guessed! Move again. Guess the letter: *e*", gamePlay.move(doe));
    }

    @Test
    void testMoveFailedGameNotStarted() {
        gamePlay.register(roe.getName());
        assertEquals("The game has not started yet!", gamePlay.move(roe));
    }

    @Test
    void testMoveMissed() {
        gamePlay.register(roe.getName());
        gamePlay.register(doe.getName());
        gamePlay.start();
        String response = gamePlay.move(roe);

        assertEquals("You've missed :(  Now John Doe is moving!", response);
    }

    @Test
    void testMoveRepeatAfterMissed() {
        gamePlay.register(roe.getName());
        gamePlay.register(doe.getName());
        gamePlay.start();
        String response = gamePlay.move(roe);

        assertEquals("You've missed :(  Now John Doe is moving!", response);
        assertEquals("Now John Doe is moving, not you!", gamePlay.move(roe));
    }

    @Test
    void testWinTheGame() {
        gamePlay.register(doe.getName());
        gamePlay.start();

        assertEquals("John Doe is WINNER!", winTheGame());
    }

    String winTheGame() {
        gamePlay.move(doe);   // *e*
        doe.setLetter("d");
        assertEquals("The Game is already started!", gamePlay.start());
        gamePlay.move(doe);   // de*
        doe.setLetter("v");
        assertEquals("The Game is already started!", gamePlay.start());
        return gamePlay.move(doe);   // dev
    }
}