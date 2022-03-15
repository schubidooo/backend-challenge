package de.teaclead.codechallenge.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import de.teaclead.codechallenge.entity.User;
import de.teaclead.codechallenge.repo.UserRepository;
import de.teaclead.codechallenge.util.UserProvider;

@ActiveProfiles(profiles = "dev")
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
class UserControllerTest {
  protected ObjectMapper objectMapper = new ObjectMapper();
  ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
  @Autowired
  protected UserRepository userRepository;

  protected MockMvc mockMvc;

  @BeforeEach
  public void setUp(WebApplicationContext webApplicationContext,
      RestDocumentationContextProvider restDocumentation) {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        .apply(documentationConfiguration(restDocumentation)).alwaysDo(document("{method-name}",
            preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
        .build();
    userRepository.deleteAll();
  }

  @Test
  public void shouldReturnUsersByFirstname() throws Exception {
    String firstnameToFind = "Homer";
    User user1 = UserProvider.generateNotExistentUser(userRepository);
    User user2 = UserProvider.generateNotExistentUser(userRepository);
    user1.setFirstname(firstnameToFind);
    user2.setFirstname(firstnameToFind);
    this.mockMvc.perform(
        post("/user").contentType(MediaType.APPLICATION_JSON).content(ow.writeValueAsString(user1)))
        .andExpect(status().isCreated());
    this.mockMvc.perform(
        post("/user").contentType(MediaType.APPLICATION_JSON).content(ow.writeValueAsString(user2)))
        .andExpect(status().isCreated());
    this.mockMvc.perform(get("/user/filter?firstname={firstname}", firstnameToFind))
        .andExpect(status().isOk());
  }

  @Test
  public void shouldReturnUser() throws Exception {
    User user = UserProvider.generateAndCreateRandomUser(userRepository);
    this.mockMvc.perform(get("/user?mailAddress={mailAddress}", user.getMailAddress()))
        .andExpect(status().isOk());
  }

  @Test
  public void shouldReturnUserNotFound() throws Exception {
    String doesNotExistMail = "mailDoesNotExist@unknown.com";
    this.mockMvc.perform(get("/user?mailAddress={mailAddress}", doesNotExistMail))
        .andExpect(status().isNotFound());
  }

  @Test
  public void shouldCreateUser() throws Exception {
    User user = UserProvider.generateNotExistentUser(userRepository);
    String requestJson = ow.writeValueAsString(user);

    this.mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(requestJson))
        .andExpect(status().isCreated());
  }

  @Test
  public void shouldUpdateUser() throws Exception {
    User user = UserProvider.generateAndCreateRandomUser(userRepository);
    user.setFirstname("Gargamel");
    String requestJson = ow.writeValueAsString(user);
    this.mockMvc.perform(put("/user").contentType(MediaType.APPLICATION_JSON).content(requestJson))
        .andExpect(status().isOk());
  }

  @Test
  public void shouldReturnUserNotFoundOnUpdate() throws Exception {
    User user = UserProvider.generateNotExistentUser(userRepository);
    user.setMailAddress("mailDoesNotExist@unknown.com");
    String requestJson = ow.writeValueAsString(user);
    this.mockMvc.perform(put("/user").contentType(MediaType.APPLICATION_JSON).content(requestJson))
        .andExpect(status().isNotFound());
  }

  @Test
  public void shouldDeleteUser() throws Exception {
    User user = UserProvider.generateAndCreateRandomUser(userRepository);
    this.mockMvc.perform(delete("/user?mailAddress=" + user.getMailAddress()))
        .andExpect(status().isOk());
  }

}
