package classes

class Password : Random() {
    private var password: String = ""
    private var passwordLength: Byte = 0
    private var testPassed: Boolean = true
    private var alphabetRange = mutableListOf<Char>()
    private var numberRange = mutableListOf<Char>()
    private var specialCharRange = mutableListOf<Char>()
    private val defaultSpecialChars = "^!$%&/()=?+*#_<>".toCharArray()
    private val Random = Random()

    fun setLength(Length: Byte) {
        passwordLength = Length
    }

    fun addChar() {
        for (lowercaseChar in 'a'..'z') {
            alphabetRange.add(lowercaseChar)
        }
        alphabetRange.shuffle()
    }

    fun addUppercase() {
        for (uppercaseChar in 'A'..'Z') {
            alphabetRange.add(uppercaseChar)
        }
        alphabetRange.shuffle()
    }

    fun addNum(from: Byte? = null, to: Byte? = null) {
        if (from != null && to != null) {
            for (num in from..to) {
                numberRange.add(num.toChar())
            }
        } else {
            for (num in '0'..'9') {
                numberRange.add(num)
            }
        }
        numberRange.shuffle()
    }

    fun addSpecial(specialChars: String? = null) {
        when (specialChars) {
            null -> {
                specialCharRange.clear()
            }
            "default" -> {
                for (char in defaultSpecialChars) {
                    specialCharRange.add(char)
                }
            }
            else -> {
                for (char in specialChars) {
                    specialCharRange.add(char)
                }
            }
        }
        specialCharRange.shuffle()
    }

    fun build() {
        // chars are added to initial password
        for (i in 0..passwordLength) {
            when {
                alphabetRange != null && numberRange != null -> {
                    val tempRandom = Random.getNumber(1)
                    password += if (tempRandom == 0) {
                        val randomIndex: Int = if (alphabetRange.size == 0) {
                            return
                        } else {
                            Random.getNumber(alphabetRange.size - 1)
                        }
                        alphabetRange[randomIndex]
                    } else {
                        val randomIndex: Int = if (numberRange.size == 0) {
                            return
                        } else {
                            Random.getNumber(numberRange.size - 1)
                        }
                        numberRange[randomIndex]
                    }
                }
                alphabetRange != null -> {
                    val randomIndex = Random.getNumber(alphabetRange.size - 1)
                    password += alphabetRange[randomIndex]
                }
                numberRange != null -> {
                    val randomIndex = Random.getNumber(numberRange.size - 1)
                    password += numberRange[randomIndex]
                }
            }
        }

        // special chars are added to initial password
        if (specialCharRange.isNotEmpty()) {
            val tempPassword = password.toCharArray()
            for (i in tempPassword.indices) {
                val tempRandom = if (passwordLength < 16) {
                    Random.getNumber(2)
                } else {
                    Random.getNumber(3)
                }
                if (tempRandom > 2) {
                    val randomIndex = Random.getNumber(specialCharRange.size - 1)
                    tempPassword[i] = specialCharRange[randomIndex]
                }
            }
            password = String(tempPassword)
        }
    }

    fun test() {
        // check if ranges contain elements
        when {
            alphabetRange.isNotEmpty() -> {
                val regex = Regex("[a-z]")
                if (!regex.containsMatchIn(password)) {
                    testPassed = false
                }
            }
            alphabetRange.contains('A') -> {
                val regex = Regex("[A-Z]")
                if (!regex.containsMatchIn(password)) {
                    testPassed = false
                }
            }
            numberRange.isNotEmpty() -> {
                val regex = Regex("[0-9]")
                if (!regex.containsMatchIn(password)) {
                    testPassed = false
                }
            }
        }

        // test if created password contains all required chars
        return if (testPassed) {
            display()
        } else {
            build()
        }
    }

    private fun display() {
        println("Your password was successfully generated:")
        println(password)
    }
}
