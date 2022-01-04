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

    println("allow uppercase?")
    passwordUppercase = readln()

    println("include special characters:")
    passwordCharsIncluded = readln()
    if (passwordCharsIncluded == "default") {
        while (passwordLength.toByte() < 32) {
            println("The 'default' option requires a length of at least 32.")
            println("Would you like to change the length?")
            var answer = readln()
            if (answer.equals("yes") || answer.equals("y")) {
                println("Enter new length:")
                passwordLength = readln()
            } else if (answer == "no") {
                println("include special characters:")
                passwordCharsIncluded = readln()
                break
            }
        }
    }

    password = generatePassword(
        passwordLength,
        passwordAlphabet,
        passwordNumbers,
        passwordCharsIncluded,
        passwordUppercase
    )

    if (password.equals("error")) {
        println("An error occurred, please restart the program. ")
    } else {
        println("Your password has been successfully generated:")
        println(password)
    }
}

fun generatePassword(
    passwordLength: String,
    passwordAlphabeticCharacters: String,
    passwordNumbers: String,
    passwordCharsIncluded: String,
    passwordUppercase: String
): String {

    var password: String = ""
    var testPassed: Boolean = true

    var alphabetRange = mutableListOf<Char>()
    var alphabetSelected: Boolean = false
    var numberRange = mutableListOf<Char>()
    var numberSelected: Boolean = false
    var specialCharRange = mutableListOf<Char>()
    var uppercaseSelected: Boolean = false

    var additionalSpecialChars = passwordCharsIncluded.toCharArray()
    var defaultSpecialChars = "^!$%&/()=?+*#_<>".toCharArray()

    // The following if-statements create three lists with the choosen characters.
    if (passwordAlphabeticCharacters.equals("yes") || passwordAlphabeticCharacters.equals("y")) {
        alphabetSelected = true
        for (lowercaseChar in 'a'..'z') {
            alphabetRange.add(lowercaseChar)
        }
        if (passwordUppercase.equals("yes") || passwordUppercase.equals("y")) {
            uppercaseSelected = true
            for (uppercaseChar in 'A'..'Z') {
                alphabetRange.add(uppercaseChar)
            }
        }
        alphabetRange.shuffle()
    }

    if (passwordNumbers.equals("yes") || passwordNumbers.equals("y")) {
        numberSelected = true
        for (num in '0'..'9') {
            numberRange.add(num)
        }
        numberRange.shuffle()
    }

    if (passwordCharsIncluded.equals("default")) {
        for (char in defaultSpecialChars) {
            specialCharRange.add(char)
        }
    } else {
        for (char in additionalSpecialChars) {
            specialCharRange.add(char)
        }
    }

    specialCharRange.shuffle()

    // The password is created by selecting a random char that's being added at the end.
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

    if (!passwordCharsIncluded.isBlank()) {
        var tempPassword = password.toCharArray()
        for (i in tempPassword.indices) {
            if (passwordLength.toShort() < 10) {
                var randomNum = generateRandomNumber(0, 2)
                if (randomNum > 1) {
                    var randomIndex = generateRandomNumber(0, specialCharRange.size - 1)
                    tempPassword[i] = specialCharRange[randomIndex]
                }
            } else if (passwordLength.toShort() > 10) {
                var randomNum = generateRandomNumber(0, 3)
                if (randomNum > 1) {
                    var randomIndex = generateRandomNumber(0, specialCharRange.size - 1)
                    tempPassword[i] = specialCharRange[randomIndex]
                }
            }
        }
        password = String(tempPassword)
    }

    // Test if all conditions are given in the final password.
    if (alphabetSelected) {
        val regex = Regex("[a-z]")
        if (!regex.containsMatchIn(password)) {
            testPassed = false
        }
    }

    if (uppercaseSelected) {
        val regex = Regex("[A-Z]")
        if (!regex.containsMatchIn(password)) {
            testPassed = false
        }
    }

    if (numberSelected) {
        val regex = Regex("[0-9]")
        if (!regex.containsMatchIn(password)) {
            testPassed = false
        }
    }

    // The password is being returned if all tests are passed.
    if (testPassed) {
        return password;
    } else {
        return "error";
    }
}

fun generateRandomNumber(startValue: Int, endValue: Int): Int {
    return (startValue..endValue).random()
}
