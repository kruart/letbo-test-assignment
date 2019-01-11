package letbo.interview.kruart.util.exception;

import org.springframework.lang.NonNull;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(@NonNull String msg) {
        super(msg);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ": " + getMessage();
    }
}
