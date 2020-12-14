package com.ts.common.utils;

import org.springframework.util.StringUtils;

/**
 * 网页html工具类，对比后，stripHtml好用一些
 * @author jolley
 */
public class UtilHtml {

//	public static String delHTMLTag(String htmlStr) {
//		  if(StringUtils.isEmpty(content)) {
//			  return null;
//		  }
//        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
//        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
//        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式
//        String regEx_space = "\\s*|\t|\r|\n";//定义空格回车换行符
//
//        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
//        Matcher m_script=p_script.matcher(htmlStr);
//        htmlStr=m_script.replaceAll(""); //过滤script标签
//
//        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
//        Matcher m_style=p_style.matcher(htmlStr);
//        htmlStr=m_style.replaceAll(""); //过滤style标签
//
//        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
//        Matcher m_html=p_html.matcher(htmlStr);
//        htmlStr=m_html.replaceAll(""); //过滤html标签
//
//        Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
//        Matcher m_space = p_space.matcher(htmlStr);
//        htmlStr = m_space.replaceAll(""); // 过滤空格回车标签
//
//        return htmlStr.trim().replaceAll("&nbsp;", ""); //返回文本字符串
//    }
	
	public static String stripHtml(String content) { 
		if(StringUtils.isEmpty(content)) {
			return null;
		}
		
		// <p>段落替换为换行 
		content = content.replaceAll("<p .*?>", "\r\n"); 
		// <br><br/>替换为换行 
		content = content.replaceAll("<br\\s*/?>", "\r\n"); 
		// 去掉其它的<>之间的东西 
		content = content.replaceAll("\\<.*?>", ""); 
		// 还原HTML 
		// content = HTMLDecoder.decode(content); 
		return content.replaceAll("&nbsp;", ""); 
	}
	
	public static void main(String []args) {
		String str = "<!DOCTYPE html>"
				+ "<!--STATUS OK-->"
				+ "<div class=\"s_tab\" id=\"s_tab\">"
		    + "<b>网页</b><a href=\"http://news.baidu.com/ns?cl=2&rn=20&tn=news&word=\" wdfield=\"word\"  onmousedown=\"return c({'fm':'tab','tab':'news'})\">新闻</a><a href=\"http://tieba.baidu.com/f?kw=&fr=wwwt\" wdfield=\"kw\"  onmousedown=\"return c({'fm':'tab','tab':'tieba'})\">贴吧</a><a href=\"http://zhidao.baidu.com/q?ct=17&pn=0&tn=ikaslist&rn=10&word=&fr=wwwt\" wdfield=\"word\"  onmousedown=\"return c({'fm':'tab','tab':'zhidao'})\">知道</a><a href=\"http://music.baidu.com/search?fr=ps&ie=utf-8&key=\" wdfield=\"key\"  onmousedown=\"return c({'fm':'tab','tab':'music'})\">音乐</a><a href=\"http://image.baidu.com/search/index?tn=baiduimage&ps=1&ct=201326592&lm=-1&cl=2&nc=1&ie=utf-8&word=\" wdfield=\"word\"  onmousedown=\"return c({'fm':'tab','tab':'pic'})\">图片</a><a href=\"http://v.baidu.com/v?ct=301989888&rn=20&pn=0&db=0&s=25&ie=utf-8&word=\" wdfield=\"word\"   onmousedown=\"return c({'fm':'tab','tab':'video'})\">视频</a><a href=\"http://map.baidu.com/m?word=&fr=ps01000\" wdfield=\"word\"  onmousedown=\"return c({'fm':'tab','tab':'map'})\">地图</a><a href=\"http://wenku.baidu.com/search?word=&lm=0&od=0&ie=utf-8\" wdfield=\"word\"  onmousedown=\"return c({'fm':'tab','tab':'wenku'})\">文库</a><a href=\"//www.baidu.com/more/\"  onmousedown=\"return c({'fm':'tab','tab':'more'})\">更多»</a>"
		+ "</div>"

		+ "<div class=\"qrcodeCon\">"
			+ "<div id=\"qrcode\">"
				+ "<div class=\"qrcode-item qrcode-item-1\">"
					+ "<div class=\"qrcode-img\"></div>"
					+ "<div class=\"qrcode-text\">"
							+ "<p><b>手机百度</b></p>"
					+ "</div>"
				+ "</div>"
			+ "</div>"
		+ "</div>"
		+ "<div id=\"ftCon\">"

		+ "<div class=\"ftCon-Wrapper\"><div id=\"ftConw\"><p id=\"lh\"><a id=\"setf\" href=\"//www.baidu.com/cache/sethelp/help.html\" onmousedown=\"return ns_c({'fm':'behs','tab':'favorites','pos':0})\" target=\"_blank\">把百度设为主页</a><a onmousedown=\"return ns_c({'fm':'behs','tab':'tj_about'})\" href=\"http://home.baidu.com\">关于百度</a><a onmousedown=\"return ns_c({'fm':'behs','tab':'tj_about_en'})\" href=\"http://ir.baidu.com\">About&nbsp;&nbsp;Baidu</a><a onmousedown=\"return ns_c({'fm':'behs','tab':'tj_tuiguang'})\" href=\"http://e.baidu.com/?refer=888\">百度推广</a></p><p id=\"cp\">&copy;2017&nbsp;Baidu&nbsp;<a href=\"http://www.baidu.com/duty/\" onmousedown=\"return ns_c({'fm':'behs','tab':'tj_duty'})\">使用百度前必读</a>&nbsp;<a href=\"http://jianyi.baidu.com/\" class=\"cp-feedback\" onmousedown=\"return ns_c({'fm':'behs','tab':'tj_homefb'})\">意见反馈</a>&nbsp;京ICP证030173号&nbsp;<i class=\"c-icon-icrlogo\"></i>&nbsp;<a id=\"jgwab\"  target=\"_blank\" href=\"http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=11000002000001\">京公网安备11000002000001号</a>&nbsp;<i class=\"c-icon-jgwablogo\"></i></p></div></div></div>"
		        + "<div id=\"wrapper_wrapper\">"
		        + "</div>"
		    + "</div>"
		    + "<div class=\"c-tips-container\" id=\"c-tips-container\"></div>"
		    
		    + "<script>"
		        + "window.__async_strategy=2;"
+ "		        //window.__switch_add_mask=false;"
		    + "</script>";
		
//		System.out.println("jolley>> " + delHTMLTag(str));
		System.out.println("jolley>> " + stripHtml(str));
		
	}
}
