package com.label.common.module.calculus;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 * @author liugang
 *
 */
public class EncryptUtil{
	public static final String SHA256 = "SHA-256";
	public static final String AES = "AES";

	/** 编码格式；默认null为UTF-8 */
	public static Charset charset = Charset.forName("UTF-8");
	/** AES */
	public static int keysizeAES = 128;
	public static EncryptUtil me;

	/**
	 * 单例
	 */
	private EncryptUtil(){}

	public static EncryptUtil getInstance() {
		if (me == null) {
			me = new EncryptUtil();
		}
		return me;
	}

	/**
	 * 解密
	 * @param msg 16进制字符串
	 * @param key
	 * @return 
	 * @throws Exception
	 */
	public String AESDecrypt(String msg, String key) throws Exception{
		 byte[] decryptFrom = parseHexStr2Byte(msg);  
		 Cipher cipher = Cipher.getInstance(AES);// 创建密码器 
		 cipher.init(Cipher.DECRYPT_MODE, genKey(key));// 初始化
		 byte[] result = cipher.doFinal(decryptFrom); 
		 return new String(result); //解密
	}
	
	/**
	 * 加密
	 * @param msg
	 * @param key
	 * @return 16进制字符串
	 * @throws Exception
	 */
	public String AESEncrypt(String msg, String key) throws Exception{
		 Cipher cipher = Cipher.getInstance(AES);// 创建密码器 
		 byte[] byteContent = msg.getBytes(charset); 
		 cipher.init(Cipher.ENCRYPT_MODE, genKey(key));// 初始化  
		 byte[] result = cipher.doFinal(byteContent);  
		 return parseByte2HexStr(result); // 加密  
	}
	
	/** 
     * 根据密钥获得 SecretKeySpec 
     * @return 
	 * @throws NoSuchAlgorithmException 
     */  
    private SecretKeySpec genKey(String key) throws NoSuchAlgorithmException{  
	    KeyGenerator kgen = KeyGenerator.getInstance(AES);  
	    SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");     
	    secureRandom.setSeed(key.getBytes(charset));     
	    kgen.init(keysizeAES, secureRandom);  
	    SecretKey secretKey = kgen.generateKey();  
	    byte [] enCodeFormat = secretKey.getEncoded();  
	    return new SecretKeySpec(enCodeFormat, AES);  
    }
	
    /** 
     * 将二进制转换成16进制 
     *  
     * @param buf 
     * @return 
     */  
    private static String parseByte2HexStr(byte buf[]) {  
        StringBuffer sb = new StringBuffer();  
        for (int i = 0; i < buf.length; i++) {  
            String hex = Integer.toHexString(buf[i] & 0xFF);  
            if (hex.length() == 1) {  
                hex = '0' + hex;  
            }  
            sb.append(hex.toUpperCase());  
        }  
        return sb.toString();  
    }  
  
