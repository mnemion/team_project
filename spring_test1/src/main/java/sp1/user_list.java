package sp1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;



public class user_list {
	Connection conn = null;
	PreparedStatement ps = null;
	
	public user_list() {
		try {
			this.conn = dbconfig.info();
		}
		catch(Exception e) {
			System.out.println("Database 연결 오류 발생!!");
			e.printStackTrace();
		}
	}
	
	//전체 가입자 수 카운팅
	public int total_member() throws Exception {
		String sql2= null;
		sql2 = "select count(*) as cnt from member2";
		this.ps = this.conn.prepareStatement(sql2);
		ResultSet rs2 = this.ps.executeQuery();
		rs2.next();
		int total = Integer.parseInt(rs2.getString("cnt"));
		
		return total;
	}
	
	//검색어가 있을 경우의 메소드 (공부하기 편하라고 분할해주심)
	/*
	 1. 검색어를 입력 시 데이터베이스에 값과 동일할 경우
	 2. 검색어를 입력 시 데이터베이스와 값이 동일한 내용이 없을 경우
	 */
	public List<ArrayList<String>> listdata(String sh, String part) {
		List<ArrayList<String>> member2 = new ArrayList<ArrayList<String>>();
		try {
			String sql = null;
			if(part.intern() == "id") {	//아이디로 검색
				sql = "select * from member2 where mid=? order by mno desc";
				this.ps = this.conn.prepareStatement(sql);
				this.ps.setString(1, sh);
			}
			else if(part.intern() == "tel") {	//전화번호로 검색
				sql = "select * from member2 where mtel like ? order by mno desc;";
				this.ps = this.conn.prepareStatement(sql);
				this.ps.setString(1, "%" + sh);
			}
			
			//ResultSet : Stream형태임
			ResultSet rs = this.ps.executeQuery();
			dto_user dto = new dto_user();	//dto : setter, getter, Array
			//해당 검색 단어가 없을 경우 처리
			while(rs.next()) {
				dto.setMno(rs.getString("mno"));
				dto.setMid(rs.getString("mid"));
				dto.setMpass(rs.getString("mpass"));
				dto.setMemail(rs.getString("memail"));
				dto.setMtel(rs.getString("mtel"));
				dto.setMarea(rs.getString("marea"));
				dto.setMinter(rs.getString("minter"));
				dto.setMage(rs.getString("mage"));
				dto.setMdate(rs.getString("mdate"));
				member2.add(dto.db_data());
			}
			if(member2.size() == 0) {
				rs.getString("null");
				rs.getString("null");
				rs.getString("null");
				rs.getString("null");
				rs.getString("null");
				rs.getString("null");
				rs.getString("null");
				rs.getString("null");
				rs.getString("null");
				member2.add(dto.db_data());
			}
			this.ps.close();
			this.conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Database 컬럼 및 dto 오류발생!!");
		}
		return member2;
	}
	//SQL문법 및 데이터를 배열로 변환하여 가져옴 (검색어가 없을 경우의 메소드)
	public List<ArrayList<String>> listdata() {
		List<ArrayList<String>> member2 = new ArrayList<ArrayList<String>>();
		try {
			String sql = "select * from member2 order by mno desc";
			this.ps = this.conn.prepareStatement(sql);
			ResultSet rs = this.ps.executeQuery();
			dto_user dto = new dto_user();	//dto : setter, getter, Array
			
			while(rs.next()) {
				dto.setMno(rs.getString("mno"));
				dto.setMid(rs.getString("mid"));
				dto.setMpass(rs.getString("mpass"));
				dto.setMemail(rs.getString("memail"));
				dto.setMtel(rs.getString("mtel"));
				dto.setMarea(rs.getString("marea"));
				dto.setMinter(rs.getString("minter"));
				dto.setMage(rs.getString("mage"));
				dto.setMdate(rs.getString("mdate"));
				member2.add(dto.db_data());
				
			}
			this.conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Database 컬럼 및 dto 오류발생!!");
		}
		return member2;
	}
}
