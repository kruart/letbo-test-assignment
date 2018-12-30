package letbo.interview.kruart.to;

import java.util.List;

public class GameInfoTo {
    private String word;
    private String currentMove;
    private String gameStatus;
    private String message;
    private List<String> playerNames;
    private String winner;

    public GameInfoTo() {}

    public GameInfoTo(String word, String currentMove, String gameStatus, String message, List<String> playerNames, String winner) {
        this.word = word;
        this.currentMove = currentMove;
        this.gameStatus = gameStatus;
        this.message = message;
        this.playerNames = playerNames;
        this.winner = winner;
    }

    public String getWord() {
        return word;
    }

    public String getCurrentMove() {
        return currentMove;
    }

    public String getMessage() {
        return message;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public List<String> getPlayerNames() {
        return playerNames;
    }

    public String getWinner() {
        return winner;
    }
}
