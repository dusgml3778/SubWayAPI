package com.app.navi.editor;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.app.navi.dto.StationInfo;

@Service
public class DataEditor {

	/** 距離編集 */
	public StationInfo shortDistance(ArrayList<StationInfo> Info) {

		int shortDistance = 0;
		String distance_str = "";
		int distance_num = 0;
		// 一番短い距離を確認する為のインデックス
		int index = 0;
		for (int i = 0; i < Info.size(); i++) {
			if (!Info.get(i).getDistance().isEmpty()) {
				// ex) 文字列なので数字化する前にmは削除する1000m -> 1000
				distance_str = Info.get(i).getDistance().substring(0, Info.get(i).getDistance().length() - 1);
				// 数字化
				distance_num = Integer.parseInt(distance_str);
				// 1km以内
				if (distance_num <= 1000) {
					// 初回の判定時
					if (shortDistance == 0) {
						// 最寄り駅にする。
						shortDistance = distance_num;
						index = i;
					} else {
						if (shortDistance > distance_num) {
							// 比較してもっと短い距離を最寄り駅にする。
							shortDistance = distance_num;
							index = i;
						}
					}
					// 1km以上
				} else {
					// 初回の判定時
					if (shortDistance == 0) {
						// 最寄り駅にする。
						shortDistance = distance_num;
						index = i;
					} else {
						if (shortDistance > distance_num) {
							// 比較してもっと短い距離を最寄り駅にする。
							shortDistance = distance_num;
							index = i;
						}
					}
				}
			}
		}

		StationInfo info = Info.get(index);

		return info;
	}

	/** 電車時間編集 */
	public static String editTrainTime(String text) {

		int x = text.indexOf("車");
		int y = text.indexOf("）")-1;

		return text.substring(x + 1, y);

	}

	/** 時間編集 */
	public static String editWalkTime(String text) {

		return text.substring(0, 1);

	}

	/** 乗り換え編集 */
	public static String editVia(String text) {

		return text.substring(3, 4);
	}
	
	/** 乗り換え駅編集 */
	public static String editStation(String text) {

		return text.replace(",", "---------->");
	}
	
	/** 片道料金編集 */
	public static String editOnewayFare(String text) {
		int x = text.indexOf("：");
		return text.substring(x + 1);
	}
}
