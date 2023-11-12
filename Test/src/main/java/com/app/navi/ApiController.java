package com.app.navi;

import java.util.ArrayList;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.navi.api.latilongtiAPI;
import com.app.navi.api.stationApi;
import com.app.navi.api.ticketAPI;
import com.app.navi.api.walkApi;
import com.app.navi.dto.LatitudeLongitude;
import com.app.navi.dto.ScreenInfo;
import com.app.navi.dto.StationInfo;
import com.app.navi.dto.TicketInfo;
import com.app.navi.dto.WalkInfo;
import com.app.navi.editor.DataEditor;
import com.app.navi.editor.JsonEditor;

/**
 * Handles requests for the application home page.
 */
@Controller
public class ApiController {

	@Autowired
	latilongtiAPI latilongapi;
	@Autowired
	stationApi stationApi;
	@Autowired
	walkApi walkapi;
	@Autowired
	ticketAPI ticketapi;
	@Autowired
	JsonEditor jsonEditor;
	@Autowired
	DataEditor dataEditor;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {

		return "home";
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String Send(@RequestParam String departure, @RequestParam String destination, Model model) {

		Double longitude0 = 0.0;
		Double latitude0 = 0.0;
		Double longitude1 = 0.0;
		Double latitude1 = 0.0;

		try {

			// 出発の軽度緯度情報取得
			String defartureInfo = latilongapi.sendURL(departure);
			// 到着の軽度緯度情報取得
			String destinationlatilongInfo = latilongapi.sendURL(destination);

			// 軽度緯度情報を加工
			ArrayList<LatitudeLongitude> list0 = jsonEditor.editJsonData(departure,defartureInfo, "geometry", "coordinates");
			ArrayList<LatitudeLongitude> list1 = jsonEditor.editJsonData(destination,destinationlatilongInfo, "geometry",
					"coordinates");

			if (!list0.isEmpty() && !list1.isEmpty()) {

				// 出発の軽度緯度を取得
				longitude0 = list0.get(0).getLongitude();
				latitude0 = list0.get(0).getLatitude();

				// 到着の軽度緯度を取得
				longitude1 = list1.get(0).getLongitude();
				latitude1 = list1.get(0).getLatitude();

				// 駅情報を取得
				String stationInfo0 = stationApi.sendURL(longitude0, latitude0);
				String stationInfo1 = stationApi.sendURL(longitude1, latitude1);

				if (!stationInfo0.isEmpty() && !stationInfo1.isEmpty()) {

					// 駅情報を加工
					ArrayList<StationInfo> Info0 = jsonEditor.editJsonData1(stationInfo0);
					ArrayList<StationInfo> Info1 = jsonEditor.editJsonData1(stationInfo1);

					if (!Info0.isEmpty() && !Info1.isEmpty()) {
						// 駅情報取得(駅名、距離)
						StationInfo info0 = dataEditor.shortDistance(Info0);
						StationInfo info1 = dataEditor.shortDistance(Info1);
						// 最寄り駅眼名を取得
						String stationName0 = info0.getStationName();
						String stationName1 = info1.getStationName();
						// 徒歩情報取得
						String walkInfo0 = walkapi.sendURL(departure, stationName0);
						String walkInfo1 = walkapi.sendURL(destination, stationName1);
						// 徒歩情報を加工
						if (!walkInfo0.isEmpty() && !walkInfo1.isEmpty()) {
							ArrayList<WalkInfo> walkinfo0 = jsonEditor.editJsonData2(walkInfo0);
							ArrayList<WalkInfo> walkinfo1 = jsonEditor.editJsonData2(walkInfo1);

							String warkTime0 = DataEditor.editWalkTime(walkinfo0.get(0).getWalkTime());
							String walkDistance0 = walkinfo0.get(0).getWalkDistance();
							String warkTime1 = DataEditor.editWalkTime(walkinfo1.get(0).getWalkTime());
							String walkDistance1 = walkinfo1.get(0).getWalkDistance();

							ArrayList<TicketInfo> ticketInfo = ticketapi.sendURL(stationName0, stationName1);

							ArrayList<ScreenInfo> screenInfo = new ArrayList<ScreenInfo>();
							
							for(int i =0;i<ticketInfo.size();i++) {
								TicketInfo dto = ticketInfo.get(i);
								String distance = dto.getDistance();
								String stations = dto.getStations();
								String via = dto.getVia();
								String fare = dto.getFare();
								int totalTime = Integer.parseInt(dto.getTime()) + Integer.parseInt(warkTime0) + Integer.parseInt(warkTime1);
								String time = Integer.toString(totalTime);
								String onewayfare = dto.getOnewayfare();

								screenInfo.add(new ScreenInfo(stationName0, warkTime0, walkDistance0, stationName1, warkTime1, walkDistance1, distance, stations, via, fare, time, onewayfare));
								
							}
							model.addAttribute("count",screenInfo.size());
							model.addAttribute("departure", departure);
							model.addAttribute("destination", destination);
							model.addAttribute("screenInfo", screenInfo);

						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "home";
	}

}
