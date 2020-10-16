package rsa;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class RSACrypto {
    public static void main(String[] args) {
        RSAKey rsaKey = new RSAKey();

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
        BigInteger n = rsaKey.getN();
        BigInteger e = rsaKey.getE();

        StringBuilder rsaString = new StringBuilder();
        for (byte m : ascii) {
            // C = M ^ e mod(n)
            BigInteger M = BigInteger.valueOf(m);
            rsaString.append(M.modPow(e, n).toString() + " ");
        }
        System.out.println(rsaString);
        // FIM

        // voltar str em ASCII
        // 98 97 116 97 116 97 32 102 114 105 116 97
        BigInteger d = rsaKey.getD();

        StringBuilder asciiStringAgain = new StringBuilder();
        String[] rsaArray = rsaString.toString().split("\\s+");

        for (String c : rsaArray) {
            // M = C ^ d mod(n)
            BigInteger C = new BigInteger(c);
            asciiStringAgain.append(C.modPow(d, n) + " ");
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
