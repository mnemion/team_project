package sp1;

import java.util.ArrayList;

import lombok.*;

@Getter
@Setter
public class dto_user {
	String mno, mid, mpass, memail, mtel, marea, minter, mage, mdate;
	
	//Database => 1차원 배열 = return 2차원 배열로 전달 
	public ArrayList<String> db_data() {
		ArrayList<String> lists = new ArrayList<String>();
		lists.add(getMno());	//auto_incre
		lists.add(getMid());	//아이디
		lists.add(getMemail());	//이메일
		lists.add(getMtel());	//연락처
		lists.add(getMage());	//나이
		lists.add(getMdate());	//가입일자
		
		return lists;
	}
}
