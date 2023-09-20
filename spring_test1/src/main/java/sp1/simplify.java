package sp1;

//값을 단순화 작업 또는 날짜, 암호화
//return으로 활용
public class simplify {
	
	//날짜 및 시간 단순화 작업
	public String day(String val) {
		val = val.replaceAll("T", " ");
		return val;
	}
}
