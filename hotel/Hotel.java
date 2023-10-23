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




public class Hotel {
    private Room[][] rooms;
    private Scanner sc;
    private Date today;

    public Room[][] getRooms() {
		return rooms;
	}



	public void setRooms(Room[][] rooms) {
		this.rooms = rooms;
	}



	public Scanner getSc() {
		return sc;
	}



	public void setSc(Scanner sc) {
		this.sc = sc;
	}



	public Date getToday() {
		return today;
	}



	public void setToday(Date today) {
		this.today = today;
	}

	static String hotelFileName = "src/net/hb/work/hotel/data/hotelData.json";

    public Hotel() {
        rooms = null;
        sc = new Scanner(System.in);
        today = new Date();
    }



    public void generateHotelRoom(){
        int totalFloors = 0;
        boolean validInput = false;
        String roomNumber;

        while (!validInput) {
            System.out.print("몇 층의 방을 만들까요? : ");
            try {
                totalFloors = sc.nextInt();
                validInput = true; // Set validInput to true if an integer is entered
            } catch (InputMismatchException e) {
                System.out.println("잘못된 입력입니다. 정수를 입력해주세요.");
                sc.nextLine(); // Consume the invalid input
            }
        }
        sc.nextLine(); // Consume leftover \n from nextInt()

        rooms = new Room[totalFloors][];
        for (int i = 0; i < totalFloors; i++) {
            System.out.print(i + 1 + "층에 몇 개의 방을 만들까요? : ");
            int totalRooms;
            try {
                totalRooms = sc.nextInt();

            } catch (InputMismatchException e) {
                System.out.println("잘못된 입력입니다. 정수를 입력해주세요.");
                sc.nextLine(); // Consume leftover \n from nextInt()
                generateHotelRoom();
                return;
            }
            sc.nextLine(); // delete leftover \n from nextInt()

            rooms[i] = new Room[totalRooms];
            for (int j = 0; j < totalRooms; j++) {
                roomNumber = (i+1)+"0"+(j+1);
                rooms[i][j] = new Room(roomNumber);
            }
        }
    }//function generateHotelRoom end



    public void printListOfRooms(Room[][] rooms){
        System.out.println("전체 예약 상황 확인");

        if (rooms == null) {
            System.out.println("방을 먼저 생성해야 합니다.");
            return;
        }

        for (int i = 0; i < rooms.length; i++) {
            for (int j = 0; j < rooms[i].length; j++) {
                String roomNumber = String.format("%d%02d", i + 1, j + 1);
                System.out.print(roomNumber + "\t\t\t\t\t");
            }
            System.out.println();

            for (int j = 0; j < rooms[i].length; j++) {
                if (rooms[i][j].isReserved()) {
                    //load users
                    ArrayList<User> loadedUsersData = loadUserData();
                    String userId = rooms[i][j].getUserId();
                    System.out.print(findUserWithId(loadedUsersData,userId).getUserName());
                }
                System.out.print("\t\t\t\t\t");
            }
            System.out.println();
        }

    }//function printListOfRooms end

    public void printSelectedUserRoomList(User user){
        System.out.println(user.getUserName() + "님 예약상황 확인");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (Room room : user.getUserReservedRooms()) {
            String roomNumber = room.getRoomNumber();
            Date startDate = room.getReservedDates().get(0);
            Date endDate = room.getReservedDates().get(room.getReservedDates().size() - 1);
            String startDateStr = dateFormat.format(startDate);
            String endDateStr = dateFormat.format(endDate);
            System.out.println(roomNumber + " : " + startDateStr + " ~ " + endDateStr);
        }
    }//function printSelectedUserRoomList end

