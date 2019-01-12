package letbo.interview.kruart.controller;

import letbo.interview.kruart.repository.UserRepository;
import letbo.interview.kruart.to.UserTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    @Autowired
    private UserRepository repo;

    @PostMapping("/signin")
    public ResponseEntity<String> signin(@RequestBody UserTo user) {
        logger.debug("calling '/signin' endpoint");
        repo.checkIfUserExist(user);
        return new ResponseEntity<>("Hello, " + user.getName(), HttpStatus.OK);
    }
}
