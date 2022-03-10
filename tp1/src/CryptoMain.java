import java.math.BigInteger;
import java.security.SecureRandom;

public class CryptoMain {

    public static void exo73() {

        // q1
        int lenBits = 2048;

        SecureRandom rand = new SecureRandom();

        BigInteger a = new BigInteger(lenBits, rand);
        BigInteger b = new BigInteger(lenBits, rand);

        System.out.println("a: " + a);
        System.out.println("a bits: " + a.bitLength());

        System.out.println("b: " + b);
        System.out.println("b bits: " + b.bitLength());

        // q2
        BigInteger sum = a.add(b);
        System.out.println("\na + b: " + sum);
        System.out.println("a + b bits: " + sum.bitLength());

        BigInteger mult = a.multiply(b);
        System.out.println("a * b: " + mult);
        System.out.println("a * b bits: " + mult.bitLength());

        // q3
        BigInteger div = a.divide(b);
        System.out.println("\na / b: " + div);
        System.out.println("a / b bits: " + div.bitLength());

        BigInteger mod = a.mod(b);
        System.out.println("a % b: " + mod);
        System.out.println("a % b bits: " + mod.bitLength());

        long startTime;
        long endTime;
        int nbTimes = 100000;

        startTime = System.currentTimeMillis();
        for (int i = 0; i < nbTimes; i++) {
            a.add(b);
        }
        endTime = System.currentTimeMillis();
        System.out.println(nbTimes + " additions completed in " + (endTime - startTime) + "ms");

        startTime = System.currentTimeMillis();
        for (int i = 0; i < nbTimes; i++) {
            a.multiply(b);
        }
        endTime = System.currentTimeMillis();
        System.out
            .println(nbTimes + " multiplications completed in " + (endTime - startTime) + "ms");

        startTime = System.currentTimeMillis();
        for (int i = 0; i < nbTimes; i++) {
            a.divide(b);
        }
        endTime = System.currentTimeMillis();
        System.out.println(nbTimes + " divisions completed in " + (endTime - startTime) + "ms");

        startTime = System.currentTimeMillis();
        for (int i = 0; i < nbTimes; i++) {
            a.mod(b);
        }
        endTime = System.currentTimeMillis();
        System.out
            .println(nbTimes + " modulo operations completed in " + (endTime - startTime) + "ms");
    }

    public static void exo74() {

        // q1
        int lenBits = 30;

        SecureRandom rand = new SecureRandom();

        BigInteger a = new BigInteger(lenBits, rand);
        BigInteger b = new BigInteger(lenBits, rand);
        BigInteger c = new BigInteger(lenBits, rand);

        System.out.println("\na: " + a);
        System.out.println("a bits: " + a.bitLength());

        System.out.println("b: " + b);
        System.out.println("b bits: " + b.bitLength());

        System.out.println("c: " + c);
        System.out.println("c bits: " + c.bitLength());

        // q2
        // Etant donne le fait que les nombres que l'on vient
        // de generer sont tres grands, composes de 9 ou 10 chiffres,
        // la fonction "A a la puissance B" augmente tres vite.
        // En consequence, meme quand le calcul sera termine,
        // on aura juste pas assez de memoire pour sauvegarder
        // un tel grand nombre. Donc, le premier methode est
        // tres inefficace. Ainsi, on utilise la deuxieme
        // methode avec la fonction "modPow" de "BigInteger".

        BigInteger p = a.modPow(b, c);
        System.out.println("\na.modPow(b, c) : " + p);
        System.out.println("Size in bits : " + p.bitLength());

        // q3
        lenBits = 2048;
        int nbTimes = 100;

        a = new BigInteger(lenBits, rand);
        b = new BigInteger(lenBits, rand);
        c = new BigInteger(lenBits, rand);

        System.out.println("\na: " + a);
        System.out.println("a bits: " + a.bitLength());

        System.out.println("b: " + b);
        System.out.println("b bits: " + b.bitLength());

        System.out.println("c: " + c);
        System.out.println("c bits: " + c.bitLength());

        long startTime;
        long endTime;
        long time;
        double timeOneOp;

        System.out.println("Using BigInteger class's methods:");
        startTime = System.currentTimeMillis();
        for (int i = 0; i < nbTimes; i++) {
            a.modPow(b, c);
        }
        endTime = System.currentTimeMillis();

        time = (endTime - startTime);
        timeOneOp = (double) time / nbTimes;
        System.out.println(nbTimes + " operations completed in " + time + "ms");
        System.out.println("Average Time for 1 operation is " + timeOneOp + "ms");
    }

