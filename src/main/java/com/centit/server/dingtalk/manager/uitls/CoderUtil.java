package com.centit.server.dingtalk.manager.uitls;

import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import net.sf.json.JSONObject;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * <p>加密解密工具类<p>
 * @version 1.0
 * @author li_hao
 * @date 2017年3月13日
 */
public class CoderUtil {
	
	/**
	 * BASE64解密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptBASE64(String key) throws Exception {
		return (new BASE64Decoder()).decodeBuffer(key);
	}

	/**
	 * BASE64加密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptBASE64(byte[] key) throws Exception {
		return (new BASE64Encoder()).encodeBuffer(key);
	}

	/**
	 * MD5加密 生成32位md5码 
	 * @param inStr
	 * @return
	 */
    public static String string2MD5(String inStr){  
        MessageDigest md5 = null;  
        try{  
            md5 = MessageDigest.getInstance("MD5");  
        }catch (Exception e){  
            System.out.println(e.toString());  
            e.printStackTrace();  
            return "";  
        }  
        char[] charArray = inStr.toCharArray();  
        byte[] byteArray = new byte[charArray.length];  
  
        for (int i = 0; i < charArray.length; i++)  
            byteArray[i] = (byte) charArray[i];  
        byte[] md5Bytes = md5.digest(byteArray);  
        StringBuffer hexValue = new StringBuffer();  
        for (int i = 0; i < md5Bytes.length; i++){  
            int val = ((int) md5Bytes[i]) & 0xff;  
            if (val < 16)  
                hexValue.append("0");  
            hexValue.append(Integer.toHexString(val));  
        }  
        return hexValue.toString();  
  
    }  
  
    /**
     * MD5加密解密算法 执行一次加密，两次解密    
     * @param inStr
     * @return
     */
    public static String convertMD5(String inStr){  
        char[] a = inStr.toCharArray();  
        for (int i = 0; i < a.length; i++){  
            a[i] = (char) (a[i] ^ 't');  
        }  
        String s = new String(a);  
        return s;  
    }
    
    /**
     * Cipher对称加密： AES或DES加密
     * @param content 加密内容
     * @param codeType加密类型  AES或DES
     * @return
     */
    public static String cipherCode(String content,String codeType){
    	String base64str = null;
		try {
			Cipher cipher = Cipher.getInstance(codeType);//Cipher：提供加密的类，"AES" 表示加密使用的算法
			SecretKey key = KeyGenerator.getInstance(codeType).generateKey();//秘钥
	        cipher.init(Cipher.ENCRYPT_MODE, key);//用密钥初始化此 Cipher，   Cipher.ENCRYPT_MODE 加密模式
	        byte[] result = cipher.doFinal(content.getBytes());//执行加密的操作,返回值为加密后的结果
	        String keynum = parseByte2HexStr(key.getEncoded());//秘钥后转String存储
	        
	        JSONObject jsonContent = new JSONObject();
	        jsonContent.put("keynum", keynum);
	        jsonContent.put("codeContent", parseByte2HexStr(result));
	        base64str = encryptBASE64(jsonContent.toString().getBytes());//秘钥和密文再用base64进行加密
		} catch (Exception e) {
			e.printStackTrace();
		}
		return base64str;
	}
	
    /**
     * Cipher对称解密： AES或DES解密
     * @param map 加密内容codeContent，加密key
     * @param codeType加密类型 AES或DES
     * @return
     */
    public static String cipherDeCode(String base64str,String codeType){
		String content = null;
		try {
			String jsonStr = new String(decryptBASE64(base64str));//base64解密
			JSONObject jsonContent = JSONObject.fromObject(jsonStr);
			
			String keynum = jsonContent.getString("keynum");
			byte[] encodedKey = parseHexStr2Byte(keynum);
			SecretKey key = new SecretKeySpec(encodedKey, codeType);//获取秘钥

			String codeContent = jsonContent.get("codeContent").toString();//获取密文
			Cipher cipher = Cipher.getInstance(codeType);
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] contentBt = cipher.doFinal(parseHexStr2Byte(codeContent));//密文解密
			content = new String(contentBt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}
	
	/**将二进制转换成16进制
	 * @param buf
	 * @return
	 */ 
	public static String parseByte2HexStr(byte buf[]) { 
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
	
	/**将16进制转换为二进制
	 * @param hexStr
	 * @return
	 */ 
	public static byte[] parseHexStr2Byte(String hexStr) { 
        if (hexStr.length() < 1) 
                return null; 
        byte[] result = new byte[hexStr.length()/2]; 
        for (int i = 0;i< hexStr.length()/2; i++) { 
                int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16); 
                int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16); 
                result[i] = (byte) (high * 16 + low); 
        } 
        return result; 
	}
    
    
	
	public static void main(String[] args) throws Exception {
		
		String encryptBASE64 = encryptBASE64(parseHexStr2Byte("http://tool.chacuo.net/cryptaes"));
		System.out.println(encryptBASE64);
	}
    
}
