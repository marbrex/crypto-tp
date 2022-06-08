import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class RSA {

    public static Key[] keyGen(int lenBits) {

        // Choosing 2 large prime numbers
        SecureRandom rand = new SecureRandom();
        BigInteger p = new BigInteger(lenBits, 100, rand);
        BigInteger q = new BigInteger(lenBits, 100, rand);

        if (p.compareTo(q) == 0) {
            q = new BigInteger(lenBits, 100, rand);
        }

        // Computing N and phiOfN
        BigInteger N = p.multiply(q);
        BigInteger phiOfN = phi(p, q);

        // computing "e" and generating the Public Key
        BigInteger e = BigInteger.ONE.nextProbablePrime();
        // int tryCount = 0;
        while (e.gcd(phiOfN).compareTo(BigInteger.ONE) != 0) {
            e = e.nextProbablePrime();
            // tryCount++;
        }

        // computing "d" and generating the Private Key
        BigInteger d = e.modInverse(phiOfN);

        Key[] keys = new Key[2];
        keys[0] = new Key(N, e);
        keys[1] = new Key(N, d);

        return keys;
    }

    private static BigInteger phi(BigInteger p, BigInteger q) {
        return p.add(BigInteger.valueOf(-1L)).multiply(q.add(BigInteger.valueOf(-1L)));
    }

    public static BigInteger encrypt(BigInteger m, Key publicKey) throws Exception {

        if (BigInteger.ZERO.compareTo(m) <= 0 && publicKey.N.compareTo(m) > 0) {
            return m.modPow(publicKey.key, publicKey.N);
        }
        else {
            throw new Exception("m does not match the requirements (0 <= m < N)");
        }

    }

    public static BigInteger decrypt(BigInteger c, Key privateKey) {
        return c.modPow(privateKey.key, privateKey.N);
    }

    public static BigInteger sign(BigInteger m, Key privateKey) {
        return RSA.sign(m, privateKey, "SHA-256");
    }

    public static BigInteger sign(BigInteger m, Key privateKey, String hashAlgo) {
        try {
            hashAlgo = hashAlgo == null || "".equals(hashAlgo) ? "SHA-256" : hashAlgo;
            MessageDigest sha256 = MessageDigest.getInstance(hashAlgo);
            BigInteger digest = new BigInteger(sha256.digest(m.toByteArray()));
            return digest.modPow(privateKey.key, privateKey.N);

        } catch (NoSuchAlgorithmException | NullPointerException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean verify(BigInteger m, BigInteger s, Key publicKey) {
        return RSA.verify(m, s, publicKey, "SHA-256");
    }

    public static boolean verify(BigInteger m, BigInteger s, Key publicKey, String hashAlgo) {
        try {
            hashAlgo = hashAlgo == null || "".equals(hashAlgo) ? "SHA-256" : hashAlgo;
            MessageDigest sha256 = MessageDigest.getInstance(hashAlgo);
            BigInteger digest = new BigInteger(sha256.digest(m.toByteArray()));
            return s.modPow(publicKey.key, publicKey.N).compareTo(digest) == 0;

        } catch (NoSuchAlgorithmException | NullPointerException e) {
            throw new RuntimeException(e);
        }
    }

}
