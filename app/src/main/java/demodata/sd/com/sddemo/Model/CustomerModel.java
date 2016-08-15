package demodata.sd.com.sddemo.Model;

/**
 * Created by jabbir on 14/8/15.
 */
public class CustomerModel {

    private String cust_no;
    private String cust_name;
    private String item_no;
    private String uom;
    private String item_desc;
    private String qty_in;
    private String prod_date;
    private String serial_lot_no;




    public String getcust_no() {
        return cust_no;
    }

    public void setcust_no(String cust_no) {
        this.cust_no = cust_no;
    }




    public String getcust_name() {
        return cust_name;
    }

    public void setcust_name(String cust_name) {
        this.cust_name = cust_name;
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


}
