package letbo.interview.kruart.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {
    private List<String> players;
    private Status status;
    private Word word;

    public Game() {
        this.players = Collections.synchronizedList(new ArrayList<>());
        this.status = Status.NOT_STARTED;
    }

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayer(String player) {
        this.players.add(player);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Word getWord() {
        return word;
    }

    public void createWord(String w, String mask) {
        this.word = new Word(w, mask);
    }
}
