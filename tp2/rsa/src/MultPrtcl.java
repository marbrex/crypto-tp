import java.math.BigInteger;

public class MultPrtcl {

    static class Bob {
        private static BigInteger r, s;

        public static BigInteger[] step1(Key publicKey, BigInteger X, BigInteger Y) {

            BigInteger N = publicKey.N;
            BigInteger N2 = N.multiply(N);

            // Generate random 'r' and 's' , both modulo N
            r = Paillier.randInInterval(1024, BigInteger.ZERO, N);
            s = Paillier.randInInterval(1024, BigInteger.ZERO, N);

            // Selon les proprietes homomorphes de Paillier,
            // Encryption de x+r est XR et celle de y+s est YS

            try {
                BigInteger R = Paillier.encrypt(r, publicKey);
                BigInteger S = Paillier.encrypt(s, publicKey);

                BigInteger XR = X.multiply(R).mod(N2);
                BigInteger YS = Y.multiply(S).mod(N2);

                BigInteger[] result = new BigInteger[2];
                result[0] = XR;
                result[1] = YS;

                return result;

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public static BigInteger step3(Key publicKey, BigInteger P, BigInteger X, BigInteger Y) {

            BigInteger N = publicKey.N;
            BigInteger N2 = N.multiply(N);

            try {
                BigInteger R = Paillier.encrypt(r, publicKey);

                BigInteger aux1 = X.modPow(s.negate(), N2);
                BigInteger aux2 = Y.modPow(r.negate(), N2);
                BigInteger aux3 = R.modPow(s.negate(), N2);

                return P.multiply(aux1).multiply(aux2).multiply(aux3);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    static class Alice {
        public static BigInteger step2(Key publicKey, Key privateKey, BigInteger XR, BigInteger YS) {

            // Selon les proprietes homomorphes de Paillier,
            // Encryption de x*y est: X^y mod N2

            BigInteger N2 = publicKey.N.multiply(publicKey.N);
            BigInteger yPlusS = Paillier.decrypt(YS, privateKey);

            return XR.modPow(yPlusS, N2);
        }
    }

}
