import java.math.BigInteger;
import java.util.Random;

class LCG {
  /*
   * Params as per SUN-UNIX drand48
   * IMPORTANT: Since the numbers are so big in this reference, even long is not enough
   * and creates an overflow. Hence we have to use BigInteger.
   */
  private BigInteger startValue;
  private BigInteger factorA = BigInteger.valueOf(25214903917L);
  private BigInteger incrementB = BigInteger.valueOf(11L);
  private BigInteger moduloN = BigInteger.valueOf(2L).pow(48);

  public LCG(BigInteger startValue) {
    this.startValue = startValue;
  }

  public BigInteger nextValue() {
    startValue = (factorA.multiply(startValue).add(incrementB)).mod(moduloN);
    return startValue;
  }

  public static void main(String[] args) {
    // For testing purpose with static startValue
    // LCG lcg = new LCG(BigInteger.valueOf(100L));

    // with random startValue
    Random rand = new Random();
    long random = rand.nextLong();
    LCG lcg = new LCG(BigInteger.valueOf(random));

    for (int i = 0; i < 30; i++) {
      System.out.println(lcg.nextValue());
    }
  }
}
