package de.teaclead.codechallenge.util;

import java.util.Random;
import de.teaclead.codechallenge.entity.User;
import de.teaclead.codechallenge.repo.UserRepository;

/**
 * Helper class to provide/generate/create childs
 */
public class UserProvider {

  public static User generateRandomUser() {
    User user = new User("tempMail@domain.com", generateRandomFirstname(), generateRandomSurname());
    user.setMailAddress(generateRandomMailAddress(user));
    return user;
  }

  public static User generateNotExistentUser(UserRepository userRepository) {
    User user = generateRandomUser();
    int i = 0;
    while (userRepository.existsById(user.getMailAddress())) {
      i++;
      user.setMailAddress(generateRandomMailAddress(user, i));
    }
    return user;
  }

  public static User generateAndCreateRandomUser(UserRepository userRepository) {
    User user = generateNotExistentUser(userRepository);
    userRepository.save(user);
    return user;
  }

  public static String generateRandomFirstname() {
    Random pick = new Random();
    String[] firstName = {"Bahira", "Sabine", "Christian", "Christin", "Florian", "Daniela",
        "Katharina", "Michaela", "Stefanie", "Ayusha", "Marie", "Meghan", "Ilja", "Dalal", "Lisa",
        "Alexander", "Sandra", "Stefan", "Filip", "Darja", "Anfisa", "Ben"};
    return firstName[pick.nextInt(firstName.length)];
  }

  public static String generateRandomSurname() {
    Random pick = new Random();
    String[] surname = {"Müller", "Schmidt", "Schneider", "Fischer", "Weber", "Meier", "Wagner",
        "Becker", "Yilmaz", "Kaya", "Demir", "Nowak", "Kowalska", "Wiśniewski"};
    return surname[pick.nextInt(surname.length)];
  }

  public static String generateRandomMailAddress(User user) {
    return String.format("%s.%s@%s", user.getFirstname(), user.getSurname(),
        generateRandomDomain().toLowerCase()).toLowerCase();
  }

  public static String generateRandomMailAddress(User user, int number) {
    return String.format("%s.%s.%s@%s", user.getFirstname(), user.getSurname(),
        Integer.toString(number), generateRandomDomain()).toLowerCase();
  }

  public static String generateRandomDomain() {
    Random pick = new Random();
    String[] domain = {"gmx.de", "web.de", "gmail.com", "yahoo.com"};
    return domain[pick.nextInt(domain.length)];
  }

}
