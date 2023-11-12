package com.app.navi.api;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.app.navi.dto.TicketInfo;
import com.app.navi.editor.DataEditor;

@Service
public class ticketAPI {

	public ArrayList<TicketInfo> sendURL(String departure, String destination) {

		ArrayList<TicketInfo> list = new ArrayList<TicketInfo>();
		
		try {
			// Yahoo 乗車ホームページ
			String URL = "https://transit.yahoo.co.jp/search/result?from=" + departure + "&to="+destination+"&fromgid=&togid";
			Connection conn = Jsoup.connect(URL);

			Document document = conn.get();
			// 距離Elements
			Elements distanceEl = document.select("div#srline li.distance");
			// 乗り換え駅Elements
			Elements transferEl = document.select("div#srline li.transfer");
			// 定期券Elements
			Elements fareEl = document.select("div.detail.commuterPass dd ul li");
			// 片道Elements
			Elements onewayFareEl = document.select("div#srline div.routeSummary li.fare");
			// 駅名Elements
			Elements stationNameEl = document.select("div#srline");


			// 定期券取得
			ArrayList<String> fareList = new ArrayList<String>();
			int count = 0;
			StringBuffer buffer0 = new StringBuffer();
			for (int i = 0; i < fareEl.size(); i++) {
				count++;
				if (count == 3) {
					buffer0.append(fareEl.get(i-2).text());
					fareList.add(buffer0.toString());
					count = 0;
					buffer0.setLength(0);
				}
			}

			for (int i = 1; i <= distanceEl.size(); i++) {
				//各情報Element
				Element routeEl = stationNameEl.get(0).getElementById("route0" + i);
				//各情報の駅名Element
				Elements stationEls = routeEl.getElementsByTag("dt");
				Elements stationEl = stationEls.select("a");
				Elements timeEl = routeEl.select("ul.summary li.time");

				// 乗り換え駅を含めて格納
				StringBuffer buffer1 = new StringBuffer();
				for (int j = 0; j < stationEl.size(); j++) {
					buffer1.append(stationEl.get(j).text());
					buffer1.append(",");
				}
				
				String distance = distanceEl.get(i - 1).text();
				String time = DataEditor.editTrainTime(timeEl.get(0).text());
				String stations = DataEditor.editStation(buffer1.toString().substring(0, buffer1.toString().length() - 1));
				String transfer = DataEditor.editVia(transferEl.get(i - 1).text());
				String fare = fareList.get(i - 1);
				String onewayfare = DataEditor.editOnewayFare(onewayFareEl.get(i - 1).text());
				list.add(new TicketInfo(distance, stations, transfer, fare, time, onewayfare));
			}

		} catch (IOException e) {

		}

		return list;
	}
}
