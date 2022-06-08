import java.math.BigInteger;
import java.security.SecureRandom;

public class Paillier {

    private static final BigInteger ONE = BigInteger.ONE;
    private static final BigInteger ZERO = BigInteger.ZERO;

    public static Key[] keyGen(int lenBits) {

        // Choosing 2 large prime numbers
        SecureRandom rand = new SecureRandom();
        BigInteger p = new BigInteger(lenBits, 100, rand);
        BigInteger q = new BigInteger(lenBits, 100, rand);

        while (p.compareTo(q) == 0) {
            System.out.println("@keyGen : p == q");
            q = new BigInteger(lenBits, 100, rand);
        }

        // Computing N and phiOfN
        BigInteger N = p.multiply(q);
        BigInteger phiOfN = phi(p, q);

        BigInteger ro = N.modInverse(phiOfN);

        Key[] keys = new Key[2];
        keys[0] = new Key(N, N); // Public Key
        keys[1] = new Key(N, ro); // Private Key

        return keys;

    }

    private static BigInteger phi(BigInteger p, BigInteger q) {
        return p.subtract(ONE).multiply(q.subtract(ONE));
    }

    public static BigInteger encrypt(BigInteger m, Key publicKey) throws Exception {

        final BigInteger N = publicKey.N;

        if (m.compareTo(ZERO) >= 0 && m.compareTo(N) < 0) {
            final BigInteger N2 = N.multiply(N);
            final BigInteger r = randInInterval(1024, ZERO, N);

            return ONE.add(m.multiply(N)).multiply(r.modPow(N, N2)).mod(N2);
        }
        else {
            throw new Exception("m does not match the requirements (0 < m < N)");
        }

    }

    public static BigInteger decrypt(BigInteger c, Key privateKey) {

        final BigInteger N = privateKey.N;
        final BigInteger N2 = N.multiply(N);
        final BigInteger r = c.modPow(privateKey.key, N);

        return c.multiply(r.modPow(N.negate(), N2)).mod(N2).subtract(ONE).divide(N);
    }

    /**
     * @param from lower bound
     * @param to greater bound
     * @return random in (from;to) not included, i.e. from < random < to
     */
    public static BigInteger randInInterval(int lenBits, BigInteger from, BigInteger to) {
        BigInteger num;
        SecureRandom rand = new SecureRandom();

        do num = new BigInteger(lenBits, rand);
        while (num.compareTo(from) <= 0 || num.compareTo(to) >= 0);

        return num;
    }

}
