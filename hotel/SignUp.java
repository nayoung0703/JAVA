package hotel;

import java.util.ArrayList;
import java.util.Scanner;

public class SignUp {

     static public User makeNewAccount(String id){

        String userId;
        String name;
        String pwd;
        String pwdCheck;

        Scanner sc = new Scanner(System.in);

        System.out.println("**************회원가입*************");
        System.out.print("사용하실 Id를 입력하세요 : ");
        userId =sc.nextLine();

        System.out.print("사용하실 비밀번호를 입력하세요 : ");
        pwd = sc.nextLine();
        System.out.print("비밀번호 확인 : ");
        pwdCheck = sc.nextLine();

        boolean isPwdValid = false;

        while(!isPwdValid){
            if(pwd.equals(pwdCheck)){
                System.out.println("비밀번호 일치");
                isPwdValid = true;
            }else{
                System.out.println("비밀번호가 일치하지 않습니다. 재입렵 하십시오");
                System.out.print("사용하실 비밀번호를 입력하세요 : ");
                pwd = sc.nextLine();
                System.out.print("비밀번호 확인 : ");
                pwdCheck = sc.nextLine();
            }
        }

        System.out.println("고객님의 이름을 알려주세요 : ");
        name = sc.nextLine();

        System.out.println(name+"고객님 ID : "+userId+" pwd : "+pwd+" 로 회원가입을 신청하셨습니다.");
        System.out.print("맞으시면 1번을 틀리시면 2번을 눌러주세요");

        String temp = sc.nextLine();
        if(temp.equals("1")){
            System.out.println("회원가입을 축하드립니다. 예약 창으로 이동됩니다.");
            //예약 창으로 이동 ... reservation 함수 호출

        }else if(temp.equals(("2"))){
            return makeNewAccount(id);
        }else{
            System.out.println("잘못된 입력입니다.");
            return makeNewAccount(id);
        }

        User user = new User(id, userId, pwd, name);
        ArrayList<User> users = User.loadUserData();
        users.add(user);
        User.saveUserData(users);

        System.out.println(user);


        return user;
    }
}//SignUp class END
