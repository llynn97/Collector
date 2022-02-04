package SweetredBeans.collector.domain.user;

import java.security.MessageDigest;

public class SHA256 {

    public static String encrypt(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(plainText.getBytes());
            byte byteDate[] = md.digest();

            StringBuffer sb = new StringBuffer();
            for(int i=0; i<byteDate.length; i++) {
                sb.append(Integer.toString((byteDate[i] & 0xff) + 0x100, 16)
                        .substring(1));
            }

            StringBuffer hexString = new StringBuffer();
            for(int i=0; i<byteDate.length; i++) {
                String hex = Integer.toHexString(0xff & byteDate[i]);
                if(hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();

        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
