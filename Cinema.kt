package cinema

val frontPrice = 10
val rearPrice = 8

fun main() {
    print("Enter the number of rows:\n")
    val rows = readLine()!!.toInt()
    print("Enter the number of seats in each row:\n")
    val seats = readLine()!!.toInt()

    val cinema = Array(rows) { CharArray(seats) { 'S' } }

    val totalIncome = if (rows * seats < 61) rows * seats * frontPrice
    else {
        val front = rows / 2;
        val rear = rows - front
        seats * (front * frontPrice + rear * rearPrice)
    }

    var sold = 0
    var income = 0

    do {
        print("\n1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit\n>")
        when (readLine()!!) {
            "1" -> {
                printRoom(cinema, rows, seats)
            }
            "2" -> {
                do {
                    print("\nEnter a row number:\n>")
                    val row = readLine()!!.toInt()
                    print("Enter a seat number in that row:\n>")
                    val seat = readLine()!!.toInt()

                    if (!(row in 1..rows) || !(seat in 1..seats)) print("\nWrong input!\n")
                    else
                        if (cinema[row-1][seat-1] == 'B') print("That ticket has already been purchased!\n")
                        else {
                            cinema[row-1][seat-1] = 'B'
                            sold++
                            val price = pricing(rows, seats, row, seat)
                            income += price
                            print("Ticket price: \$$price\n")
                            break
                        }
                } while (true)
             }
            "3" -> {
                print("\nNumber of purchased tickets: $sold\n")
                val percentage: Double = 100 * sold.toDouble() / (rows * seats)
                print("Percentage: ${String.format("%.2f",percentage)}% \n")
                print("Current income: \$${income}\n")
                print("Total income: \$$totalIncome\n")
            }
            "0" -> return
            else -> continue
        }
    } while (true)
}

fun printRoom(cinema: Array<CharArray>, rows: Int, seats: Int) {
    print("\nCinema:\n")
    print(" ")
    for (i in 1..seats) print(" $i")
    for (i in 1..rows) {
        print("\n$i ${cinema[i - 1].joinToString(" ")}")
    }
    println()
}

fun pricing(rows: Int, seats: Int, row: Int, seat: Int): Int =
    if (rows * seats < 61) frontPrice
    else if (row <= rows / 2) frontPrice else rearPrice
