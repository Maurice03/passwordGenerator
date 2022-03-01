import imports.Password

fun main() {
    script()
}

fun script() {
    val Password = Password()

    println("Password Generator")
    println("Complete the following attributes to generate your password")

    println("length:")
    var passwordLength = readln()
    while (passwordLength.toByte() < 6) {
        println("The minimum length is 6 characters, please enter a new length:")
        passwordLength = readln()
    }
    Password.setLength(passwordLength.toByte())

    println("allow alphabetic characters?")
    var passwordAlphabet = readln()
    if (passwordAlphabet.matches(Regex("y(es)?"))) {
        Password.addChar()
    }

    println("allow uppercase?")
    var passwordUppercase = readln()
    if (passwordUppercase.matches(Regex("y(es)?"))) {
        Password.addUppercase()
    }

    println("allow numbers?")
    var passwordNumbers = readln()
    if (passwordNumbers.matches(Regex("y(es)?"))) {
        Password.addNum()
    }

    println("include special characters:")
    var passwordCharsIncluded = readln()
    if (Regex("d(efault)?").matches(passwordCharsIncluded)) {
        while (passwordLength.toByte() < 32) {
            println("The 'default' option requires a length of at least 32 chars.")
            println("Would you like to change the length?")
            var answer = readln()
            if (answer.matches(Regex("y(es)?"))) {
                println("Enter new length:")
                passwordLength = readln()
                Password.setLength(passwordLength.toByte())
                Password.addSpecial("default")
            } else if (answer.matches(Regex("n(o)?"))) {
                println("include special characters:")
                passwordCharsIncluded = readln()
                Password.addSpecial(passwordCharsIncluded)
                break
            }
        }
    } else {
        Password.addSpecial(passwordCharsIncluded)
    }

    Password.build()

    Password.test()

}
