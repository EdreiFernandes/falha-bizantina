package rsa;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class RSAKey {
    public static void main(String[] args) {

        // Str original
        // batata frita
        String str = "batata frita";
        System.out.println(str);
        // FIM

        // Str em ASCII
        // 98 97 116 97 116 97 32 102 114 105 116 97
        byte[] ascii = str.getBytes(StandardCharsets.US_ASCII);
        String asciiString = Arrays.toString(ascii);
        System.out.println(asciiString);
        // FIM

        // Str em RSA
        // 55 79 24 79 24 79 315 34 45 265 24 79
        // public keys
        int n = 391;
        int e = 3;

        StringBuilder rsaString = new StringBuilder();
        for (byte m : ascii) {
            // C = M ^ e mod(n)
            rsaString.append((int) Math.pow(m, e) % n + " ");
        }
        System.out.println(rsaString);
        // FIM

        // voltar str em ASCII
        // 98 97 116 97 116 97 32 102 114 105 116 97
        // private keys
        int p = 17;
        int q = 23;
        int d = 235;

        StringBuilder asciiStringAgain = new StringBuilder();
        String[] rsaArray = rsaString.toString().split("\\s+");
        for (String c : rsaArray) {
            // M = C ^ d mod(n)

            BigInteger C = BigInteger.valueOf(Integer.parseInt(c));
            asciiStringAgain.append(C.pow(d).mod(BigInteger.valueOf(n)) + " ");
        }
        System.out.println(asciiStringAgain);
        // FIM

        // voltar str original
        // batata frita
        StringBuilder strAgain = new StringBuilder();
        String[] asciiAgainArray = asciiStringAgain.toString().split("\\s+");
        for (String m : asciiAgainArray) {
            strAgain.append((char) Integer.parseInt(m));
        }
        System.out.println(strAgain);
        // FIM

    }
}
