import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class client3 {

	public static void main(String[] args) {
		client3 c3 = new client3();
		c3.start();
		
	}
	
	
	public void start() {
		Socket socket = null;
		BufferedReader in = null;
		InputStream is  = null;
		InputStreamReader ir =null;
		Scanner sc = null;
		
		try {
			//서버 접속 ip 및 port
			socket = new Socket("192.168.110.222",8000);
			System.out.println("[서버 접속 완료]");
			sc = new Scanner(System.in);
			System.out.println("접속할 id를 입력하세요");
			String name = sc.next();	//아이디값 생성
			//Thread로 값을 넘김
			Thread clients= new clients(socket, name);
			clients.start();
			//Thread 실행 및 코드 종료
			
			is = socket.getInputStream();
			ir = new InputStreamReader(is);
			in = new BufferedReader(ir);
			
			//채팅으로 내용을 입력 시 발동하는 형태
			while(in != null){
				String inputmsg = in.readLine().intern();
				if(inputmsg =="["+name+"] Chatting Out") {
					break;
				}
				System.out.println("전송: " + inputmsg);
			}
			//Thread로 값을 던짐
		}catch(Exception e) {
			 System.out.println("[서버 접속 오류]");
		}finally {	//예외처리 오류 발생 후 처리기준
			try {	//클라이언트 소켓 종료
				socket.close();
				sc.close();
			}catch(Exception e) {	//소켓 종료가 되지 않을 경우 프로그램 강제종료
				System.out.println("강제 접속 진행");
				System.exit(0);
			}
		}
		System.out.println("서버 접속 종료");
	}

}

class clients extends Thread{
	Socket socket = null;
	String name = "";
	Scanner sc = null;
	
	//setter (소켓, 사용자 이름)
	public clients(Socket socket, String name) {
		this.socket=socket;
		this.name=name;
	}
	
	@Override
	public void run() {
		//PrintStream(클래스) : 바이너리 기반이며, Stream 자료형을 모두 받을 수 있음
		try{
			PrintStream out = new PrintStream(this.socket.getOutputStream());
			out.println(name);
			out.flush();
			//각 클라이언트가 채팅창에 메시지를 전송
			while(true) {
				this.sc = new Scanner(System.in);
				System.out.println("채팅메시지 : ");
				String outputmsg = this.sc.nextLine().intern();
				out.println(outputmsg);
				out.flush();
				if(outputmsg =="exit") {
					break;
				}
			}
		}catch(Exception e) {
			System.out.println("Thread 소켓 통신 오류");
		}
	}
}