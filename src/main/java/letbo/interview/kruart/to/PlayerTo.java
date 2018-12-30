package letbo.interview.kruart.to;

import letbo.interview.kruart.util.Check;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

public class PlayerTo {
    // Check.OnRegister.class, when we register, we need validate only name without letter
    @NotNull(groups = {Check.OnRegister.class, Default.class}, message = "The name cannot be null")
    @Size(groups = {Check.OnRegister.class, Default.class}, min = 3, max = 15, message = "The name must be from 3 to 15 characters")
    private String name;

    @NotNull(message = "The letter cannot be null")
    @Pattern(regexp = "^[\\p{Alpha}]$", message = "The letter must be strictly 1 character")
    // @Size(min = 1, max = 1, message = "The letter must be strictly 1 character")
    private String letter;

    public PlayerTo() {}

    public PlayerTo(String name, String letter) {
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
