package hotel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;


public class User {
    private String id;
    private String userId;
    private String pwd;
    private String userName;
    private List<Room> userReservedRooms;

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<Room> getUserReservedRooms() {
		return userReservedRooms;
	}

	public void setUserReservedRooms(List<Room> userReservedRooms) {
		this.userReservedRooms = userReservedRooms;
	}

	static String userFileName = "src/net/hb/work/hotel/data/userData.json";

    public User(String id, String userId, String pwd, String userName) {
        this.id = id;
        this.userId = userId;
        this.pwd = pwd;
        this.userName = userName;
        this.userReservedRooms = new ArrayList<Room>();
    }
	

    public User(String id, String pwd, String userName, ArrayList<Room> userReservedRooms) {
        this.id = id;
        this.pwd = pwd;
        this.userName = userName;
        this.userReservedRooms = userReservedRooms;
    }
    public User(String id, String pwd, String userName) {
        this.id = id;
        this.pwd = pwd;
        this.userName = userName;
        this.userReservedRooms = new ArrayList<Room>();
    }

    public User(String id, String pwd) {
        this.id = id;
        this.pwd = pwd;
    }



	public boolean equals(Object obj) {   //Override of equals for remove(Object o)
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        User otherUser = (User) obj;
        return Objects.equals(this.userId, otherUser.userId);
    }

    public void addReservation(Room room, Date startDate, Date endDate) {
        room.addReservedDateRange(room, startDate, endDate);
        room.setReserved(true);
        userReservedRooms.add(room);
    }


    public void removeReservation(Room room) {
        if (userReservedRooms.contains(room)) {
            userReservedRooms.remove(room);
            room.setReserved(false);
            room.setUserId(null);
            room.setReservedDates(new ArrayList<>());
        }
    }

    public static User findUserWithId(ArrayList<User> users, String userId){
        User foundUser = null;
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                foundUser = user;
                break;
            }
        }
        return foundUser;
    }

    public static void updateUsers(ArrayList<User> users, User updatedUser) {
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getUserId().equals(updatedUser.getUserId())) {
                // Update the user in the list
                users.set(i, updatedUser);
                return;
            }
        }
    }

    public  void changeUserData(ArrayList<User> users, String userId, Consumer<User> function) {
        //change userdata with function
        User foundUser = findUserWithId(users, userId);

        if (foundUser != null) {
            function.accept(foundUser); // Invoke the function on the user object
        } else {
            System.out.println("User not found");
        }
        updateUsers(users, foundUser);
        saveUserData(users);
    }// function changeUserData end


    public static void saveUserData(ArrayList<User> users) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(users);

        try {
            FileWriter file = new FileWriter(userFileName); //"src/net/hb/work/hotel/data/userData.json";
            file.write(json);
            file.flush();
            file.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }// function saveUserData end

    public static ArrayList<User> loadUserData() {
        ArrayList<User> users = new ArrayList<>();
        File file = new File(userFileName);
        if (!file.exists()) {
            // File does not exist, return an empty list
            return users;
        }
        
        try {
            FileReader reader = new FileReader(file);
            Gson gson = new Gson();
            users = gson.fromJson(reader, new TypeToken<ArrayList<User>>() {
            }.getType());
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return users;
    }// function loadUserData end

}//class END
