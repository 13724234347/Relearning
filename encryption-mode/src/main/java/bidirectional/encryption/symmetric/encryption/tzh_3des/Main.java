package bidirectional.encryption.symmetric.encryption.tzh_3des;

import org.apache.commons.codec.binary.Base64;

import java.io.Console;

public class Main {
 
    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        String msg = "tzh20010319";
        System.out.println("【加密前】：" + msg);
        
        //加密
        byte[] secretArr = SecretUtils.encryptMode(msg.getBytes("UTF-8"));
        System.out.println("【加密后】：" + Base64.encodeBase64String(secretArr));

        //解密
        byte[] myMsgArr = SecretUtils.decryptMode(secretArr);  
        System.out.println("【解密后】：" + new String(myMsgArr));
    }
}