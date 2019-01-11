package letbo.interview.kruart.to;

import java.util.Objects;

public class UserTo {
    private String name;
    private String password;

    public UserTo() {
    }

    public UserTo(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserTo userTo = (UserTo) o;
        return Objects.equals(name, userTo.name) &&
                Objects.equals(password, userTo.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, password);
    }
}
