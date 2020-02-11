package Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import CheckLot_2.LotStatus;
import DataProvider.ConnectionDB;
import DataProvider.GetData;

public class Sanity {
	List<String> lotList;
	List<String> codeList;

	@BeforeTest
	public void data() throws ClassNotFoundException, IOException, SQLException {
		ConnectionDB db = new ConnectionDB();
		lotList = db.getLots("LTLOTNBR");
		GetData g = new GetData();
		codeList = g.convertSellerCode();

	}

	@Test(dataProvider = "SearchProvider")
	public void testSeller(Map<String, String> map) {
		LotStatus s = new LotStatus();
		for (Map.Entry<String, String> h : map.entrySet()) {
			s.sellerStatus(h.getKey(), h.getValue());
		}

	}

	@DataProvider(name = "SearchProvider")
	public Map<String, String> getDataFromDataprovider() throws ClassNotFoundException, IOException, SQLException {
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < lotList.size(); i++) {
			map.put(lotList.get(i), codeList.get(i));
		}
		return map;

	}
}
