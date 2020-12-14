package com.ts.common.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/**
 * 网络工具类，参考：
 * http://www.cnblogs.com/nihaorz/p/5334208.html
 * http://www.jianshu.com/p/ca8a982a116b
 * http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0106/2275.html（推荐）
 * @author jolley
 */
public class UtilNetwork {

	private static Logger log = LoggerFactory.getLogger(UtilNetwork.class);
	
	public static String doGet(String url) {
		return doGet(url, 0);
	}
	
	/**
	 * GET请求数据
	 * @param url
	 * @return
	 */
	public static String doGet(String url, int socketTimeout) {
		String respData = null;
		
		long sn =System.currentTimeMillis();
		
		try {
			if(!url.startsWith("http://") && !url.startsWith("https://")) {
				url = "http://" + url;
			}
			
			log.info("jolley>> sn: {}, doGet url: {}", sn, url);
			OkHttpClient client = new OkHttpClient();
			if(socketTimeout > 0) {
				OkHttpClient.Builder builder = client.newBuilder();
				builder.connectTimeout(socketTimeout, TimeUnit.SECONDS);
				client = builder.build();
			}
			
			Request request = new Request.Builder()
					.url(url)
					.build();
			Response response = client.newCall(request).execute();
			
			if(!response.isSuccessful()) {
				throw new IOException("服务器端错误: " + response);
			}
//			Headers responseHeaders = response.headers();
//			for(int i = 0; i < responseHeaders.size(); i++) {
//				System.out.println("jolley>> " + responseHeaders.name(i) + ": " + responseHeaders.value(i));
//			}
			
			respData = response.body().string();
//			byte[] responseBytes = response.body().bytes();
//			respData = new String(responseBytes,"UTF-8");
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		if(respData != null && respData.length() > 400) {
			log.info("jolley>> sn: {}, doGet url: {}, response: {} ...", sn, url, respData.substring(0, 400));
		}
		else {
			log.info("jolley>> sn: {}, doGet url: {}, response: {}", sn, url, respData);
		}
		return respData;
	}
	
	public static String doPost(String url, String body) {
		return doPost(url, body, null, 0);
	}
	/**
	 * POST提交数据
	 * @param url
	 * @param body
	 * @param meiatype
	 * @param socketTimeout
	 * @return
	 */
	public static String doPost(String url, String body, String meiatype, int socketTimeout) {
		String respData = null;
		
		long sn =System.currentTimeMillis();
		try {
			if(!url.startsWith("http://") && !url.startsWith("https://")) {
				url = "http://" + url;
			}
			
			log.info("jolley>> sn: {}, doPost url: {}, body: {}", sn, url, body);
			OkHttpClient client = new OkHttpClient();
			if(socketTimeout > 0) {
				OkHttpClient.Builder builder = client.newBuilder();
				builder.connectTimeout(socketTimeout, TimeUnit.SECONDS);
				client = builder.build();
			}
			
			if(StringUtils.isEmpty(meiatype)) {
				meiatype = "text/plain; charset=utf-8";
			}
			MediaType MEDIA_TYPE = MediaType.parse(meiatype);
			RequestBody stringBody = RequestBody.create(MEDIA_TYPE, body == null ? "" : body);
			
			Request request = new Request.Builder()
					.url(url)
					.post(stringBody)
					.build();
			Response response = client.newCall(request).execute();
			
			if(!response.isSuccessful()) {
				throw new IOException("服务器端错误: " + response);
			}
//			Headers responseHeaders = response.headers();
//			for(int i = 0; i < responseHeaders.size(); i++) {
//				System.out.println("jolley>> " + responseHeaders.name(i) + ": " + responseHeaders.value(i));
//			}
			
			respData = response.body().string();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		if(respData != null && respData.length() > 400) {
			log.info("jolley>> sn: {}, doPost url: {}, response: {} ...", sn, url, respData.substring(0, 400));
		}
		else {
			log.info("jolley>> sn: {}, doPost url: {}, response: {}", sn, url, respData);
		}
		return respData;
	}
	
	public static String doPostForm(String url, Map<String, String> map) {
		return doPostForm(url, map, 0);
	}
	/**
	 * POST表单提交数据
	 * @param url
	 * @param map
	 * @param socketTimeout 请求超时时间，单位秒
	 * @return
	 */
	public static String doPostForm(String url, Map<String, String> map, int socketTimeout) {
		String respData = null;
		
		long sn =System.currentTimeMillis();
		try {
			if(!url.startsWith("http://") && !url.startsWith("https://")) {
				url = "http://" + url;
			}
			
			String body = "";
			if(map != null) {
				for(Map.Entry<String, String> entry : map.entrySet()) {
					body += entry.getKey() + " => " + entry.getValue() + "    ";
				}
			}
			log.info("jolley>> sn: {}, doPost url: {}, body: {}", sn, url, body);
			OkHttpClient client = new OkHttpClient();
			if(socketTimeout > 0) {
				OkHttpClient.Builder builder = client.newBuilder();
				builder.connectTimeout(socketTimeout, TimeUnit.SECONDS);
				client = builder.build();
			}
			
			FormBody.Builder builder = new FormBody.Builder();
			if(map != null) {
				for(Map.Entry<String, String> entry : map.entrySet()) {
					builder.add(entry.getKey(), entry.getValue());
				}
			}
			RequestBody formBody = builder.build();
			
			Request request = new Request.Builder()
					.url(url)
					.post(formBody)
					.build();
			Response response = client.newCall(request).execute();
			
			if(!response.isSuccessful()) {
				throw new IOException("服务器端错误: " + response);
			}
//			Headers responseHeaders = response.headers();
//			for(int i = 0; i < responseHeaders.size(); i++) {
//				System.out.println("jolley>> " + responseHeaders.name(i) + ": " + responseHeaders.value(i));
//			}
			
			respData = response.body().string();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		if(respData != null && respData.length() > 400) {
			log.info("jolley>> sn: {}, doPost url: {}, response: {} ...", sn, url, respData.substring(0, 400));
		}
		else {
			log.info("jolley>> sn: {}, doPost url: {}, response: {}", sn, url, respData);
		}
		return respData;
	}
	
	/**
	 * POST提交大数据
	 * @param url
	 * @param body 大于1M数据
	 * @socketTimeout
	 * @return
	 */
	public static String doPostStream(String url, String body, int socketTimeout) {
		String respData = null;
		
		long sn =System.currentTimeMillis();
		try {
			if(!url.startsWith("http://") && !url.startsWith("https://")) {
				url = "http://" + url;
			}
			
			final String streamBody = body == null ? "" : body;
			
			log.info("jolley>> sn: {}, doPost url: {}, body: {} ...", sn, url, streamBody.substring(0, 400));
			OkHttpClient client = new OkHttpClient();
			if(socketTimeout > 0) {
				OkHttpClient.Builder builder = client.newBuilder();
				builder.connectTimeout(socketTimeout, TimeUnit.SECONDS);
				client = builder.build();
			}
			
			final MediaType MEDIA_TYPE = MediaType.parse("text/x-markdown; charset=utf-8");
			RequestBody requestBody = new RequestBody() {
				@Override
				public MediaType contentType() {
					return MEDIA_TYPE;
				}
				@Override
				public void writeTo(BufferedSink sink) throws IOException {
					sink.writeUtf8(streamBody);
				}
				@Override
				public long contentLength() throws IOException {
					return streamBody.length();
				}
			};
			
			Request request = new Request.Builder()
					.url(url)
					.post(requestBody)
					.build();
			Response response = client.newCall(request).execute();
			
			if(!response.isSuccessful()) {
				throw new IOException("服务器端错误: " + response);
			}
//			Headers responseHeaders = response.headers();
//			for(int i = 0; i < responseHeaders.size(); i++) {
//				System.out.println("jolley>> " + responseHeaders.name(i) + ": " + responseHeaders.value(i));
//			}
			
			respData = response.body().string();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		if(respData != null && respData.length() > 400) {
			log.info("jolley>> sn: {}, doPost url: {}, response: {} ...", sn, url, respData.substring(0, 400));
		}
		else {
			log.info("jolley>> sn: {}, doPost url: {}, response: {}", sn, url, respData);
		}
		return respData;
	}
	
	/**
	 * 上传文件
	 * @param url
	 * @param filePath
	 * @param meiatype 
	 * text/x-markdown; charset=utf-8
	 * application/json; charset=utf-8
	 * image/png
	 * 
	 * @return
	 */
	public static String upload(String url, String filePath, String meiatype) {
		return upload(url, null, filePath, meiatype);
	}
	
	/**
	 * 提交分块请求
	 * @param url
	 * @param mapHeads
	 * @param filePath
	 * @param meiatype 
	 * text/x-markdown; charset=utf-8
	 * application/json; charset=utf-8
	 * image/png
	 * 
	 * @return
	 */
	public static String upload(String url, Map<String, String> mapHeads, String filePath, String meiatype) {
		String respData = null;
		
		long sn =System.currentTimeMillis();
		try {
			log.info("jolley>> sn: {}, upload url: {}, filePath: {}", sn, url, filePath);
			File file = new File(filePath);
		    if (!file.exists() || !file.isFile()) {
		    	throw new IOException("文件不存在");
		    }
		    
		    OkHttpClient client = new OkHttpClient();
		    
		    if(StringUtils.isEmpty(meiatype)) {
				meiatype = "text/x-markdown; charset=utf-8";
			}
		    MediaType MEDIA_TYPE = MediaType.parse(meiatype);
		    RequestBody fileBody = RequestBody.create(MEDIA_TYPE, file);
		    
		    MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
		    if(mapHeads != null) {
		    	for(String key : mapHeads.keySet()) {
		            builder.addFormDataPart(key, mapHeads.get(key));
		        }
		    }
		    String filename = filePath.split("/")[filePath.split("/").length - 1];
	    	builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"file\";filename=\"" + filename + "\""), fileBody);
		    RequestBody requestBody = builder.build();
		    
		    Request request = new Request.Builder()
		            .header("Authorization", "Client-ID " + "---.123.---")
		            .url(url)
		            .post(requestBody)
		            .build();
		        
		    Response response = client.newCall(request).execute();
		    
		    if(!response.isSuccessful()) {
				throw new IOException("服务器端错误: " + response);
			}
//			Headers responseHeaders = response.headers();
//			for(int i = 0; i < responseHeaders.size(); i++) {
//				System.out.println("jolley>> " + responseHeaders.name(i) + ": " + responseHeaders.value(i));
//			}
			
			respData = response.body().string();
		} catch(Exception e) {
			e.printStackTrace();
		}
	    
		log.info("jolley>> sn: {}, doPost response: {}", sn, respData);
		return respData;
	}
	
	/**
	 * 下载文件
	 * @param url
	 * @param path 以/结尾表示目录，否则表示文件
	 * @return 成功返回下载路径，失败null
	 */
	public static String download(String url, String path) {
		String respData = null;
		
		long sn =System.currentTimeMillis();
        try {
			log.info("jolley>> sn: {}, download url: {} to path: {}", sn, url, path);
			OkHttpClient client = new OkHttpClient();
			
			Request request = new Request.Builder()
					.url(url)
					.build();
			Response response = client.newCall(request).execute();
			
			if(!response.isSuccessful()) {
				throw new IOException("服务器端错误: " + response);
			}
			
			// 下载的文件名
        	String filename = null;
			Headers responseHeaders = response.headers();
			for(int i = 0; i < responseHeaders.size(); i++) {
//				System.out.println("jolley>> " + responseHeaders.name(i) + ": " + responseHeaders.value(i));
				if("Content-Type".equalsIgnoreCase(responseHeaders.name(i))) {
            		if(!"text/plain".equalsIgnoreCase(responseHeaders.value(i))) {
                		filename = UUID.randomUUID().toString();
            		}
            	}
            	else if("Content-disposition".equalsIgnoreCase(responseHeaders.name(i))) {
            		String value = responseHeaders.value(i);
        			// 取文件名
//            		if(value.indexOf("filename=\"") > 0) {
//            			filename = value.substring(value.indexOf("filename=\"") + 10);
//            			if(filename.indexOf("\"") > 0) {
//            				filename.substring(0, filename.indexOf("\""));
//            			}
//            		}
            		// 取后缀名
            		if(value.lastIndexOf(".") > 0) {
            			String suffix = value.substring(value.lastIndexOf("."));
            			filename += suffix.replace("\"", "");
            		}
            		break;
            	}
			}
			
            
            if(filename != null) {
                BufferedOutputStream bw = null;
                try {
                	String pathName = path;
                	if(pathName.endsWith("/")) {
	                	pathName += filename;
                	}
                    // 创建文件对象
                    File f = new File(pathName);
                    // 创建文件路径
                    if(!f.getParentFile().exists()) {
                        f.getParentFile().mkdirs();
                    }
                    // 写入文件
                    bw = new BufferedOutputStream(new FileOutputStream(pathName));
                    bw.write(response.body().bytes());
                    
                    respData = pathName;
                } catch (Exception e) {
                	e.printStackTrace();
                } finally {
                    try {
                        if (bw != null)
                            bw.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
            	respData = response.body().string();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		
        log.info("jolley>> sn: {}, doPost response: {}", sn, respData);
		return respData;
	}
	
	public static void main(String []args) {
//		String resp = doGet("www.baidu.com");
//		System.out.println("jolley>> resp: " + resp);
		
		// https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=wxb97feda134b21777&corpsecret=AhjuuzQYfLnOd7dl6hyVRdgu2r0IF7AApKp-6miKXU_5Qd9mak2p9oXDL-OquTSA
//		String access_token = "yq8zhCRe9pyHmTgDk7nvx3dfisKQqGgLlBZwRf6znqfnRIT5vavNUSLREAAXVqff";
//		String url = String.format("https://qyapi.weixin.qq.com/cgi-bin/media/upload?access_token=%s&type=voice", access_token);
//		
//		String resp = UtilNetwork.upload(url, "/Users/chenjolley/Downloads/a6.mp3", null);
//		System.out.println("jolley>> " + resp);
	}

}
