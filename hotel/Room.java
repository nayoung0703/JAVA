package hotel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class Room {

    public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getReservedDay() {
		return reservedDay;
	}

	public void setReservedDay(int reservedDay) {
		this.reservedDay = reservedDay;
	}

	public List<Date> getReservedDates() {
		return reservedDates;
	}

	public void setReservedDates(List<Date> reservedDates) {
		this.reservedDates = reservedDates;
	}

	public void setReserved(boolean reserved) {
		this.reserved = reserved;
	}


	private String roomNumber; //복수의 방 예약시 체크
    private String userId;
    private String roomType;
    private boolean reserved;
    private int price;
    private int reservedDay;
    private List<Date> reservedDates; // List to store reserved dates

    public Room(String roomNumber) {
        this.roomNumber = roomNumber;
        this.reserved = false;
        this.reservedDates = new ArrayList<>();
    }

    //getter and setter auto generate by annotation

    public boolean equals(Object obj) {   //Override of equals for remove(Object o)
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Room otherRoom = (Room) obj;
        return Objects.equals(this.roomNumber, otherRoom.roomNumber);
    }

    public static Room findRoomByNumber(Room[][] rooms, String roomNumber) {
        for (Room[] room : rooms) {
            for (Room value : room) {
                if (value.getRoomNumber().equals(roomNumber)) {
                    return value;
                }
            }
        }
        return null; // Room not found
    }//function findRoomByNumber end

    public void addReservedDateRange(Room room, Date startDate, Date endDate) {
        long startMillis = startDate.getTime();
        long endMillis = endDate.getTime();

        for (long millis = startMillis; millis <= endMillis; millis += 24 * 60 * 60 * 1000) {
            Date date = new Date(millis);
            room.addReservedDate(date);
        }
    }

    public void addReservedDate(Date date) {
        reservedDates.add(date);
    }

    public void removeReservedDate(Date date) {
        reservedDates.remove(date);
    }


    public boolean isReserved(){
        return reserved;
    }


}//Room class END
