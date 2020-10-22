package rsa;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class RSAManager {
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public RSAManager() {
        privateKey = new PrivateKey();
        publicKey = new PublicKey(privateKey);
    }

    public String EncryptMessage(String _message, PublicKey _publicKey) {
        byte[] ascii = _message.getBytes(StandardCharsets.US_ASCII);

        StringBuilder rsaMessage = new StringBuilder();
        for (byte m : ascii) {
            // C = M ^ e mod(n)
            String C = BigInteger.valueOf(m).modPow(_publicKey.GetE(), _publicKey.GetN()).toString();
            rsaMessage.append(C + " ");
        }

        return rsaMessage.toString();
    }

    public String DecryptMessage(String _message) {
        // multiplicative inverse
        BigInteger d = publicKey.GetE().modInverse(publicKey.GetTot());

        StringBuilder ascii = new StringBuilder();
        String[] rsaArray = _message.toString().split("\\s+");

        for (String c : rsaArray) {
            BigInteger C = new BigInteger(c);
            // M = C ^ d mod(n)
            ascii.append(C.modPow(d, publicKey.GetN()) + " ");
        }

        StringBuilder decryptedMessage = new StringBuilder();
        String[] asciiAgainArray = ascii.toString().split("\\s+");
        for (String m : asciiAgainArray) {
            decryptedMessage.append((char) Integer.parseInt(m));
        }

        return decryptedMessage.toString();
    }

    public PublicKey GetPublicKey() {
        return publicKey;
    }
}
