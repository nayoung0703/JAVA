package hotel;

import java.util.ArrayList;

import static hotel.User.*;

public class TestCode {
    static public void printRooms(Room[][] rooms){
        for(Room[] floor : rooms){
            for(Room room : floor){
                System.out.println(room.getRoomNumber());
            }
        }
    }
    static public void saveTest(){
        User user1 = new User("0", "tirea","1234", "albert");
        User user2 = new User("1", "joe", "1234", "john");
        ArrayList<User> users = new ArrayList<>();

        users.add(user1);
        users.add(user2);

        user1.changeUserData(users,"1", user ->{
            user.setUserName("sophia");
        });

        saveUserData(users);
    }// function saveText end

    static public void loadTest(){
        ArrayList<User> users = loadUserData();
        for(User user : users){
            System.out.println(user.toString());
        }
    }
}
