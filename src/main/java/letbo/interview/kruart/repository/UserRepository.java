package letbo.interview.kruart.repository;

import letbo.interview.kruart.to.UserTo;
import letbo.interview.kruart.util.exception.UserNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    private List<UserTo> users = new ArrayList<>();

    {
        users.add(new UserTo("John Doe", "qwerty"));
        users.add(new UserTo("Richard Roe", "qwerty"));
    }


    public void checkIfUserExist(UserTo user) {
        if (!users.contains(user)) {
            throw new UserNotFoundException("Bad Credentials!");
        }
    }
}
