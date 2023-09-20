package sp1;

import java.util.*;

//List : ArrayList, LinkedList, Vector, Stack
public class array_link2 {
	public static void main(String[] args) {
		String a[] = {"A", "B", "C"};
		List<String> z = Arrays.asList(a); 	//추가 안됨
		System.out.println(z);
		List<String> zz = new ArrayList<String>(Arrays.asList(a));
		zz.add("D");
		zz.add(4, "J");		//추가
		zz.set(2, "H");		//해당 데이터를 변경
		System.out.println(zz);
		
		//Vector : 쓰레드를 활용함 / 배열에 대한 안정성이 확보됨 LinkedList 빠름, ArrayList 늘림
		List<String> zzz = new Vector<String>(Arrays.asList(a));
		zzz.add("10");
		zzz.set(0, "AAA");
		System.out.println(zzz);
		
		//자료형이 없는 경우 
		List k = new ArrayList<String>(Arrays.asList(a));
		k.add(110);
		k.add("홍길동");
		System.out.println(k);
		
		//자료형이 없으니 오브젝트
		ArrayList<?> al = new ArrayList<Object>(Arrays.asList(a));
		System.out.println(al);
		
		new datalist().abc();
	}
}

class datalist {
	public void abc() {
		String member[][] = {
				{"홍길동", "SKT", "45"},
				{"이순신", "LGT", "55"},
				{"강감찬", "KT", "25"}
		};
		
		//String[], Integer[] : List 모든 배열을 2차 배열형태로 구성함
		ArrayList<String[]> al = new ArrayList<String[]>(Arrays.asList(member));
		System.out.println(al);
		
		int w = 0;
		while(w < al.size()) {
			System.out.println(al.get(w)[0]);
			w++;
		}
		ArrayList<Integer>[] ai = new ArrayList[100];
		int ww = 0;
		while(ww < 10) {
			ai[ww] = new ArrayList<Integer>();
			ww++;
		}
		ai[0].add(10);
		ai[1].add(20);
		ai[2].add(30);
		ai[3].add(40);
		//System.out.println(Arrays.toString(ai));
		//System.out.println(ai[0].get(0));
		
		//2차원 배열 ArrayList
		//2차원 빈 배열값 생성
		ArrayList<ArrayList<String>> k = new ArrayList<ArrayList<String>>();
		//System.out.println(k);
		//System.out.println(Arrays.deepToString(k.toArray()));
		
		//2차원 배열에 그룹으로 값을 삽입하기 위한 1차원 빈 배열 생성
		ArrayList<String> kk = new ArrayList<String>();
		kk.add("홍길동");
		kk.add("25");
		kk.add("hong@nate.com");
		
		k.add(kk);	//2차원 배열에 해당 값을 삽입
		
		//새로운 1차원 배열을 생성 후 데이터 입력
		ArrayList<String> kk2 = new ArrayList<String>();
		kk2.add("이순신");
		kk2.add("36");
		kk2.add("lee@nate.com");
		//새로운 데이터를 2차원 배열로 갑쇼을 삽입
		k.add(kk2);
		//출력
		System.out.println(k);
		//System.out.println(k.get(0).get(2));	//2차원 배열 중 그룹, index 숫자
		//k.remove(1);
		//System.out.print(k);
		//k.remove(k.get(1).remove(2));
		k.get(1).remove(2);		//2차원 배열 중 해당 그룹에서 데이터를 삭제하는 방식
		//데이터 추가
		k.get(1).add("lee@gmail.com");
		System.out.println(k);
	}
}