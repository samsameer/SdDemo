package demodata.sd.com.sddemo.Model;

/**
 * Created by jabbir on 14/8/15.
 */
public class Orderin {

    private String s_no;
    private String item_id;
    private String item_no;
    private String uom;
    private String item_desc;
    private String qty_in;
    private String prod_date;
    private String serial_lot_no;
    private String price;
    private String disCount;
    private String reMarks;

    public String gets_no() {
        return s_no;
    }

    public void sets_no(String s_no) {
        this.s_no = s_no;
    }



    public String getreMarkso() {
        return reMarks;
    }

    public void setreMarks(String reMarks) {
        this.reMarks = reMarks;
    }


    public String getitem_id() {
        return item_id;
    }

    public void setitem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getitem_no() {
        return item_no;
    }

    public void setitem_no(String item_no) {
        this.item_no = item_no;
    }

    public String getuom() {
        return uom;
    }

    public void setuom(String uom) {
        this.uom = uom;
    }

    public String getqty_in() {
        return qty_in;
    }

    public void setqty_in(String qty_in) {
        this.qty_in = qty_in;
    }


    public String getprice() {
        return price;
    }

    public void setprice(String price) {
        this.price = price;
    }


    public String getprod_date() {
        return prod_date;
    }

    public void setprod_date(String prod_date) {
        this.prod_date = prod_date;
    }


    public String getitem_desc() {
        return item_desc;
    }

    public void setitem_desc(String item_desc) {
        this.item_desc = item_desc;
    }

    public String getdisCount() {
        return disCount;
    }

    public void setdisCount(String disCount) {
        this.disCount = disCount;
    }
}
