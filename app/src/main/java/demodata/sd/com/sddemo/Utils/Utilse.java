package demodata.sd.com.sddemo.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class Utilse {
    private static final String PREFIX = "json";

    /**
     * Using reflection to override default typeface
     * NOTICE: DO NOT FORGET TO SET TYPEFACE FOR APP THEME AS DEFAULT TYPEFACE WHICH WILL BE OVERRIDDEN
     * @param context to work with assets
     * @param defaultFontNameToOverride for example "monospace"
     * @param customFontFileNameInAssets file name of the font from assets
     */
    public static String  xsCustno="";
    public static String MAINTAG="";
    public  static  String PUTCLIENT ="/kaiserpad/web_createSO2?compcode=01&docno="+""+"&docdate="+Utilse.currenDate()+"&xmldata=";
    public  static  String PUTCLIENTSO2 ="/kaiserpad/web_createSO?compcode=01&docno="+""+"&docdate="+Utilse.currenDate()+"&xmldata=";

    public  static  String SalesMan="";
    public  static  String GETPOSCLIENT ="/kaiserpad/web_ar_customer?compcode=01&addrref=2";
    public  static  String GETLISTITE ="/kaiserpad/web_in_item_list_price?compcode=01";

    public  static  String SALESORDER="/kaiserpad/web_getSOHeader?compcode=01&docdate="+Utilse.currenDate();
    public  static  String SALESMAN ="/kaiserpad/web_user_master?compcode=01";
    public  static  String SONMAN ="/kaiserpad/web_getSODetails?compcode=01&docno=";
    public static JSONArray jsAr=new JSONArray();
    public static String userAuth;
    public static String compnyCd="";
    public static String passAuth;
    public static String [] mstrArray={ "comp_code","doc_no","doc_date","cust_no","ac_period","cust_po_no","cust_po_date","cont_name","ship_addr_ref","salesman", "curr_code", "exch_rate","absorb_tax", "tax_amt", "disc_amt", "total","so_status","remarks"};

    public static String [] dtlsArray={ "comp_code","doc_no", "s_no", "item_id", "item_no","item_descr","cust_part_no","uom","uom_cf", "qty_order",
            "price","total", "disc_type", "disc_value","disc_amount","net_amount", "tax_type","tax_rate","tax_amt","so_etd", "so_eta","remarks"};



    public static LinkedHashMap <String,String[]> newItemOrder=new LinkedHashMap<String,String[]>();
    public static void overrideFont(Context context, String defaultFontNameToOverride, String customFontFileNameInAssets) {
        try {
            final Typeface customFontTypeface = Typeface.createFromAsset(context.getAssets(), customFontFileNameInAssets);

            final Field defaultFontTypefaceField = Typeface.class.getDeclaredField(defaultFontNameToOverride);
            defaultFontTypefaceField.setAccessible(true);
            defaultFontTypefaceField.set(null, customFontTypeface);
        } catch (Exception e) {
            //Log.e("Can not set custom font " + customFontFileNameInAssets + " instead of " + defaultFontNameToOverride);
        }
    }


    private static int DEFAULT_TIMEOUT = 20 * 1000;
    private static AsyncHttpClient client = new AsyncHttpClient();
    public static String currenDate() {
        String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        return date;

    }
    public void listSortEx(List<String> list, final Boolean descOrder){
        Collections.sort(list, new Comparator<String>() {

            @Override
            public int compare(String o1, String o2) {
                if(descOrder){
                    return o2.compareTo(o1);
                }else{
                    return o1.compareTo(o2);
                }
            }
        });
    }

//    client.setTimeout(2000);
//    client.setConnectTimeout(2000);
//    AsyncHttpClient aClient = new AsyncHttpClient();
//    aClient.setTimeout(DEFAULT_TIMEOUT);

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client = new AsyncHttpClient();
        client.setBasicAuth("DBA", "#DBA!");
        client.setTimeout(500 * 1000);
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void gettxt(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client = new AsyncHttpClient();
        client.setBasicAuth("DBA", "#DBA!");
        client.setTimeout(500 * 1000);
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }


    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client = new AsyncHttpClient();
        client.setBasicAuth("DBA", "#DBA!");
        client.setTimeout(500 * 1000);
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager connMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = connMan.getActiveNetworkInfo();
        if (network == null || !network.isConnected()) {

            return false;
        }
        return true;
    }

    public static boolean saveArray(String[] array, String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("preferenceName", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(arrayName + "_size", array.length);
        for (int i = 0; i < array.length; i++)
            editor.putString(arrayName + "_" + i, array[i]);
        return editor.commit();
    }
    public static void alertDialogShow(final Context context, String message, String title, final int xyz) {

        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);

        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setMessage(message);


        int x = xyz;
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (xyz == 1) {
                   
                    ((Activity) (context)).finish();


                } else if (xyz == 2) {
                    ((Activity) (context)).finish();
                    context.startActivity(((Activity) context).getIntent());
                } else if (xyz == 3) {
               
                    ((Activity) (context)).finish();
                } else if (xyz == 6) {
                   
                    ((Activity) (context)).finish();
                }
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
    public static void saveJSONArray(Context c, String prefName, String key, JSONArray array) {
        SharedPreferences settings = c.getSharedPreferences(prefName, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(Utilse.PREFIX + key, array.toString());
        editor.commit();
    }
    public static void removeJsonSharedPreferences(Context c, String prefName) {
        if (c != null) {
            SharedPreferences mSharedPreferences = c.getSharedPreferences(prefName, 0);
            if (mSharedPreferences != null)
                mSharedPreferences.edit().clear().commit();
            //mSharedPreferences.edit().clear().commit();
        }

    }

    public static void saveMap(final Context context, Map<String, String> inputMap, String key) {
        SharedPreferences pSharedPref = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pSharedPref.edit();
        if (pSharedPref != null) {
            JSONObject jsonObject = new JSONObject(inputMap);
            String jsonString = jsonObject.toString();
            editor.remove(key).commit();
            editor.putString(key, jsonString);
            editor.commit();
        }
    }

    public static Map<String, String> loadMap(final Context context, String keys) {
        Map<String, String> outputMap = new HashMap<String, String>();
        SharedPreferences pSharedPref = context.getSharedPreferences(keys, Context.MODE_PRIVATE);
        try {
            if (pSharedPref != null) {
                String jsonString = pSharedPref.getString(keys, (new JSONObject()).toString());
                JSONObject jsonObject = new JSONObject(jsonString);
                Iterator<String> keysItr = jsonObject.keys();
                while (keysItr.hasNext()) {
                    String key = keysItr.next();
                    String value = (String) jsonObject.get(key);
                    outputMap.put(key, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputMap;
    }
    public static JSONArray loadJSONArray(Context c, String prefName, String key) throws JSONException {
        SharedPreferences settings = c.getSharedPreferences(prefName, 0);
        return new JSONArray(settings.getString(Utilse.PREFIX + key, "[]"));
    }
    public static String[] loadArray(String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("preferenceName", 0);
        int size = prefs.getInt(arrayName + "_size", 0);
        String array[] = new String[size];
        for (int i = 0; i < size; i++)
            array[i] = prefs.getString(arrayName + "_" + i, null);
        return array;
    }
    private static String getAbsoluteUrl(String relativeUrl) {
        return relativeUrl;
    }

}