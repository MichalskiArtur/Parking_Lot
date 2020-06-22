import java.util.Scanner

// do not change this data class
data class Box(val height: Int, val length: Int, val width: Int)

fun main(args: Array<String>) {
    val input = Scanner(System.`in`)
    // implement your code here
    val h: Int = input.nextInt()
    val l1: Int = input.nextInt()
    val l2: Int = input.nextInt()
    val w: Int = input.nextInt()
    val box = Box(h, l1, w)
    val box2 = box.copy(length = l2)

    println("Box(height=${box.height}, length=${box.length}, width=${box.width})")
    println("Box(height=${box2.height}, length=${box2.length}, width=${box2.width})")
}