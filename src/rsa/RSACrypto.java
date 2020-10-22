package rsa;

public class RSACrypto {
    public static void main(String[] args) {
        RSAManager rsa = new RSAManager();

        String str = "batata frita";
        var rsaMsg = rsa.EncryptMessage(str, rsa.GetPublicKey());
        var strAgain = rsa.DecryptMessage(rsaMsg);

        System.out.println("--------------------------");

        System.out.println(str);
        System.out.println(rsaMsg);
        System.out.println(strAgain);
    }
}
