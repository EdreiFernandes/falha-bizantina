package rsa;

public class RSACrypto {
    public static void main(String[] args) {
        RSAManager rsaCliente1 = new RSAManager();
        RSAManager rsaCliente2 = new RSAManager();

        // cliente 1 manda para cliente 2
        String msgCliente1 = "batata frita";
        var rsaMsgCliente1 = rsaCliente1.EncryptMessage(msgCliente1, rsaCliente2.GetPublicKey());

        // cliente 2 entende mensagem de cliente 1
        var msgCliente1Again = rsaCliente2.DecryptMessage(rsaMsgCliente1);

        System.out.println("---------C_1 --> C_2 -------------");
        System.out.println(msgCliente1);
        System.out.println(rsaMsgCliente1);
        System.out.println(msgCliente1Again);

        // cliente 2 manda resposta pra cliente 1
        String msgCliente2 = "Pedido anotado";
        var rsaMsgCliente2 = rsaCliente2.EncryptMessage(msgCliente2, rsaCliente1.GetPublicKey());

        // cliente 1 entende mensagem de cliente 2
        var msgCliente2Again = rsaCliente1.DecryptMessage(rsaMsgCliente2);

        System.out.println("---------C_2 --> C_1 -------------");
        System.out.println(msgCliente2);
        System.out.println(rsaMsgCliente2);
        System.out.println(msgCliente2Again);
    }
}
