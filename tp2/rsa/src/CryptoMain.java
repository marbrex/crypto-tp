import java.math.BigInteger;

public class CryptoMain {

    public static void exo79() {
        Key[] keys = RSA.keyGen(1024);
        Key pk = keys[0];
        Key sk = keys[1];

        try {
            BigInteger m = BigInteger.valueOf(465234348);
            BigInteger c = RSA.encrypt(m, pk);

            BigInteger plaintext = RSA.decrypt(c, sk);

            if (m.compareTo(plaintext) == 0) {
                System.out.println("\nMATCH !");
            }
            else {
                System.out.println("\nm: " + m);
                System.out.println("plaintext: " + plaintext);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        exo79();
    }

}
