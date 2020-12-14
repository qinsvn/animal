package com.ts;

import com.alibaba.fastjson.JSON;
import com.ts.common.utils.Result;

public class Tstest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(System.currentTimeMillis());//testData
		System.out.println(System.currentTimeMillis());//testData
		
		Result r = Result.error("错误");
		new ts().in(r );
	    System.out.println(JSON.toJSONString(r));
	}

}

class ts{
	
	public int in(Result r){
		  r.set(Result.SECCESS, "成功", "11111111111");
		return 0;
	}
}