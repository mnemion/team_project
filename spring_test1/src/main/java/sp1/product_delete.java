package sp1;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class product_delete {
	Connection conn = null;
	
	public product_delete() {
		try {
			this.conn = dbconfig.info();
		}
		catch(Exception e) {
			System.out.println("DB접속 오류!!");
		}
	}
	//삭제 Module
	protected int delete_ok(String idx) throws Exception {
		String sql = "delete from product where pidx=?";
		PreparedStatement ps = this.conn.prepareStatement(sql);
		ps.setString(1, idx);
		int oksign = ps.executeUpdate();
		
		ps.close();
		this.conn.close();
		return oksign;
	}
}
