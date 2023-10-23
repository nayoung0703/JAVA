package hotel;

import java.util.Scanner;

public class UserUtils {

    public static void changePassword(User user) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the new password: ");
        String newPassword = scanner.nextLine();
        user.setPwd(newPassword);
    }

    public static void changeName(User user) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the new name: ");
        String newName = scanner.nextLine();
        user.setUserName(newName);
    }
}
