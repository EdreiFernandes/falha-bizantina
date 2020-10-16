package rsa;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RSAKey {
    // Public keys
    private BigInteger n;
    private BigInteger e;
    // Private keys
    private BigInteger p;
    private BigInteger q;
    // Auxiliary key
    private BigInteger d;
    private BigInteger tot;

    public RSAKey() {
        generatePrivateKeys();
        generatePublicAndAuxKeys();
    }

    private void generatePrivateKeys() {
        // generate prime numbers
        int bitlen = 100;
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

    public BigInteger getN() {
        return n;
    }

    public BigInteger getE() {
        return e;
    }

    public BigInteger getD() {
        return d;
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
