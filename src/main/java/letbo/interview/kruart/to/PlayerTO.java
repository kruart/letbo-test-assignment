package letbo.interview.kruart.to;


public class PlayerTO {
    private String name;
    private String letter;

    public PlayerTO() {}

    public PlayerTO(String name, String letter) {
        this.name = name;
        this.letter = letter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }
}
