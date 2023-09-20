package sp1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.RequestParam;

public class air_sql {
	Connection con = null;
	dbconfig db = new dbconfig();
	String sql = null;
	int msg = 0;	//결과값
	PreparedStatement ps = null;
	Statement st = null;	//SQL 구문 실행
	
	//여러가지 테이블에 대한 총 갯수 값 파악하는 메소드
	public int total_sum(String tablename) throws Exception {
		this.con = this.db.info();
		
		this.sql = "select count(*) as ctn from " + tablename;
		this.st = this.con.createStatement();
		ResultSet rs = this.st.executeQuery(this.sql);
		rs.next();
		int sum = Integer.parseInt(rs.getString("ctn"));
		this.st.close();
		this.con.close();
		
		return sum;
	}
	
	protected ArrayList<ArrayList<String>> person_list(int vpage) throws Exception {
	    ArrayList<ArrayList<String>> alldata = new ArrayList<ArrayList<String>>();
	    this.con = this.db.info();
	    try {
	        this.sql = "select * from air_person order by midx desc limit "+vpage+", 2";
	        this.ps = this.con.prepareStatement(this.sql);
	        ResultSet rs = this.ps.executeQuery();
	        dto_air da = new dto_air(); //dto 1차원 배열을 로드하기 위한 호출
	        while(rs.next()) {
	            da.setMidx(rs.getString("midx"));
	            da.setMid(rs.getString("mid"));
	            da.setMname(rs.getString("mname"));
	            da.setMnum(rs.getString("mnum"));
	            da.setMmobile(rs.getString("mmobile"));
	            da.setMtel(rs.getString("mtel"));
	            da.setMair(rs.getString("mair"));
	            da.setMpeo(rs.getString("mpeo"));
	            da.setMmoney(rs.getString("mmoney"));
	            da.setMidate(rs.getString("midate"));
	            alldata.add(da.listdata());
	        }
	        this.ps.close();
	        this.con.close();
	    }
	    catch(Exception e) {
	        System.out.println("SQL 오류 발생!!");
	    }
	    
	    return alldata;
	}
	
	//예매자 등록
	protected int perinsert(String mid, String mname, String mnum, String mmobile, String mtel, String mair,
			String mpeo, String totalmoney) throws Exception {
		this.con = this.db.info();
		this.con.setAutoCommit(false);	//transaction를 사용
		
		try {
			//SQL문 : 인원수를 확인하기 위한 SQL 문법
			String count = "select count(mperson) as cnt from air_reserse where mcode= '"+mid+"' and mperson >= '"+mpeo+"'";
			this.st = this.con.createStatement();
			ResultSet rs = this.st.executeQuery(count);
			rs.next();
			//System.out.println(count);
			//System.out.println(rs.getString("cnt"));
			if(rs.getString("cnt").equals("1")) {	//해당 요청 인원과 여유인원이 맞을 경우
				//사용자 정보를 입력받아서 SQL에 저장시킴
				this.sql = "insert into air_person values ('0', ?, ?, ?, ?, ?, ?, ?, ?, now())";
				this.ps = this.con.prepareStatement(this.sql);
				this.ps.setString(1, mid);
				this.ps.setString(2, mname);
				this.ps.setString(3, mnum);
				this.ps.setString(4, mmobile);
				this.ps.setString(5, mtel);
				this.ps.setString(6, mair);
				this.ps.setString(7, mpeo);
				this.ps.setString(8, totalmoney);
				this.msg = this.ps.executeUpdate();
				if(this.msg == 1) {	//해당 정보가 정상 입력이 되었을 경우 기존 인원수를 조정
					this.sql = "update air_reserse set mperson=mperson-"+mpeo+" where mcode='" + mid + "';";
					this.st = this.con.createStatement();
					this.st.executeUpdate(this.sql);	//update 진행 후 종료
					//System.out.println(this.sql);
				}
				this.ps.close();
				
				//System.out.println(this.msg);
			}
			else {
				this.msg = 3;	//여유좌석이 없음
			}
			this.con.commit();	//transaction 완료
		}
		catch(Exception e) {
			System.out.println("데이터 입력 시 조건에 문제가 발생하여 입력 취소");
			this.con.rollback();	//입력사항 취소
		}
		this.st.close();
		this.con.close();
		//System.out.println(this.msg);
		return this.msg;	//Controller로 값을 넘겨줌
	}
	
	//항공정보 입력파트
	protected int insert(String mcode, String mair, String marea, String mdate, String mperson, String mairfare, String start_date, String stop_date) {
		
		try {
			this.con = this.db.info();
			this.sql = "insert into air_reserse values ('0', ?, ?, ?, ?, ?, ?, ?, ?, now())";
			this.ps = this.con.prepareStatement(this.sql);
			this.ps.setString(1, mcode);
			this.ps.setString(2, mair);
			this.ps.setString(3, marea);
			this.ps.setString(4, mdate);
			this.ps.setString(5, mperson);
			this.ps.setInt(6, Integer.parseInt(mairfare));
			this.ps.setString(7, start_date);
			this.ps.setString(8, stop_date);
			this.msg = this.ps.executeUpdate();
			this.ps.close();
			this.con.close();			
		}
		catch(Exception e) {
			System.out.println("Database 문법 오류 발생 및 연결 오류!!");
		}
		return this.msg;
	}
}
