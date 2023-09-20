import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class udp_server {
	//UDP - server 통신	1:1 통신
	public static void main(String[] args) {
		server_chat sc = new server_chat();
		sc.udp();
	}
}

class server_chat {
	private String ip = null;	//서버 IP
	private int port = 0;	//서버 UDP 포트
	public DatagramSocket ds = null;	//UDP 소켓
	public DatagramPacket dp = null;	//메시지 송수신 패킷
	public InetAddress ia = null; 	//서버 IP, 접속자 IP 기록
	public BufferedReader br = null;
	String msg = "";	//메시지
	
	public server_chat() {
		this.ip = "192.168.110.222";
		this.port = 7000;
	}
	
	public void udp() {
		try {
			this.ia = InetAddress.getByName(this.ip);	//서버IP를 확인
			System.out.println("UDP 서버 오픈!!");
			this.ds = new DatagramSocket(this.port);	//UDP 포트 오픈
			
			while(true) {
				byte[] by = new byte[200];	//메시지 크기
				this.dp = new DatagramPacket(by, by.length);	//Client에서 오는 패킷 크기를 정함
				System.out.println("채팅 시작!!");
				this.ds.receive(this.dp);	//client에서 보낸 메시지를 서버에서 받는 역할
				
				this.msg = new String(this.dp.getData());
				System.out.println(this.msg);
								
				/* 클라이언트로 메시지를 서버에서 전송하는 형태 */
				System.out.println("메시지를 입력하세요 : ");
				
				/* 클라이언트 IP, UDP PORT 정보 가져옴 */
				InetAddress ia2 = this.dp.getAddress();
				int port2 = this.dp.getPort();
				
				this.br = new BufferedReader(new InputStreamReader(System.in));
				this.msg = this.br.readLine();
				byte by2[] = this.msg.getBytes();
				
				this.dp = new DatagramPacket(by2, by2.length, ia2, port2);
				this.ds.send(this.dp);	//서버에서 클라이언트로 메시지를 전송
			}
		}
		catch(Exception e) {
			System.out.println("UDP 서버 오픈 오류!!");
		}
	}
}