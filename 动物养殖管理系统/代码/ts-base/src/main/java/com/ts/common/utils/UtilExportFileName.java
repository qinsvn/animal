package com.ts.common.utils;

import java.io.File;
import java.util.Random;

/**
 * 获取excel的命名规则
 * @author Allen
 *
 */
public class UtilExportFileName {
		
	/**
	 * {sys_save_path}/{sys_uploadpath}/随机两位小写字母与数字/随机两位小写字母与数字/upload.txt
	 * @param fileName
	 * @return
	 */
	public static String getName() {
		return "/" + createRandomCharData(2) + "/" + createRandomCharData(2) + "/";
	}
	
	//根据指定长度生成字母和数字的随机数
    //0~9的ASCII为48~57
    //A~Z的ASCII为65~90
    //a~z的ASCII为97~122
    public static String createRandomCharData(int length)
    {
        StringBuilder sb=new StringBuilder();
        Random rand=new Random();//随机用以下三个随机生成器
        Random randdata=new Random();
        int data=0;
        for(int i=0;i<length;i++)
        {
            int index=rand.nextInt(2);
            //目的是随机选择生成数字，大小写字母
            switch(index)
            {
            case 0:
                 data=randdata.nextInt(10);//仅仅会生成0~9
                 sb.append(data);
                break;
            /*case 1:
                data=randdata.nextInt(26)+65;//保证只会产生65~90之间的整数
                sb.append((char)data);
                break;*/
            case 1:
                data=randdata.nextInt(26)+97;//保证只会产生97~122之间的整数
                sb.append((char)data);
                break;
            }
        }
        String result=sb.toString();
        return result;
    }
}
