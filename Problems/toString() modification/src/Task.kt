data class Box(val height: Int, val length: Int, val width: Int) {
    override fun toString(): String {
        return "Box(height=${this.height}, width=${this.width})"
    }
}