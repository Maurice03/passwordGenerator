package imports

import java.security.SecureRandom

open class Random {
    private val secureRandom = SecureRandom.getInstance("SHA1PRNG")

    fun getNumber(bound: Int): Int {
        return secureRandom.nextInt(bound + 1)
    }
}
