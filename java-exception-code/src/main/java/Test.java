import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class Test {
    public static void main(String[] args) throws Exception {

        String text = "[ {\n" +
                "  \"id\" : 687,\n" +
                "  \"plat_merchant_uuid\" : \"90213fa9-f742-11ea-a8f4-b8599f49ddfc\",\n" +
                "  \"plat_merchant_id\" : \"9999a\",\n" +
                "  \"contract_uuid\" : null,\n" +
                "  \"settle_id\" : null,\n" +
                "  \"merchant_uuid\" : null,\n" +
                "  \"shop_code\" : \"mx0003\",\n" +
                "  \"plat_shop_name\" : \"test\",\n" +
                "  \"plat_account\" : \"wmmxsj2760356\",\n" +
                "  \"plat_password\" : \"wm123456\",\n" +
                "  \"plat_main_account\" : \"ddzhmeituan\",\n" +
                "  \"settle_bank_card_type\" : 1,\n" +
                "  \"settle_bank_card\" : null,\n" +
                "  \"channel\" : 3,\n" +
                "  \"plat_main_account_name\" : null,\n" +
                "  \"reserve_online_status\" : 0,\n" +
                "  \"reserve_online_time\" : \"Thu Aug 20 23:56:50 CST 2020\",\n" +
                "  \"force_reserve_online\" : 0,\n" +
                "  \"plat_status\" : 2,\n" +
                "  \"plat_status_time\" : \"Fri Aug 28 23:57:02 CST 2020\",\n" +
                "  \"open_shop_user_id\" : null,\n" +
                "  \"open_shop_user_name\" : \"万枫\",\n" +
                "  \"operation_user_id\" : \"1273537462425227264\",\n" +
                "  \"operation_user_name\" : \"翁嘉隆\",\n" +
                "  \"disabled\" : 0,\n" +
                "  \"create_time\" : \"Fri Aug 28 23:57:04 CST 2020\",\n" +
                "  \"create_by\" : \"姜绪聪\",\n" +
                "  \"modify_by\" : \"姜绪聪\",\n" +
                "  \"modify_time\" : \"Fri Aug 28 23:57:04 CST 2020\",\n" +
                "  \"shop_business_hours\" : null,\n" +
                "  \"brand_code\" : null,\n" +
                "  \"brand_name\" : \"麻选食记\",\n" +
                "  \"department_id\" : null,\n" +
                "  \"department_name\" : null,\n" +
                "  \"draw_type\" : null,\n" +
                "  \"fixed_amount\" : null\n" +
                "} ]";
        SimpleDateFormat simpleDateFormat =  new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        SimpleDateFormat target =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        JSONArray array = JSON.parseArray(text);
        for (int i = 0; i < array.size(); i++) {
            JSONObject tmp = array.getJSONObject(i);
            for (Map.Entry<String, Object> entry : tmp.entrySet()) {
                Object value = entry.getValue();
                if (value!=null) {
                    String valueStr = String.valueOf(value);
                    if (valueStr.contains("CST")){
                        Date date = simpleDateFormat.parse(valueStr);
                        tmp.put(entry.getKey(),target.format(date));
                    }
                }
            }
        }

        System.out.println(JSONObject.toJSONString(array, SerializerFeature.WriteMapNullValue));
    }


}
