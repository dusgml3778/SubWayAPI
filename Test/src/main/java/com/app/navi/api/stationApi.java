package com.app.navi.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Service;

@Service
public class stationApi {
	
	private final String URL = "https://express.heartrails.com/api/json?method=getStations";

	public String sendURL(Double longitude, Double latitude) throws Exception {

		URL url = new URL(URL + "&x=" + longitude + "&y=" + latitude);
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
