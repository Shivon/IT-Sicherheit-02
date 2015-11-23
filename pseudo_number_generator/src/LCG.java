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

  /*
   * This function creates a pseudo number with linear congruential method
   */
  public BigInteger nextValue() {
    startValue = (factorA.multiply(startValue).add(incrementB)).mod(moduloN);
    return startValue;
  }

  public static void main(String[] args) {
    /*
     * Gets input param as start value if existing
     * Else generates random start value
     */
    long inputValue;
    if (args.length == 1) {
      try {
        inputValue = Long.parseLong(args[0]);
      } catch (NumberFormatException e) {
        System.out.println("Please enter a number as parameter.");
        return;
      }
    } else {
      // With random startValue
      Random rand = new Random();
      inputValue = rand.nextLong();
    }

    LCG lcg = new LCG(BigInteger.valueOf(inputValue));

    for (int i = 0; i < 30; i++) {
      System.out.println(lcg.nextValue());
    }
  }
}
