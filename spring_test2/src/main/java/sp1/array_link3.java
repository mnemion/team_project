package sp1;

import java.util.ArrayList;

//2차원 배열에 대한 응용편 ArrayList 필수
/*
  이름만 있고 결과대로 바꾸는 것
  김경민, 배유미, 김승균, 이철의, 장진호, 홍사라, 박병준, 전정호, 이경선, 최현제, 서강인, 염무원
  
  A조 김경민 배유미 김승균
  B조 이철의 장진호 홍사라
  C조 박병준 전정호 이경선
  D조 최현제 서강인 염무원
  
  [결과]
  [[A조, 김경민, 배유미, 김승균], [B조, 이철의, 장진호, 홍사라], [C조, 박병준, 전정호, 이경선], [D조, 최현제, 서강인, 염무원]]
*/
public class array_link3 {
	public static void main(String[] args) {
		new axxx().abc();
	}
}

class axxx {
	public void abc() {
		String[][] members = {
			    {"A조", "김경민", "배유미", "김승균"},
			    {"B조", "이철의", "장진호", "홍사라"},
			    {"C조", "박병준", "전정호", "이경선"},
			    {"D조", "최현제", "서강인", "염무원"}
		};
		ArrayList<ArrayList<String>> k = new ArrayList<ArrayList<String>>();
		
		for (int i = 0; i < members.length; i++) {
			ArrayList<String> group = new ArrayList<String>();
		    for (int j = 0; j < members[i].length; j++) {
		    	group.add(members[i][j]);
			}
		    k.add(group);
		}
		System.out.println(k);
	}
}