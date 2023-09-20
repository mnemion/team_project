package sp1;

import java.util.ArrayList;

import lombok.*;

@Setter
@Getter
public class dto_air {
    String midx, mid, mname, mnum, mmobile, mtel, mair, mpeo, mmoney, midate;
    
    public ArrayList<String> listdata(){
        ArrayList<String> al = new ArrayList<String>();
        al.add(getMidx());
        al.add(getMid());
        al.add(getMname());
        al.add(getMnum());
        al.add(getMmobile());
        al.add(getMtel());
        al.add(getMair());
        al.add(getMpeo());
        al.add(getMmoney());
        al.add(getMidate());
        
        return al;
    }
}