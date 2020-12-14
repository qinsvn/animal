package com.label.service.web;

import java.util.Map;

import com.label.common.constant.UpmsResult;

/**
 * 设备service
 * @author jolley
 *
 */
public interface StockDataService extends CommonService {
	public UpmsResult exportStockData(Map<String, Object> params) ;
}
