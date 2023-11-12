package com.app.navi.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Service;

@Service
public class latilongtiAPI {

	private final String URL = "https://msearch.gsi.go.jp/address-search/AddressSearch?q=";
	
	public String sendURL(String target) throws Exception {
		
		URL url = new URL(URL+target);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("GET"); // http 메서드
		conn.setRequestProperty("Content-Type", "application/json"); // header Content-Type 정보
		conn.setRequestProperty("auth", "myAuth"); // header의 auth 정보
		conn.setDoOutput(true); // 서버로부터 받는 값이 있다면 true

		// 서버로부터 데이터 읽어오기
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		StringBuilder sb = new StringBuilder();
		String line = null;

		while ((line = br.readLine()) != null) { // 읽을 수 있을 때 까지 반복
			sb.append(line);
		}
		
		return sb.toString();
	}
	
}