    /** 
     * 将16进制转换为二进制 
     *  
     * @param hexStr 
     * @return 
     */  
    private static byte[] parseHexStr2Byte(String hexStr) {  
        if (hexStr.length() < 1)  
            return null;  
        byte[] result = new byte[hexStr.length() / 2];  
        for (int i = 0; i < hexStr.length() / 2; i++) {  
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);  
            int low  = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);  
            result[i] = (byte) (high * 16 + low);  
        }  
        return result;  
    }
	
    /**
     * 
     * @param msg
     * @param key 密钥
     * @return
     * @throws NoSuchAlgorithmException
     */
    public String sha256(String msg, String key) throws NoSuchAlgorithmException{
    	return sha256(msg+ key);
    }
    
    /**
	 * 使用 SHA256 将报文加签
	 * @param msg
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
    public String sha256(String msg) throws NoSuchAlgorithmException{
    	MessageDigest messageDigest = getDigest(SHA256);
        messageDigest.update(msg.getBytes(charset));
        byte byteBuffer[] = messageDigest.digest(); 
        return parseByte2HexStr(byteBuffer);
    }
  
    private MessageDigest getDigest(final String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (final NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
    }
    
    public static String generatorKey() throws NoSuchAlgorithmException{
    	byte[] bytes = SecureRandom.getInstance("SHA1PRNG").generateSeed(20);
    	return parseByte2HexStr(bytes);
    }
    
	public static void main(String ...arg) throws Exception{
//	    String a = UUID.randomUUID().toString().replaceAll("-", "").substring(12);
	   // System.out.println(a);
		//String message = "{\"APP_HEAD\": {\"PAGE_NO\": \"1\",\"PAGE_SIZE\": \"10\",},\"SYS_HEAD\": {\"CHNL_DATE\": \"20170630\",\"CHNL_SEQ\": '"+a+"',\"CHNL_ID\":\"23\",\"IP_ADDR\": \"127.0.0.1\",\"TRAN_CODE\": \"TBUT0038\",\"CHNL_TIME\": \"120911\"},\"BODY\": {\"LSZH_TXNAMT\": \"100\",\"LSFH_TXNAMT\": \"\",\"GDZH_TXNAMT\": \"\",\"GDFH_TXNAMT\": \"100\",\"GSZH_TXNAMT\": \"\",\"GSFH_TXNAMT\": \"\",\"DZZH_TXNAMT\": \"\",\"DZFH_TXNAMT\": \"\",\"LOGINNAME\": \"z13333\",\"NAME\": \"cj301\",\"BRANCHNUM\": \"98\",\"INPUT_FLAG\": \"1\",\"CARD_NO\": \"\"}}";
	   //String message = "{\"APP_HEAD\": {\"PAGE_NO\": \"\",\"PAGE_SIZE\": \"\",},\"SYS_HEAD\": {\"CHNL_DATE\": \"20170629\",\"CHNL_SEQ\": \"LE968193779409481338\",\"CHNL_ID\": \"23\",\"IP_ADDR\": \"127.0.0.1\",\"TRAN_CODE\": \"TBUT0039\",\"CHNL_TIME\": \"161836\"},\"BODY\": {\"LSZH_TXNAMT\": \"0.00\",\"LSFH_TXNAMT\": \"0.00\",\"GDZH_TXNAMT\": \"0.00\",\"GDFH_TXNAMT\": \"0.00\",\"GSZH_TXNAMT\": \"0.00\",\"GSFH_TXNAMT\": \"0.00\",\"DZZH_TXNAMT\": \"40.00\",\"DZFH_TXNAMT\": \"0.00\",\"LOGINNAME\": \"z13333\",\"NAME\": \"cj301\",\"BRANCHNUM\": \"99\",\"CHNLSEQ\": \"LE431685302115179741\",\"CHNLDATE\": \"20170629\",\"TXNTIME\": \"161836\",\"PRESEQ\": \"\",\"INPUT_FLAG\": \"1\",\"CARD_NO\": \"\"}}";
	    String message = "{\"APP_HEAD\": {\"PAGE_NO\": \"1\",\"PAGE_SIZE\": \"10\"},\"SYS_HEAD\": {\"CHNL_DATE\": \"20171213\",\"CHNL_SEQ\": \"20171213\",\"CHNL_ID\":\"22\",\"IP_ADDR\":\"127.0.0.1\",\"TRAN_CODE\":\"TBUT0040\",\"CHNL_TIME\": \"120911\"},\"BODY\": {\"TXNAMT\": \"100\",\"ACCTNO\":\"66660000000007866\",\"TIMEVALID\":\"\",\"INTEGRALTYPE\":\"\",\"ACTIVITYCODE\": \"\",\"REMARKINFO\": \"测试联盟tbut0040\"}}";
		String key = "18CAF29E3CDE0EB6826A19B7697834F49BE366FD";
        String sing = EncryptUtil.getInstance().sha256(message, key);
        System.out.println("签名: " + sing);
        
        String msg = EncryptUtil.getInstance().AESEncrypt(message, key);
        System.out.println("密文  : " + msg);
        
        
		//String msg="B042D31350F9B9DC0056A6CE68F683AFC3DF332A28C2FBCE5270E81EB4C2FFA12DA58A357960AEE3FE75B49643CA143B7A0D5DD7D7333D60BF0A909747872ABE8178CC83FB48CFF8175F088E135744663EE5A1CA668554CCFF05EF603D151D6BB6BBD662DE5F3AAA11C29048C96BA150A0C40E76C961A61918D3BFC0F5800B3BAC60B1279A28D1686F6DB772591E39F251C6BE24D422605820D4C8A86206521793B1093FB402109EC10277E74873FE9E62CA8740D6C722DC5437F4FA75799F1F4049F78223EECC1E3E4E4960B7021AC215";
		//String key="18CAF29E3CDE0EB6826A19B7697834F49BE366FD";
        String messageStr = EncryptUtil.getInstance().AESDecrypt(msg, key);
        System.out.println("明文 : " + messageStr);
        
        String decryptQm = EncryptUtil.getInstance().sha256(messageStr, key);
        System.out.println("签名 decryptQm: " + decryptQm);
       /* 
        if(sing.equals(decryptQm)){
        	System.out.println("签名一致");
        } else {
        	System.out.println("非法报文");
        }*/
	}
}
