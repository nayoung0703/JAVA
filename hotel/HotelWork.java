package hotel;


import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import static hotel.Room.findRoomByNumber;
import static hotel.User.*;

public class HotelWork {
    String headMessage="";
    Boolean isLogin = false;
    public static void main(String[] args) {
        //see your working dir
//        String workingDir = System.getProperty("user.dir");
//        System.out.println("Working Directory: " + workingDir);


        //generate new hotelWork
        HotelWork hotelWork = new HotelWork();
        //check if hotel present and load data
        Hotel loadedHotel = Hotel.loadHotelData();
        if (loadedHotel != null) {
            hotelWork.run(loadedHotel);
        } else {
            // Handle the case where the hotel data couldn't be loaded
            System.out.println("Failed to load hotel data. Creating a new hotel.");
            Hotel newHotel = new Hotel();
            hotelWork.run(newHotel);
        }
    }

    public void run(Hotel hotel) {
        Scanner sc = new Scanner(System.in);
        // check if room is not loaded
        if (hotel.getRooms() == null) {
            hotel.generateHotelRoom();
        }
        ArrayList<User> users = User.loadUserData();
        int choice = 0;


        // Display login or signup options
        System.out.println("**************호텔 예약 시스템*************");

        boolean running = true;
        while (running) {
            System.out.println("1. 로그인");
            System.out.println("2. 회원가입");
            System.out.println("3. 예약상황 확인");
            System.out.println("9. 종료");
            System.out.print("메뉴 선택: ");

            boolean isInputChoiceValid = false;
            while(!isInputChoiceValid) {
                try {
                    choice = sc.nextInt();
                    isInputChoiceValid = true;
                } catch (InputMismatchException e) {
                    System.out.println("메뉴중에 골라주세요 : ");
                    sc.nextLine();
                }
            }
            sc.nextLine(); // Consume the newline character

            switch (choice) {
                case 1 -> {
                    User user = Login.loginUser();
                    if (user != null) {
                        performActions(hotel, user);
                    }
                }
                case 2 -> {
                    users = User.loadUserData();
                    String newUserId = Integer.toString(users.size()); //size() 는  index +1 때문에 그대로 받으면됨
                    User newUser = SignUp.makeNewAccount(newUserId);
                    performActions(hotel, newUser);
                }
                case 3->{
                    hotel.printListOfRooms(hotel.getRooms());
                }
                case 9 -> {
                    System.out.println("프로그램을 종료합니다.");
                    running = false;
                }
                default -> System.out.println("잘못된 선택입니다. 다시 선택하세요.");
            }
        }
    }

    public void performActions(Hotel hotel, User user) {
        Scanner scanner = new Scanner(System.in);
        isLogin = true;
        // Perform actions for the logged-in user
        System.out.println("사용자 " + user.getUserName() + "로 로그인되었습니다.");
        headMessage = user.getUserName() + "님 환영합니다";

        while (isLogin) {
            System.out.println(headMessage);
            System.out.println("**************메뉴 선택*************");
            System.out.println("1. 로그아웃");
            System.out.println("2. 예약하기");
            System.out.println("3. 예약 취소하기");
            System.out.println("4. 예약 내역 보기");
            System.out.println("5. 개인 정보 수정");
            System.out.println("6. 비밀번호 변경");
            System.out.println("7. 회원 탈퇴");


            System.out.print("메뉴 선택: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> {
                    System.out.println("로그아웃 되었습니다.");
                    isLogin = false;
                }
                case 2 -> {
                    ArrayList < User > users = loadUserData();
                    hotel.makeReservation(user, users);
                }
                case 3 -> {
                    // 예약 취소하기 로직
                    ArrayList < User > users = loadUserData();
                    hotel.cancelReservation(user, users);
                }
                case 4 -> {
                    hotel.printSelectedUserRoomList(user);
                }
                case 5-> {
                    // 개인 정보 수정 로직
                    ArrayList < User > users = loadUserData();
                    user.changeUserData(users, user.getUserId(), UserUtils::changeName);
                }
                case 6 -> {
                    // 비밀번호 변경 로직
                    ArrayList < User > users = loadUserData();
                    user.changeUserData(users, user.getUserId(), UserUtils::changePassword);
                }
                case 7 -> {
                    // 회원 탈퇴 로직
                    ArrayList < User > users = loadUserData();
                    System.out.println("탈퇴하시겠습니까? 이 결정은 번복할 수 없습니다.");
                    System.out.print("정말 탈퇴하시려면 1번을 취소하시려면 아무키나 입력하세요 : ");
                    int tempInput = Integer.parseInt(scanner.nextLine());
                    if(tempInput==1){
                        System.out.println("정상 탈퇴 처리 되었습니다.");
                        users.remove(user);
                        saveUserData(users);
                        isLogin = false;
                    }
                }
                default -> {
                    System.out.println("잘못된 선택입니다. 다시 선택하세요.");
                }
            }
        }

    }// function performAction end
}//HotelWork class END