    public static void exo75() {

        // q1
        int lenBits = 2048;

        SecureRandom rand = new SecureRandom();

        BigInteger p = new BigInteger(lenBits, rand);
        while (!p.isProbablePrime(100)) {
            p = new BigInteger(lenBits, rand);
        }

        System.out.println("\np: " + p);
        System.out.println("p is prime: " + p.isProbablePrime(100));
        System.out.println("p bits: " + p.bitLength());

        // q2
        // On genere des nombres entre [1;p-1] nbTimes fois
        // et a chaque fois on regarde si le generateur donne le bon nombre
        BigInteger a;
        int nbTimes = 10;
        int primesCount = 0;
        for (int i = 0; i < nbTimes; i++) {
            a = aleaInf(p);
            if (!isFromOneToSupMinusOne(a, p)) {
                break;
            } else {
                primesCount++;
            }
        }
        if (primesCount == nbTimes) {
            System.out.println("\nGenerator OK");
        } else {
            System.out.println("\nGenerator FAILED");
        }
        System.out.println(primesCount + " generations finished successfully");
        // System.out.println("Probability of n in [1;p-1] = " + primesCount + "%");

        // q3

    }

    /**
     * True if : 1 <= n <= sup-1
     */
    public static boolean isFromOneToSupMinusOne(BigInteger n, BigInteger sup) {
        return n.compareTo(sup.add(BigInteger.valueOf(-1L))) <= 0 &&
            n.compareTo(BigInteger.ONE) >= 0;
    }

    public static BigInteger aleaInf(BigInteger p) {
        BigInteger bigInt = new BigInteger(2048, new SecureRandom());
        return bigInt.mod(p.add(BigInteger.valueOf(-1L))).add(BigInteger.ONE);
    }

    public static void exo76() {

        // q1
        int lenBits = 1024;

        SecureRandom rand = new SecureRandom();

        BigInteger p = new BigInteger(lenBits, rand);
        BigInteger q = new BigInteger(lenBits, rand);

        System.out.println("\np: " + p);
        System.out.println("p bits: " + p.bitLength());

        System.out.println("q: " + q);
        System.out.println("q bits: " + q.bitLength());

        BigInteger n = p.multiply(q);
        System.out.println("n: " + n);
        System.out.println("n bits: " + n.bitLength());

        // q2
        BigInteger phiOfN = phi(p, q);
        System.out.println("\nф(n): " + phiOfN);
        System.out.println("ф(n) bits: " + phiOfN.bitLength());

        // q3
        BigInteger a = aleaInf(p);
        System.out.println("\na: " + a);
        System.out.println("a bits: " + a.bitLength());

        // q4

    }

    public static BigInteger phi(BigInteger p, BigInteger q) {
        return p.add(BigInteger.valueOf(-1L)).multiply(q.add(BigInteger.valueOf(-1L)));
    }

    public static void exo77() {

        // q1
        int lenBits = 1024;
        SecureRandom rand = new SecureRandom();

        BigInteger p = new BigInteger(lenBits, rand);
        BigInteger q = new BigInteger(lenBits, rand);

        System.out.println("\np: " + p + "\np bits: " + p.bitLength());
        System.out.println("q: " + q + "\nq bits: " + q.bitLength());

        BigInteger n = p.multiply(q);
        System.out.println("n: " + n + "\nn bits: " + n.bitLength() + "\n");

        // q2
        // on peut calculer b <=> a est co-premier avec n (pgcd = 1) ET n > 0
        // on cherche un tel a
        BigInteger a = aleaInf(p);
        int tryCount = 0;
        while (a.gcd(n).compareTo(BigInteger.ONE) != 0) {
            a = aleaInf(p);
            tryCount++;
        }
        System.out.println("Found invertible number on " + (tryCount + 1) + " try");
        System.out.println("a: " + a + "\na bits: " + a.bitLength());

        BigInteger b = null;
        try {
            b = a.modInverse(n);
            System.out.println("b: " + b + "\nb bits: " + b.bitLength());

        } catch (ArithmeticException e) {
            e.printStackTrace();
        }

        // q3
        // assert a.multiply(b).mod(n).compareTo(BigInteger.ONE) == 0;
        if (a.multiply(b).mod(n).compareTo(BigInteger.ONE) == 0) {
            System.out.println("\nOK : ( a * b ) % n == 1");
        }

        // q4
        // on peut pas calculer l'inverse de "p mod n",
        // car, comme mentionne ci-dessus, l'inverse est
        // calculable si est seulement si n > 0 et PGCD(p, n) = 1.
        // Mais comme n=p*q => PGCD(p,n)=p
        // Donc l'inverse n'est pas calculable.
        try {
            BigInteger inv = p.modInverse(n);
        } catch (ArithmeticException e) {
            System.out.println("\n(p mod n) is not invertible");
        }

    }

    public static void exo78() {

    }

    public static void main(String[] args) {
        // exo73();
        // exo74();
        // exo75();
        // exo76();
        // exo77();
         exo78();
    }

}
