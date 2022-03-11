package de.teaclead.codechallenge.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import de.teaclead.codechallenge.entity.User;
import de.teaclead.codechallenge.repo.UserRepository;

@RestController
@RequestMapping(value = "user")
public class UserRestController {

  @Autowired
  private UserRepository userRepository;

  public UserRepository getUserRepository() {
    return userRepository;
  }

  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @GetMapping(value = "/", produces = MediaType.TEXT_PLAIN_VALUE)
  public String home() {

    return "Home page";
  }

  @RequestMapping(value = "", method = RequestMethod.POST)
  @ResponseStatus(HttpStatus.CREATED)
  public User post(@RequestBody User userDto) {
    this.userRepository.save(userDto);
    return userDto;
  }

  @RequestMapping(value = "", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public User get(@RequestParam(value = "userId", required = true) UUID userId) {
    Optional<User> optionalUser = this.userRepository.findById(userId);
    if (optionalUser.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    return optionalUser.get();
  }

  @RequestMapping(value = "", method = RequestMethod.DELETE)
  @ResponseStatus(HttpStatus.OK)
  public void delete(@RequestParam(value = "userId", required = true) UUID userId) {
    this.userRepository.deleteById(userId);
  }

  @RequestMapping(value = "", method = RequestMethod.PUT)
  @ResponseStatus(HttpStatus.OK)
  public User put(@RequestBody User userDto) {
    Optional<User> optionalUser = this.userRepository.findById(userDto.getUuid());
    if (optionalUser.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    this.userRepository.save(optionalUser.get());
    return optionalUser.get();
  }

  @RequestMapping(value = "/findByFirstName", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public List<User> getByFirstName(
      @RequestParam(value = "firstName", required = true) String firstName) {
    List<User> findByNames = this.userRepository.findByFirstName(firstName);
    return findByNames;
  }
}