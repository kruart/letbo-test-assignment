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

    public void setWord(String word) {
        this.word = word;
    }

    public String getCurrentMove() {
        return currentMove;
    }

    public void setCurrentMove(String currentMove) {
        this.currentMove = currentMove;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }

    public List<String> getPlayerNames() {
        return playerNames;
    }

    public void setPlayerNames(List<String> playerNames) {
        this.playerNames = playerNames;
    }
}
