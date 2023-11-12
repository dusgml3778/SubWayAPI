package com.app.navi.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class walkApi {
	
	@Value("${google-api}")
	private String APIKEY;
	
	public String sendURL(String departure , String station) throws Exception {
		
		String URL = "https://maps.googleapis.com/maps/api/directions/json?origin="+departure+"&destination="+station+"&key="+APIKEY;

		URL url = new URL(URL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("GET"); 
		conn.setRequestProperty("Content-Type", "application/json"); 
		conn.setRequestProperty("auth", "myAuth"); 
		conn.setDoOutput(true); 

		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		StringBuilder sb = new StringBuilder();
		String line = null;

		while ((line = br.readLine()) != null) { 
			sb.append(line);
		}

		return sb.toString();
	}
}
