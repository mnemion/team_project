package sp1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

//Module
public class product_list {
	Connection conn = null;
	PreparedStatement ps = null;
	//database 연결
	public product_list() {
		try {
			this.conn = dbconfig.info(); 
		}
		catch(Exception e) {
			System.out.println("Database 접속 오류 발생!!");
			e.printStackTrace();
		}
	}
	//DTO와 관계없는 데이터 갯수르 파악
	public int data_ea() {
		ResultSet rs = null;
		int ea = 0;
		try {
			String sql = "select count(*) as cnt from product order by pidx desc";
			this.ps = this.conn.prepareStatement(sql);
			rs = this.ps.executeQuery();
			rs.next();
			ea = rs.getInt("cnt");
			this.conn.close();	//DB 접속 종료
		}
		catch(Exception e) {
			System.out.println("Database 문법 오류!!");
		}
		return ea;
	}
	
	
	//DTO와 연결하여 Database에 있는 값을 2차원 배열로 만드는 작업
	public ArrayList<ArrayList<String>> list_data() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<ArrayList<String>> pd_list = new ArrayList<ArrayList<String>>();
		try {
			//새로운 DB연결을 재실행함
			this.conn = dbconfig.info();
			String sql = "select * from product order by pidx desc";
			ps = this.conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			//DTO 작업 (setter)
			while(rs.next()) {
			    dto_product dp = new dto_product();
			    dp.setPidx(rs.getString("pidx"));
			    dp.setPcode(rs.getString("pcode"));
			    dp.setPname(rs.getString("pname"));
			    dp.setPmoney(rs.getString("pmoney"));
			    dp.setPuse(rs.getString("puse"));
			    pd_list.add(dp.db_data());
			}
			this.conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("SQL 문법 오류 발생!!");
		}
		return pd_list;
	}
}