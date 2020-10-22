package rsa;

import java.math.BigInteger;

public class PublicKey {
    private BigInteger n;
    private BigInteger e;
    private BigInteger tot;

    public PublicKey(PrivateKey _privateKey) {
        // n = p * q
        n = _privateKey.GetP().multiply(_privateKey.GetQ());

        // φ(n) = (p - 1) * (q - 1)
        tot = (_privateKey.GetP().subtract(BigInteger.ONE)).multiply((_privateKey.GetQ().subtract(BigInteger.ONE)));

        // 1 < e < φ(n) e d(e) ∉ d(φ(n))
        e = new BigInteger("3");
        while (tot.gcd(e).intValue() > 1) {
            e = e.add(new BigInteger("2"));
        }
    }

    public BigInteger GetE() {
        return e;
    }

    public BigInteger GetN() {
        return n;
    }

    public BigInteger GetTot() {
        return tot;
    }
}
