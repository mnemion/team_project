package sp1;

import java.io.File;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class webpage2 {
	PrintWriter pw = null;
	
	//예약자 리스트 출력 파트 + pageing 효과 주기
	//required = false : GET, POST 해당 변수값에 값이 없을 경우 해당 요구사항을 예외처리 하게 된다.
	@GetMapping("air_list.do")
	public String air_list(Model m, @RequestParam(required = false) String page) {
	    String p = page;
		air_sql as = new air_sql();
		
		int vpage = 0;	//페이지 번호 1번
		if(p == null || p == "null" || p.equals("1") || p == "") {
	    	vpage = 0;
	    }
		else {	//1번 페이지 외에 작동되는 범위 사이즈
			vpage = (Integer.parseInt(p) * 2) - 2;
		}
	    
	    try {
	        //데이터 전체 리스트 view로 보내기
	        ArrayList<ArrayList<String>> total_list = as.person_list(vpage);
	        m.addAttribute("total_list", total_list);
	        
	        //총 갯수값 view로 보내기
	        int total_person = as.total_sum("air_person");
	        m.addAttribute("total_person", total_person);
	    }
	    catch(Exception e) {
	        System.out.println("데이터베이스 문법 오류!!");
	    }
	    return "/air_list";
	}
	
	@PostMapping("/air_personok.do")
	public String air_person(Model m,
			@RequestParam String mid,
			@RequestParam String mname,
			@RequestParam String mnum,
			@RequestParam String mmobile,
			@RequestParam String mtel,
			@RequestParam String mair,
			@RequestParam String mpeo,
			@RequestParam String totalmoney) {
		
		try {
			air_sql as = new air_sql();
			int result = as.perinsert(mid, mname, mnum, mmobile, mtel, mair, mpeo, totalmoney);
			//System.out.println(result);
			switch(result) {
				case 1:
					System.out.println("예매가 완료되었습니다.");
					break;
				case 3:
					System.out.println("죄송합니다. 여유좌석이 없습니다.");
					break;
				default:
					System.out.println("접속에 문제가 발생하였습니다.");
					break;
			}
		}
		catch(Exception e) {
			System.out.println(e);
			System.out.println("DB 입력오류 발생!!");
		}
		
		return null;
	}

	@PostMapping("/air_reserveok.do")
    public String air_reserve(Model m,
    		@RequestParam String mcode,
    		@RequestParam String mair,
    		@RequestParam String marea,
    		@RequestParam String mdate,
    		@RequestParam String mperson,
    		@RequestParam String mairfare,
    		@RequestParam String start_date,
    		@RequestParam String stop_date
    		) {
		simplify sp = new simplify();
		
		mdate = sp.day(mdate);
		start_date = sp.day(start_date);
		stop_date = sp.day(stop_date);
        
		air_sql as = new air_sql();
		int result = as.insert(mcode, mair, marea, mdate, mperson, mairfare, start_date, stop_date);
		if(result == 0) {
			m.addAttribute("msg", "비정상 데이터로 인하여 정보가 등록되지 않았습니다.");
		}
		else {
			m.addAttribute("msg", "성공적으로 데이터가 입력되었습니다.");
		}
        return "/WEB-INF/jsp/air_reserveok";
    }

	
	@PostMapping("/kakao_loginok.do")
	public String kakaos(HttpServletRequest req, Model m) throws Exception {
		String part = req.getParameter("part").intern();
		String mid, mpass, kakao_id, kakao_nick, kakao_email = null;
		login_etc le = null;
		int result = 0;
		
		if(part == "kakao") {
			kakao_id = req.getParameter("kakao_id");
			kakao_nick = req.getParameter("kakao_email");
			kakao_email = req.getParameter("kakao_nick");
			le = new login_etc(kakao_id, kakao_email, kakao_nick, part);
		}
		else {
			mid = req.getParameter("mname");
			mpass = req.getParameter("mpass");
			le = new login_etc(mid, mpass, "", part);
		}
		le.join();	//Thread 작업이 끝날 때까지 아래의 코드를 활성하지 않지 않음.
		
		result = le.result();	//getter 메소드에서 결과값을 받는 부분
		
		if(result == 1) {
			System.out.println("정상적으로 회원가입이 되었습니다.");
		}
		else {
			System.out.println("프로세스 오류 발생!!");
		}
		return null;
	}
	
	
	@PostMapping("/fileok.do")
	public void upload(MultipartFile mfile, HttpServletRequest req, Model m) throws Exception {
		String filename = mfile.getOriginalFilename();
		long filesize = mfile.getSize();
		String url = req.getServletContext().getRealPath("/fileup") + filename;
		System.out.println(url);
		//System.out.println(filename);
		//파일 저장
		File f = new File(url);
		FileCopyUtils.copy(mfile.getBytes(), f);	//Spring 전용 파일업로드 클래스
		System.out.println("업로드 파일 정상적으로 진행 완료!!");
	}
	
	
	//수정 완료 파트
	@PostMapping("/product_modifyok.do")
	public String ok_modify(HttpServletRequest req, HttpServletResponse res) {
		res.setContentType("text/html; charset=utf-8");
		String pidx = req.getParameter("pidx");
		String pcode = req.getParameter("pcode");
		String pname = req.getParameter("pname");
		String pmoney = req.getParameter("pmoney");
		String psale = req.getParameter("psale");
		String pues = req.getParameter("puse");
		
		product_ok ok = new product_ok();
		String msg = "";
		int result = ok.modify_sql(pidx, pcode, pname, pmoney, psale, pues);
		if(result == 1) {
			msg = "<script>alert('정상적으로 수정 완료 되었습니다.');"
					+ "location.href='./product_list.do'"
					+ "</script>";
		}else {
			msg = "<script>alert('수정 내용이 올바르지 않습니다.');"
					+ "history.go(-1)"
					+ "</script>";
		}
		try {
			this.pw = res.getWriter();
			this.pw.write(msg);
		}catch(Exception e) {
			System.out.println(e);
			System.out.println("올바른 값이 전달되지 않음");
		}
		return null;
	}
	
	//하나의 상품 수정 파트(JSTL)
	@GetMapping("/product_modify.do")
	public String view_product(HttpServletRequest req, Model m) {
		String idx = req.getParameter("idx");
		try {
			product_modify pm = new product_modify();
			ArrayList<String> data = pm.view_ok(idx);
			System.out.println(data);
			m.addAttribute("data",data);
		}catch(Exception e) {
			System.out.println("접근 오류");
		}
				
		return "/WEB-INF/jsp/product_modify";
	}
	
	
	
	//상품 삭제 파트
	@GetMapping("/product_delete.do")
	public void del_product(HttpServletRequest req, HttpServletResponse res) {
		res.setContentType("text/html; charset=utf-8;");
		try {
			this.pw=res.getWriter();
			String no = req.getParameter("idx");

			product_delete pd = new product_delete();
			int result = pd.delete_ok(no);	
			if(result==1) {  //정상적인 sql 작동
				this.pw.write("<script>"+ "alert('정상적으로 삭제 되었습니다.');"+"location.href='./product_list.do';"+"</script>");
			}else {// sql 문법이 올바르게 작동되지 않을 경우
				this.pw.write("<script>"+ "alert('올바른 데이터 갑ㄱ이 압니다..');"+"location.href='./product_list.do"+"</script>");
			}
		}catch(Exception e) { 
			this.pw.write("<script>"+ "alert('잘못된 접근 방식입니다.');"+"history.go(-1);"+"</script>");
		}
	}
	
	//Controller
	//JSTL로 view page 출력 파트
	@RequestMapping("/product_list.do")
	public String pd_list(HttpServletRequest req, Model m) {
	    List<ArrayList<String>> product_data = null;
	    product_list pl = new product_list();
	    int listea = pl.data_ea();
	    product_data = pl.list_data();
	    ArrayList<ArrayList<String>> plist = pl.list_data();
	    req.setAttribute("listea", listea); //Controller -> view 출력
	    req.setAttribute("plist", plist);	//Controller -> view 출력
	        
	    return "/WEB-INF/viewpage/product_list";
	        
	}

	
	//spring1.html에 넘어온 값을 view를 통해서 핸들링함
	@PostMapping("/spring1ok.do")
	public String product(HttpServletRequest req, HttpServletResponse res, Model m) {
		String pdcode = null;
		String pdname = null;
		
		try {
			pdcode = req.getParameter("pdcode").intern();
			pdname = req.getParameter("pdname").intern();
			m.addAttribute("code", pdcode);
			m.addAttribute("name", pdname);
		}
		catch(Exception e) {
			System.out.println("파라미터 오류 발생!!");
		}
		return "/WEB-INF/jsp/spring1ok";
	}
	
	@PostMapping("spring2ok.do")
	public String agree(HttpServletRequest req, Model m) {
		//checkbox 사용 시 intern() 사용하지 않음
		String ag = req.getParameter("mail");
		
		//radio 사용 시 intern()을 사용하여 조건완성
		String ad = req.getParameter("ad").intern();
		if(ad == "N") {
			System.out.println("동의안함");
		}
		else {
			System.out.println("동의함");
		}	
		return null;
	}
	
	//getter와 setter를 이용해서 값을 로드
	@PostMapping("/spring3ok.do")
	public String user(HttpServletRequest req, Model m) {
		String mid = req.getParameter("mid");
		String mname = req.getParameter("mname");
		
		userdata ud = new userdata(mid, mname);
		System.out.println(ud.getMid());
		System.out.println(ud.getMname());
		
		return null;
	}
	//실제 메일 서비스 + naver.com 메일서버
	@PostMapping("/spring4ok.do")
	public String mails(HttpServletRequest req, Model m) {
		String pname = req.getParameter("pname");	//보낸이
		String pemail = req.getParameter("pemail");	//회신 받을 메일
		String ptitle = req.getParameter("ptitle");	//제목
		String pcentent = req.getParameter("pcentent");	//내용
		/* 실제 메일 API 서버 정보를 입력 */
		String host = "smtp.naver.com";
	    String user = "game1761@naver.com";
	    String password = "";
	    String to_mail = "game1761@naver.com";
		/* API 포트번호 및 TLS 정보를 셋팅 */
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", 587);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.debug", "true");
		props.put("mail.smtp.socketFactory.port", 587);
		props.put("mail.smtp.ssl.protocols", "TLSv1.2");
		
		//STMP 서버에 로그인을 시키기 위한 작업(암호화)
		Session session = Session.getDefaultInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new javax.mail.PasswordAuthentication(user, password);
			}
		});
		try {
			//MimeMessage : okcall 발생함 아이디/패스워드, 포트 모두 맞을 경우
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(pemail, pname));	//보낸이
			//받는 메일 주소
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to_mail));
			
			msg.setSubject(ptitle);	//제목
			msg.setText(pcentent);	//내용
			Transport.send(msg);	//발송
			System.out.println("메일 발송이 정상적으로 되었습니다.");
		}
		catch(Exception e) {
			System.out.println("메일 서버 통신 오류!!");
		}
		return null;
	}
	//Controller에서 배열을 JSP (View)로 출력하는 형태
	@GetMapping("spring5ok.do")
	public String datalist(HttpServletRequest req, Model m) {
		/*
		dbconfig db = new dbconfig();
		try {
		Connection ct = db.info();
		System.out.println(ct.toString());
		}
		catch(Exception e) {
			
		}
		*/
		String data[] = {"이순신", "홍길동", "강감찬", "이산", "한석봉"};
		ArrayList<String> al = new ArrayList<String>(Arrays.asList(data));
		//m.addAttribute("person_list", al);
		
		//JSP방식 -> 파이썬으로 크롤링이 잘 됨
		//req.setAttribute("person_list", al);
		//return "/WEB-INF/jsp/spring5ok"; //일반 JSP view
		
		//표현식
		m.addAttribute("person_list", al);	//표현식 방식
		m.addAttribute("person_delete","10");	//강제로
		
		//표현식 값을 자바스크립트로 전달(front-end) Node형태로 출력
		
		return "/WEB-INF/jsp/spring5_2ok"; //표현식 JSP view
	}
	
	//사용자 리스트 출력 MYSQL 이용
	@RequestMapping("spring6ok.do")
	public String userlist(HttpServletRequest req, Model m) {
		//파라미터값으로 검색어가 적용되는 경우
		String search = req.getParameter("search");
		String part = req.getParameter("part");
		
		List<ArrayList<String>> member_data = null;
		
		try {
			//검색이 없을 경우
			if(search == "null" || search == null || search == "") {
				user_list ul = new user_list();
				member_data = ul.listdata();
			}
			else {	//검색 단어가 있을 경우
				user_list ul = new user_list();
				member_data = ul.listdata(search, part);
			}
			//JSP 방식
			//회원가입 카운팅 전체 숫자값
			req.setAttribute("total", new user_list().total_member());
			
			req.setAttribute("member_data", member_data);
			req.setAttribute("part", part);	//검색형태
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return "/WEB-INF/jsp/member_list";
	}
}