    public void updateRooms(){

    }
    public void makeReservation(User user, ArrayList<User> users) {
        Scanner scanner = new Scanner(System.in);

        // Display available rooms
        this.printListOfRooms(this.getRooms());

        // Get user input for room selection
        System.out.print("예약할 방 번호를 입력하세요: ");
        String roomNumber = scanner.nextLine();

        // Find the selected room in the hotel
        Room selectedRoom = findRoomByNumber(this.getRooms(), roomNumber);

        if (selectedRoom == null) {
            System.out.println("해당 방 번호가 유효하지 않습니다.");
            return;
        }

        if (selectedRoom.isReserved()) {
            System.out.println("이미 예약된 방입니다.");
            return;
        }

        // Update room reservation status and user information
        selectedRoom.setReserved(true);
        selectedRoom.setUserId(user.getUserId());

        // Get user input for start and end dates
        Date today = this.getToday(); // Get current date from the hotel
        Date startDate = null;
        Date endDate = null;
        boolean validDates = false;

        while (!validDates) {
            System.out.print("예약 시작 날짜를 입력하세요 (YYYY-MM-DD): ");
            String startDateString = scanner.nextLine();
            System.out.print("예약 종료 날짜를 입력하세요 (YYYY-MM-DD): ");
            String endDateString = scanner.nextLine();

            // Convert start and end date strings to Date objects
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                startDate = dateFormat.parse(startDateString);
                endDate = dateFormat.parse(endDateString);

                // Compare start date with today's date
                if (startDate.before(today)) {
                    System.out.println("시작 날짜가 오늘보다 이전입니다. 다시 입력하세요.");
                    continue;
                }

                validDates = true; // Dates are valid, exit the loop
            } catch (ParseException e) {
                System.out.println("잘못된 날짜 형식입니다. 다시 입력하세요.");
            }
        }

        // Update user's reservation information
        user.addReservation(selectedRoom, startDate, endDate);

        //update users
        updateUsers(users, user);

        // Save updated hotel and user data
        this.saveHotelData(this);
        saveUserData(users);

        System.out.println("예약이 완료되었습니다.");
    }//function makeReservation end

    public void cancelReservation(User user, ArrayList<User> users){
        Scanner scanner = new Scanner(System.in);
        System.out.println(user.getUserName()+"님 취소하실 방을 골라주세요");

        //show user reserved room
        printSelectedUserRoomList(user);

        boolean isInputValid = false;
        String selectedRoomNumber ="";

        while(!isInputValid) {
            selectedRoomNumber = scanner.nextLine();
            for (Room room : user.getUserReservedRooms()) {
                if (room.getRoomNumber().equals(selectedRoomNumber)) {
                    isInputValid = true;
                    break;
                }
            }
            if(!isInputValid) {
                System.out.println("예약한 방번호가 아닙니다.");
                System.out.println("예약취소를 취소하시려면 9를 입력하세요, 계속하시려면 취소하려는 방번호를 입력해주세요.");
                if (scanner.nextLine().equals("9")) {
                    return;
                }
            }
        }

        // Find the selected room in the hotel
        Room selectedRoom = Room.findRoomByNumber(this.getRooms(), selectedRoomNumber);

        // remove room from Room[][]
        assert selectedRoom != null;
        selectedRoom.setReserved(false);
        selectedRoom.setUserId(null);
        selectedRoom.setReservedDates(null);


        // Update user's reservation information
        user.removeReservation(selectedRoom);

        //update users
        updateUsers(users, user);

        // Save updated hotel and user data
        this.saveHotelData(this);
        saveUserData(users);

        System.out.println("예약이 취소되었습니다.");

    }//function cancelReservation end



    public void saveHotelData(Hotel hotel) {
        try (FileWriter writer = new FileWriter(hotelFileName)) {
            final Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Scanner.class, new ScannerTypeAdapter())
                    .setPrettyPrinting()
                    .create();
            gson.toJson(hotel, writer);
            System.out.println("호텔 데이터가 저장되었습니다.");
        } catch (IOException e) {
            System.out.println("호텔 데이터를 저장할 수 없습니다.");
        }
    }

    public static Hotel loadHotelData() {
        File file = new File(hotelFileName);
        if (!file.exists()) {
            System.out.println("호텔 데이터 파일이 존재하지 않습니다. 새로운 파일을 생성합니다.");
            return new Hotel();
        }
        try (FileReader reader = new FileReader(file)) {
            final Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Scanner.class, new ScannerTypeAdapter())
                    .setPrettyPrinting()
                    .create();
            return gson.fromJson(reader, Hotel.class);
        } catch (IOException e) {
            System.out.println("호텔 데이터를 로드할 수 없습니다.");
        }
        return null;
    }


}




