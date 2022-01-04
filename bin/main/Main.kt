fun main() {
    script()
}

fun script() {
    var password: String
    var passwordLength: String
    var passwordAlphabet: String
    var passwordNumbers: String
    var passwordCharsIncluded: String
    var passwordUppercase: String

    println("Password Generator")
    println("Complete the following attributes to generate your password")

    println("length:")
    passwordLength = readln()
    while (passwordLength.toByte() < 6) {
        println("The minimum length is 6 characters, please enter a new length:")
        passwordLength = readln()
    }

    println("allow alphabetic characters?")
    passwordAlphabet = readln()

    println("allow numbers?")
    passwordNumbers = readln()

    println("include special characters:")
    passwordCharsIncluded = readln()
    if (passwordCharsIncluded == "default") {
        while (passwordLength.toByte() < 32) {
            println("The default option requires a length of at least 32.")
            println("Would you like to change the length?")
            var answer = readln()
            if (answer == "yes") {
                println("Enter new length:")
                passwordLength = readln()
            } else if (answer == "no") {
                println("include special characters:")
                passwordCharsIncluded = readln()
                break
            }
        }
    }

    println("allow uppercase?")
    passwordUppercase = readln()

    password = generatePassword(
        passwordLength,
        passwordAlphabet,
        passwordNumbers,
        passwordCharsIncluded,
        passwordUppercase
    )

    println("Your password has been successfully generated:")
    println(password)
}

fun generatePassword(
    passwordLength: String,
    passwordAlphabeticCharacters: String,
    passwordNumbers: String,
    passwordCharsIncluded: String,
    passwordUppercase: String
): String {

    var password: String = ""

    var alphabetRange = mutableListOf<Char>()
    var alphabetSelected: Boolean = false
    var numberRange = mutableListOf<Char>()
    var numberSelected: Boolean = false
    var specialCharRange = mutableListOf<Char>()

    var additionalSpecialChars = passwordCharsIncluded.toCharArray()
    var defaultSpecialChars = "^!$%&/()=?+*#_<>".toCharArray()

    if (passwordAlphabeticCharacters == "yes" || passwordAlphabeticCharacters == "y") {
        alphabetSelected = true
        for (char in 'a'..'z') {
            alphabetRange.add(char)
        }
    }

    if (passwordNumbers == "yes" || passwordNumbers == "y") {
        numberSelected = true
        for (num in '0'..'9') {
            numberRange.add(num)
        }
    }

    var counter1: Byte = 0
    while (counter1 < passwordLength.toByte()) {
        if (alphabetSelected && numberSelected) {
            var randomRange = generateRandomNumber(0, 1)
            if (randomRange == 0) {
                var randomIndex = generateRandomNumber(0, alphabetRange.size - 1)
                password += alphabetRange[randomIndex]
            } else if (randomRange == 1) {
                var randomIndex = generateRandomNumber(0, numberRange.size - 1)
                password += numberRange[randomIndex]
            }
        } else if (alphabetSelected) {
            var randomIndex = generateRandomNumber(0, alphabetRange.size - 1)
            password += alphabetRange[randomIndex]
        } else if (numberSelected) {
            var randomIndex = generateRandomNumber(0, numberRange.size - 1)
            password += numberRange[randomIndex]
        }
        counter1++
    }

    if (passwordCharsIncluded == "default") {
        for (char in defaultSpecialChars) {
            specialCharRange.add(char)
        }
    } else {
        for (char in additionalSpecialChars) {
            specialCharRange.add(char)
        }
    }

    // todo: Hier
    var counter2: Byte = 0
    while (counter2 < passwordLength.toByte() / 2) {
        var randomSpecialChar = specialCharRange[generateRandomNumber(0, specialCharRange.size - 1)]
        var randomIndex = generateRandomNumber(0, password.length - 1)
        println(randomSpecialChar)
        println(randomIndex)
        password.replaceRange(randomIndex..randomIndex + 1, randomSpecialChar.toString())
        counter2++
    }

    /*
    if (passwordUppercase == "yes" || passwordUppercase == "y") {
        var newRandom: Int = generateRandomNumber(0, passwordLength.toInt() / 2)
        var counter: Byte = 0
        while (counter < newRandom) {
            var randomIndex = generateRandomNumber(0, passwordLength.toInt())
            password = password[randomIndex].uppercase()
            counter++
        }
    }
     */

    return password;
}

fun generateRandomNumber(startValue: Int, endValue: Int): Int {
    return (startValue..endValue).random()
}
