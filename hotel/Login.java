package hotel;

import java.util.ArrayList;
import java.util.Scanner;

public class Login {
    public static User loginUser() {
        String userId;
        String password;

        Scanner sc = new Scanner(System.in);

        System.out.println("**************로그인*************");
        System.out.print("아이디를 입력하세요: ");
        userId = sc.nextLine();

        System.out.print("비밀번호를 입력하세요: ");
        password = sc.nextLine();

        // Perform login validation
        // You can check the user credentials against your data store or any other validation logic

        // Assuming you have a list of users loaded from data store
        ArrayList<User> users = User.loadUserData();

        for (User user : users) {
            if (user.getUserId().equals(userId) && user.getPwd().equals(password)) {
                System.out.println("로그인에 성공했습니다.");
                return user;
            }
        }

        System.out.println("로그인에 실패했습니다. 다시 시도하세요.");
        return null;
    }
}//Login class END
