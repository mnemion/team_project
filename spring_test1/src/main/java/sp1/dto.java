package sp1;

import java.util.ArrayList;

import lombok.*;

//dto (setter, getter)
@Setter
@Getter

//setter, getter 중에서 getter값을 로드하여 1차원 배열로 데이터를 삽입
public class dto {
	String userid = null;
	String email = null;
	
	public ArrayList<String> database(){
		ArrayList<String> as = new ArrayList<String>();
		as.add(getUserid());
		as.add(getEmail());
		return as;
	}
}
