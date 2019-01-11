package letbo.interview.kruart.controller;

import letbo.interview.kruart.repository.UserRepository;
import letbo.interview.kruart.to.UserTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    private UserRepository repo;

    @PostMapping("/signin")
    public void signin(@RequestBody UserTo user) {
        repo.checkIfUserExist(user);
    }
}
