import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class udp_client {
	//UDP - server 통신
	public static void main(String[] args) {
		client_udp cu = new client_udp();
		cu.cudp();
	}
}

class client_udp {
	private String ip = null;
	private int port = 0;
	private int myport = 0;
	public DatagramSocket ds = null;	//UDP 소켓
	public DatagramPacket dp = null;
	public InetAddress ia = null; 	//IP
	public BufferedReader br = null;
	public String msg = null;	//메시지
	
	/*
	 UDP는 포트가 서버 포트 별로, 자신이 접속하는 포트 별도
	 중복 발생 시 접속 차단됨
	 */
	
	public client_udp() {
		this.ip = "192.168.110.222";
		this.port = 7000;
		//this.myport = 7001;	//1:1 통신
		//랜덤을 이용한 다중 접속
		this.myport = (int) Math.ceil(Math.random() * 1000)+10000;
	}
	
	public void cudp() {
	    try {
	        this.ia = InetAddress.getByName(this.ip); // 서버 IP를 가져옴
	        this.ds = new DatagramSocket(this.myport); // 자신의 port에 대한 소켓을 오픈
	        this.br = new BufferedReader(new InputStreamReader(System.in));

	        System.out.println("아이디를 입력하세요 : ");
	        String id = this.br.readLine();

	        System.out.println("패스워드를 입력하세요 : ");
	        String password = this.br.readLine();

	        if (!id.equals("hong") || !password.equals("a1234")) {
	            System.out.println("아이디 또는 패스워드가 잘못되었습니다.");
	            System.exit(0);
	        }

	        this.msg = id + ":" + password;
	        byte by[] = this.msg.getBytes();

	        // 서버로 해당 메시지를 패킷을 이용해서 전송
	        // DatagramPacket(byte배열, 배열크기, 서버IP, 서버port)
	        this.dp = new DatagramPacket(by, by.length, this.ia, this.port);
	        this.ds.send(dp); // 서버로 전송
	        System.out.println("아이디와 패스워드 전송 완료!!");

	        while (true) {
	            // 서버에서 전송된 값을 받는 역할
	            byte[] by2 = new byte[200];
	            this.dp = new DatagramPacket(by2, by2.length);
	            this.ds.receive(this.dp);
	            System.out.println(new String(this.dp.getData()));

	            System.out.println("메시지를 입력하세요 : ");
	            this.msg = this.br.readLine();
	            by = this.msg.getBytes();

	            // 서버로 해당 메시지를 패킷을 이용해서 전송
	            this.dp = new DatagramPacket(by, by.length, this.ia, this.port);
	            this.ds.send(dp); // 서버로 전송
	            System.out.println("메시지 전송 완료!!");
	        }
	    } catch (Exception e) {
	        System.out.println("서버 접속 오류!!");
	    }
	}
}