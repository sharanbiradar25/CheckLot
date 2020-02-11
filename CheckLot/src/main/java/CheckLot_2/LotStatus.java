package CheckLot_2;

import org.json.simple.JSONObject;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class LotStatus {
	
	public void sellerStatus(String a, String b){
		String sellerURI1="https://c-connect-qa4.copart.com/connect_api/self_service/seller/lot_status";
		RequestSpecification request=RestAssured.given();
		request.headers("Content-Type", "application/json");
		JSONObject json=new JSONObject();
		json.put("auth_number",b);
		json.put("first_time","N");
		json.put("lot_num",a);
		request.body(json.toJSONString());
		Response resp=request.post(sellerURI1);
		System.out.println(resp.getStatusCode());
		String s=resp.asString();
		System.out.println(s);
	
	}
	
	
	}
