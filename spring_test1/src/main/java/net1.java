import java.net.InetAddress;
//네트워크 기초
public class net1 {

	public static void main(String[] args) throws Exception {
		//InetAddress : IPnetwordAddress의 줄임말
		//getByName : 접속할 도메인 주소명을 말함
		/*
		InetAddress ia = InetAddress.getByName("localgost");
		System.out.println(ia);
		*/
		
		//getAllByName : 접속하는 도메인의 IP를 모두 확인
		InetAddress ia[] = InetAddress.getAllByName("naver.com");
		System.out.println(ia.length);
		
		int w = 0;
		while(w < ia.length) {
			System.out.println(ia[w]);
			w++;
		}
	}
}
