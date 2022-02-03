package src.main.kotlin.imports

import java.security.SecureRandom

class Random {
    private val secureRandom = SecureRandom.getInstance("SHA1PRNG")

    fun getNumber(bound: Int): Int {
        return secureRandom.nextInt(bound + 1)
    }
}
