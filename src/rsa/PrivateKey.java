package rsa;

import java.math.BigInteger;
import java.security.SecureRandom;

public class PrivateKey {
    private BigInteger p;
    private BigInteger q;

    public PrivateKey() {
        // generate prime numbers
        int bitlen = 20;
        SecureRandom r = new SecureRandom();
        p = new BigInteger(bitlen / 2, 100, r);
        q = new BigInteger(bitlen / 2, 100, r);
    }

    public BigInteger GetP() {
        return p;
    }

    public BigInteger GetQ() {
        return q;
    }
}
