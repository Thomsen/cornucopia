package com.cornucopia.devices.map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GoogleLocationManager {

	
	public GeoLocationInfo getBaseStationLocation(List<CellIdInfo> listCellIdInfo) {
		if (listCellIdInfo == null) {
			return null;
		}
		
		GeoLocation location = new GeoLocation();
		
		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost("http://www.google.com/loc/json");
		
		Gson gson = new Gson();
		CellIdInfo cellIdInfo = listCellIdInfo.get(0);
		
//		Map<String, Object> request = new HashMap<String, Object>();
//		request.put("version", "1.1.0");
//		request.put("host", "maps.google.com");
//		request.put("home_mobile_country_code", cellIdInfo.getMcc());
//		request.put("home_mobile_network_code", cellIdInfo.getMnc());
//		request.put("request_address", true);
//		
//		if ("460".equals(cellIdInfo.getMcc())) {
//			request.put("address_language", "zh_CN");
//		} else {
//			request.put("address_language", "en_US");
//		}
		
		// 请求信息
		GoogleCellIdRequest googleRequest = new GoogleCellIdRequest();
		googleRequest.setVersion("1.1.0");
		googleRequest.setHost("maps.google.com");
		googleRequest.setHomeMobileCountryCode(cellIdInfo.getMcc());
		googleRequest.setHomeMobileNetworkCode(cellIdInfo.getMnc());
		googleRequest.setRequestAddress(true);
		if (460 == (cellIdInfo.getMcc())) {
			googleRequest.setAddressLanguage("zh_CN");
		} else {
			googleRequest.setAddressLanguage("en_US");
		}
		
//		Map<String, Object> curRequest = new HashMap<String, Object>();
//		Map<String, Object> arrRequest = null;
//		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
//		
//		curRequest.put("cell_id", cellIdInfo.getCellId());
//		curRequest.put("location_area_code", cellIdInfo.getLac());
//		curRequest.put("mobile_country_code", cellIdInfo.getMcc());
//		curRequest.put("mobile_network_code", cellIdInfo.getMnc());
//		curRequest.put("age", 0);
//		data.add(curRequest);
//		
//		if (listCellIdInfo.size() > 2) {
//			for (int i=1; i<listCellIdInfo.size(); i++) {
//				arrRequest = new HashMap<String, Object>();
//				cellIdInfo = listCellIdInfo.get(i);
//				
//				arrRequest.put("cell_id", cellIdInfo.getCellId());
//				arrRequest.put("location_area_code", cellIdInfo.getLac());
//				arrRequest.put("mobile_country_code", cellIdInfo.getMcc());
//				arrRequest.put("mobile_network_code", cellIdInfo.getMnc());
//				arrRequest.put("age", 0);
//				
//				data.add(arrRequest);
//			}
//		}
//		
//		request.put("cell_towers", gson.toJson(data));
		
		// 基站信息
		CellTowerInfo cellTower = new CellTowerInfo();
		cellTower.setCellId(cellIdInfo.getCellId());
		cellTower.setMobileCountryCode(cellIdInfo.getMcc());
		cellTower.setMobileNetworkCode(cellIdInfo.getMnc());
		cellTower.setLocationAreaCode(cellIdInfo.getLac());
		cellTower.setAge(0);
		googleRequest.addCellTower(cellTower);
		
		if (listCellIdInfo.size() > 2) {
			for (int i=1; i<listCellIdInfo.size(); i++) {
				cellIdInfo = listCellIdInfo.get(i);
				
				cellTower.setCellId(cellIdInfo.getCellId());
				cellTower.setMobileCountryCode(cellIdInfo.getMcc());
				cellTower.setMobileNetworkCode(cellIdInfo.getMnc());
				cellTower.setLocationAreaCode(cellIdInfo.getLac());
				cellTower.setAge(0);
				googleRequest.addCellTower(cellTower);
			}
		}
		
		try {
//			StringEntity strEntity = new StringEntity(gson.toJson(request));
			StringEntity strEntity = new StringEntity(gson.toJson(googleRequest));
			httpPost.setEntity(strEntity);
			
			HttpResponse response = client.execute(httpPost);
			
			int state = response.getStatusLine().getStatusCode();
			if (state == HttpStatus.SC_OK) {
				HttpEntity httpEntity = response.getEntity();
				if (httpEntity != null) {
					BufferedReader buffer = new BufferedReader(
							new InputStreamReader(httpEntity.getContent()));
					
					StringBuffer sb = new StringBuffer();
					String result = "";
					while ((result = buffer.readLine()) != null) {
						sb.append(result);
					}
					buffer.close();
					
					Log.i("thom", sb.toString());
					
					
					Type type = new TypeToken<GeoLocation>() {}.getType();
					
					location = gson.fromJson(sb.toString(), type);
					
					
//					JsonObject jsonData = new JSONObject(sb.toString());
//					jsonData = jsonData.get("location");
				}
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return location.getLocationInfo();
	}
	
	class GeoLocation {
	    GeoLocationInfo location;

        /**
         * @return the location
         */
        public GeoLocationInfo getLocationInfo() {
            return location;
        }

        /**
         * @param location the location to set
         */
        public void setLocationInfo(GeoLocationInfo location) {
            this.location = location;
        }
	    
	    
	}
}
