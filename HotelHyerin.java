/*
 * 작성자: 이혜린
 * 작성 언어: JAVA
 * 최종 수정 일자: 2023. 06. 11
 * */

package net.hb.day09;
import java.util.Scanner;

public class HotelHyerin {
	private int num; //메뉴 선택
	private Scanner sc=new Scanner(System.in);
	private int floor; //층 1~3
	private int room; //호 층별 5개의 방
	private int persN; //예약 인원
	private String name; private String[][] roomName = new String[3][5]; //예약자명
	private String pwd; private String[][] rePwd = new String[3][5]; //비밀번호
	
	public HotelHyerin() {}
	
	public void intro() {
    	System.out.println("🍷 부다페스트 호텔 예약 시스템입니다 🍷\n");
    	list();
    	
    	while(true) {
    		System.out.println("①예약 ②퇴실 ③예약현황 ⑨종료\n");
    		System.out.print("원하시는 서비스 번호를 입력해 주세요 >> ");
    		num=Integer.parseInt(sc.nextLine());
    		System.out.println();
    		if(num==9) {
    			System.out.println("예약 프로그램을 종료합니다\n");break;
    		}
    		switch(num) {
    			case 1: reserv(); list();break;
    			case 2: cancel(); list();break;
    			case 3: list();break;
    			default: System.out.println("해당하는 메뉴가 존재하지 않습니다\n");
    		}
    	}
	}
	
	public void reserv() {
		System.out.print("예약하고자 하는 층수를 입력해 주세요 >> ");
		floor=Integer.parseInt(sc.nextLine());
		System.out.print("예약하고자 하는 호실을 입력해 주세요 >> ");
		room=Integer.parseInt(sc.nextLine());
		System.out.print("예약자 명을 입력해 주세요 >> ");
		name=sc.nextLine();
		System.out.print("예약할 인원 수를 입력해 주세요 >> ");
		persN=Integer.parseInt(sc.nextLine());
		System.out.print("예약 확인 및 취소를 위한 비밀번호를 입력해 주세요 >> ");
		pwd=sc.nextLine();
		System.out.println();
		
		if(roomName[floor-1][room-1] == "") {
			if(persN>3) {System.out.println("한 방당 최대 두 사람까지 예약이 가능합니다");}
			else if(persN<=2&&persN>0) {
				System.out.println(name+"님 "+floor+"0"+room+"번째 방 예약이 성공했습니다\n");
				roomName[floor-1][room-1]=name;
				rePwd[floor-1][room-1]=pwd;
			}
		} else if(roomName[floor-1][room-1] != "") {
			System.out.println(floor+"0"+room+"번째 방은 이미 예약된 방입니다\n");
		}
	}

	public void cancel() {
		System.out.print("퇴실하고자 하는 층수를 입력해 주세요 >> ");
		floor=Integer.parseInt(sc.nextLine());
		System.out.print("퇴실하고자 하는 호실을 입력해 주세요 >> ");
		room=Integer.parseInt(sc.nextLine());
		System.out.print("예약자 명을 입력해 주세요 >> ");
		name=sc.nextLine();
		System.out.print("퇴실 확인을 위한 비밀번호를 입력해 주세요 >> ");
		pwd=sc.nextLine();
		System.out.println();
		
		if(roomName[floor-1][room-1] != ""){
			if(roomName[floor-1][room-1].equals(name)&&rePwd[floor-1][room-1].equals(pwd)) {
				System.out.println("본인 확인이 완료되었습니다 \n"+ name+"님 "+floor+"0"+room+"번째 방 "+ persN + "명 퇴실이 성공했습니다\n");
				roomName[floor-1][room-1] = "";
				rePwd[floor-1][room-1] = "";
			}
			else if(!rePwd[floor-1][room-1].equals(pwd)){
				System.out.println("비밀번호가 일치하지 않습니다");
			}
			else if(!roomName[floor-1][room-1].equals(name)){
				System.out.println("예약자 정보가 일치하지 않습니다");
			}
		}
		else if(roomName[floor-1][room-1] == "") {
			System.out.println(floor+"0"+room+"번째 방은 예약되지 않은 방입니다\n");
		}
	}
	
	public void list() {
		System.out.println("\t\t\t\t\t예약 현황\n");
		
		for(int i=0; i<3; i++) {
			for(int j=0; j<5; j++) {
				if(roomName[i][j]==null) {
					roomName[i][j]="";
				}
				if(roomName[i][j]=="") {
					System.out.print("\t□ "+(i+1)+"0"+(j+1)+"(0인)");
				}else if(roomName[i][j]!="") {
					System.out.print("\t■ "+(i+1)+"0"+(j+1)+"("+ persN+"인)");
				}
			} System.out.println();
			for(int j=0; j<5; j++) {
				System.out.print("\t "+roomName[i][j]+"\t");
			} 
			System.out.println("\n");
		}
		System.out.println();
	}
	
}//class end