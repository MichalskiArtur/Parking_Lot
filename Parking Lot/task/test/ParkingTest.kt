import org.hyperskill.hstest.testcase.CheckResult
import org.hyperskill.hstest.testcase.TestCase


/** Default testCase. */
fun <T> testCase(attach: T, input: String) = TestCase<T>().apply {
    setInput(input)
    setAttach(attach)
}

open class InputClue(
        val input: String,
        /** Do not show correct output and input. */
        val isPrivate: Boolean = false,
        /** Hint will be printed even for private tests. */
        val hint: String? = null
) {

    /** Ciphers [message] or adds a [hint] to the error if it is not null. */
    fun toFailure(message: String): CheckResult {
        if (isPrivate) {
            // Ciphered
            return CheckResult.wrong("Incorrect output. This is a private test. " + (hint ?: ""))
        } else {
            return CheckResult.wrong("$message ${hint ?: ""}")
        }
    }
}

class OutputClue(input: String, val output: String, isPrivate: Boolean, hint: String?)
    : InputClue(input, isPrivate, hint) {

    fun compareLines(reply: String) : CheckResult {
        val hisLines = reply.trim().lines()
        val myLines = output.trim().lines()

        myLines.zip(hisLines).withIndex().forEach { (i, pair) ->
            val (my, his) = pair
            if (my != his) {
                return toFailure("Error in line ${i + 1}: \"$his\" instead of \"$my\".")
            }
        }
        // if all common lines are correct, but sizes are different.
        if (hisLines.size != myLines.size) {
            return toFailure("Your output contains ${hisLines.size} lines " +
                    "instead of ${myLines.size}.")
        }
        return CheckResult.correct();
    }
}

fun outputCase(
        input: String,
        output: String,
        isPrivate: Boolean = false,
        hint: String? = null
) = testCase(OutputClue(input, output, isPrivate, hint), input)



class Task2Test : ParkingTest<OutputClue>() {

    override fun generate() = listOf(
            outputCase("park KA-01-HH-1234 White",
                    "White car parked in spot 2.",
                    hint = "See example 1."),

            outputCase("leave 1",
                    "Spot 1 is free.",
                    hint = "See example 2."),

            outputCase("leave 2",
                    "There is no car in spot 2.",
                    hint = "See example 3."),

            outputCase("park KA-01-HH-1234 Red",
                    "Red car parked in spot 2.", true,
                    hint = "Try to test another colors."),

            outputCase("park 1ABC234 Blue",
                    "Blue car parked in spot 2.", true,
                    hint = "Try to test another registration numbers.")
    )


    override fun check(reply: String, clue: OutputClue): CheckResult {

        return clue.compareLines(reply)
    }
}

