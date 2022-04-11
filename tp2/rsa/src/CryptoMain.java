import java.math.BigInteger;
import java.security.SecureRandom;

public class CryptoMain {

    public static void exo79() {

        // q1
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

        // q2
        // Car on doit respecter l'equation
        // (e * d) % phi = 1
        // ou autrement dit, (e * d) doit etre
        // congru a 1 mod phi. Ainsi, on doit
        // avoir pgcd(e, phi) = 1, qui revient
        // a dire que "e" doit etre premier
        // avec "phi"

        // q3
        // Non, car e doit etre un nombre premier,
        // cad divisible que par 1 et lui-meme.

        // q4
        // On peut choisir e petit pour gagner
        // en efficacite, car cela nous
        // permettra d'effectuer des calculs plus
        // rapidement. En plus, cela ne impacte pas d.

        // q5
        long startTime;
        long endTime;
        long nbTimes = 10;
        long time;
        double timeOneOp;

        startTime = System.currentTimeMillis();
        for (int i = 0; i < nbTimes; i++) {
            keys = RSA.keyGen(1024);
            pk = keys[0];
            sk = keys[1];

            try {
                BigInteger m = BigInteger.valueOf(465234348);
                BigInteger c = RSA.encrypt(m, pk);
                BigInteger plaintext = RSA.decrypt(c, sk);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        endTime = System.currentTimeMillis();

        time = (endTime - startTime);
        timeOneOp = (double) time / nbTimes;
        System.out.println(nbTimes + " operations completed in " + time + "ms");
        System.out.println("Average Time for 1 operation is " + timeOneOp + "ms");

        // Temps pour encrypter 1Go
        // On encrypte 10 fois un message de 1024 bits
        // (donc de 128 bytes) avec une cle de 1024 bits.

        startTime = System.currentTimeMillis();
        for (int i = 0; i < nbTimes; i++) {
            keys = RSA.keyGen(1024);
            pk = keys[0];

            try {
                BigInteger m = new BigInteger(1024, new SecureRandom());
                RSA.encrypt(m, pk);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        endTime = System.currentTimeMillis();

        time = (endTime - startTime);
        timeOneOp = (double) time / nbTimes;
        System.out.println(nbTimes + " operations completed in " + time + "ms");
        System.out.println("Average Time for 1 operation is " + timeOneOp + "ms");

        // Temps moyen pour encrypter 128 bytes = timeOneOp
        // 1Gb = 1 073 741 824 bytes
        // Pour encrypter 1Gb, il nous faudra
        long facteur = 1073741824L / 128L;
        double time1GBms = facteur * timeOneOp;
        double time1GBsec = time1GBms / 1000;
        double time1GBmin = time1GBsec / 60;
        double time1GBh = time1GBmin / 60;
        System.out.println("Time for 1Gb: " + time1GBh + "h");

        // Conclusion: RSA n'est pas fait pour encrypter des
        // grands volumes de donnees

        // q6
        // Car RSA est un cryptosysteme deterministe.

    }

    public static void main(String[] args) {
        exo79();
    }

}
