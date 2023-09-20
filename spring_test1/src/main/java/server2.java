import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class server2 {

	public static void main(String[] args) {
		new chat().chatserver();
	}
}

class chat {
	private int port = 8009;	//서버 포트 오픈
	ServerSocket sk = null;	//서버 소켓 오픈
	Socket so = null;	//소켓 통신
	Scanner sc = null;	//메시지 입력
	InputStream is = null;	//client에서 메시지를 받는 역할
	OutputStream os = null;	//client로 메시지를 발송
	String mid = null;	//아이디 입력 변수
	String msg = null;	//서버가 입력한 메시지
	String cmsg = null;	//클라이언트가 입력한 메시지
	String check = null;	//exit 확인 유/무
	
	public void chatserver() {
		try {
			this.sk = new ServerSocket(this.port);
			this.sc = new Scanner(System.in);
			System.out.println("아이디를 생성하세요 : ");
			this.mid = sc.next();
			System.out.println("채팅방 개설되었습니다.");
			
			for(;;) {	//접속 유지
				this.so = this.sk.accept();
				this.is = this.so.getInputStream();
				this.os = this.so.getOutputStream();
				
				//client 내용을 받아오는 역할
				byte data[] = new byte[1024*2];
				int n = this.is.read(data);
				this.msg = new String(data, 0, n);
				System.out.println(this.msg);
				
				//메시지를 client로 보내는 역할
				this.sc = new Scanner(System.in);
				System.out.println("메시지를 입력하세요 : ");
				this.check = this.sc.nextLine().intern();
				
				if(this.check == "exit") {
					System.out.println("채팅 종료!!");
					this.os.close();
					this.is.close();
					this.so.close();
					this.sk.close();
				}
				this.cmsg = "["+this.mid+"] : " + this.check;
				this.os.write(this.cmsg.getBytes());	//보내기 완료
				this.os.flush();	//메모리를 비워둠
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
}