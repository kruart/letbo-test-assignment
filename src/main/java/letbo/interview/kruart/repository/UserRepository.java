package letbo.interview.kruart.repository;

import letbo.interview.kruart.to.UserTo;
import letbo.interview.kruart.util.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    private final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    private List<UserTo> users = new ArrayList<>();

    {
        users.add(new UserTo("John Doe", "qwerty"));
        users.add(new UserTo("Richard Roe", "qwerty"));
    }


    public void checkIfUserExist(UserTo user) {
        logger.debug("check if user[{}] exist in database", user);
        if (!users.contains(user)) {
            throw new UserNotFoundException("Bad Credentials!");
        }
    }
}
