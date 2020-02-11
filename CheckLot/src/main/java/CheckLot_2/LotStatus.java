package CheckLot_2;

import org.json.simple.JSONObject;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class LotStatus {
		
	public void sellerStatus(String sellerCode, String lotNum){
		String sellerURI="https://c-connect-qa4.copart.com/connect_api/self_service/seller/lot_status";
		RequestSpecification request=RestAssured.given();
		request.headers("Content-Type", "application/json");
		JSONObject obj=new JSONObject();
		obj.put("auth_number",sellerCode);
		obj.put("first_time","N");
		obj.put("lot_num",lotNum);
		request.body(obj.toJSONString());
		Response response=request.post(sellerURI);
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody().asString());
		
	}
	
//	public static void main(String[] args){
//		LotStatus s=new LotStatus();
//		s.sellerStatus("2634", "60156530");
//	}
	}
