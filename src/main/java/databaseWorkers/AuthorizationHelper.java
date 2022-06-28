package databaseWorkers;

import classesandenums.User;
import exceptions.DatabaseHandlingException;
import org.apache.commons.codec.digest.DigestUtils;
import utility.Console;

import javax.jws.soap.SOAPBinding;
import java.sql.SQLException;
import java.util.Scanner;

public class AuthorizationHelper {

    private final Scanner scanner;
    private final DataBaseUserManager dataBaseUserManager;
    private User user = null;

    public AuthorizationHelper(Scanner scanner, DataBaseUserManager dataBaseUserManager) {
        this.scanner = scanner;
        this.dataBaseUserManager = dataBaseUserManager;
    }

    public String run(String username, String password, boolean register) {
//        User user = null;
//        boolean success = false;
//        while (!success) {
            try {
                if (register) {
//                    System.out.print("Введите логин: ");
//                    String login = scanner.nextLine();
                    try {
                        dataBaseUserManager.getUserByUsername(username);
                        return "notUniqueLogin";
                    } catch (SQLException e) {
//                        System.out.print("Введите пароль: ");
//                        String rawPassword = scanner.nextLine();
                        String codedPass = DigestUtils.shaHex(password);
                        user = new User(username, codedPass);
                        dataBaseUserManager.insertUser(user);
//                        System.out.println("Регистрация прошла успешно!");
//                        System.out.println("Авторизация прошла успешно!");
//                        success = true;
                        return null;
                    }
                } else {
//                    System.out.print("Ввведите логин: ");
//                    String login = scanner.nextLine();
//                    System.out.print("Введите пароль: ");
//                    String rawPassword = scanner.nextLine();
                    String codedPass = DigestUtils.shaHex(password);

                    user = new User(username, codedPass);
                    if (dataBaseUserManager.checkUserByUsernameAndPassword(user)) {
//                        System.out.println("Авторизация прошла успешно!");
//                        success = true;
                        return null;
                    } else {
                        return "wrongLoginOrPassword";
                    }
                }
            } catch (DatabaseHandlingException exception) {
                return "denied";
            }
//        }
    }

//    private boolean askAboutSign() {
//        while (true) {
//            System.out.println("Если у вас есть аккаунт, введите '1'. Если у вас нет аккаунта, введите '0'): ");
//            String answer = scanner.nextLine().trim();
//                        if (answer.equals("0")) {
//                return true;
//            } else if (answer.equals("1")) {
//                return false;
//            }
//            System.out.println("Ввод некорректен!");
//        }
//    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
