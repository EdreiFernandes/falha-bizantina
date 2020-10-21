package rsa;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

public class RSAManager {
    // Public keys
    private BigInteger n;
    private BigInteger e;
    // Private keys
    private BigInteger p;
    private BigInteger q;
    // Auxiliary key
    private BigInteger d;
    private BigInteger tot;

    public RSAManager() {
        generatePrivateKeys();
        generatePublicAndAuxKeys();
    }

    private void generatePrivateKeys() {
        // generate prime numbers
        int bitlen = 20;
        SecureRandom r = new SecureRandom();
        p = new BigInteger(bitlen / 2, 100, r);
        q = new BigInteger(bitlen / 2, 100, r);
    }

    private void generatePublicAndAuxKeys() {
        // n = p * q
        n = p.multiply(q);

        // φ(n) = (p - 1) * (q - 1)
        tot = (p.subtract(BigInteger.ONE)).multiply((q.subtract(BigInteger.ONE)));

        // 1 < e < φ(n) e d(e) ∉ d(φ(n))
        e = new BigInteger("3");
        while (tot.gcd(e).intValue() > 1) {
            e = e.add(new BigInteger("2"));
        }

        // multiplicative inverse
        d = e.modInverse(tot);
    }

    public String EncryptMessage(String _message) {
        byte[] ascii = _message.getBytes(StandardCharsets.US_ASCII);

        StringBuilder rsaMessage = new StringBuilder();
        for (byte m : ascii) {
            // C = M ^ e mod(n)
            String C = BigInteger.valueOf(m).modPow(e, n).toString();
            rsaMessage.append(C + " ");
        }

        return rsaMessage.toString();
    }

    public String DecryptMessage(String _message) {
        StringBuilder ascii = new StringBuilder();
        String[] rsaArray = _message.toString().split("\\s+");

        for (String c : rsaArray) {
            BigInteger C = new BigInteger(c);
            // M = C ^ d mod(n)
            ascii.append(C.modPow(d, n) + " ");
        }

        StringBuilder decryptedMessage = new StringBuilder();
        String[] asciiAgainArray = ascii.toString().split("\\s+");
        for (String m : asciiAgainArray) {
            decryptedMessage.append((char) Integer.parseInt(m));
        }

        return decryptedMessage.toString();
    }

    public void ShowKeys() {
        System.out.println("public keys");
        System.out.println("n: " + n);
        System.out.println("e: " + e);
        System.out.println("private keys");
        System.out.println("p: " + p);
        System.out.println("q: " + q);
        System.out.println("aux keys");
        System.out.println("d: " + d);
        System.out.println("tot: " + tot);
    }
}
