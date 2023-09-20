package sp1;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class dto_product {
	String pidx, pcode, pname, pmoney, pimg, psale, puse;
	
	//1차원 배열 (getter)
	public ArrayList<String> db_data(){
	    ArrayList<String> list = new ArrayList<String>();
	    list.add(getPidx());
	    list.add(getPcode());
	    list.add(getPname());
	    list.add(getPmoney());
	    list.add(getPsale());
	    return list;
	}

}