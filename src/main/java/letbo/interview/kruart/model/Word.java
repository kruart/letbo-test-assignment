package letbo.interview.kruart.model;

public class Word {
    private char[] letters;
    private volatile char[] hiddenLetters;
    private String mask;

    public Word(String word, String mask) {
        this.letters = word.toCharArray();
        this.mask = mask;
        initializeHiddenWord();
    }

    public char[] getLetters() {
        return letters;
    }

    public void setLetters(String letters) {
        this.letters = letters.toCharArray();
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public void setLetters(char[] letters) {
        this.letters = letters;
    }

    public char[] getHiddenLetters() {
        return hiddenLetters;
    }

    public void setHiddenLetters(char[] hiddenLetters) {
        this.hiddenLetters = hiddenLetters;
    }

    private void initializeHiddenWord() {
        this.hiddenLetters = mask.repeat(letters.length).toCharArray();
    }
}
