package cinema

const val PRICE = 10 // Stage 2/5
fun main() {
    // build and get cinema info
    println("Enter the number of rows:")
    val rows = readln().toInt()
    println("Enter the number of seats in each row:")
    val seats = readln().toInt()
    val cinemaRoom1: MutableList<MutableList<Char>> = mutableListOf()
    buildCinemaRoom1(rows, seats, cinemaRoom1)
    var exit = 0
    while (exit != 1) {
        println("1. Show the seats")
        println("2. Buy a ticket")
        println("3. Statistics")
        println("0. Exit")
        when (readln().toInt()) {
            1 -> getCinemaInfo(rows, seats, cinemaRoom1)
            2 -> buyTicket(cinemaRoom1)
            3 -> statistics(rows, seats, cinemaRoom1)
            0 -> exit = 1
        }
    }
}

fun statistics(rows: Int, seats: Int, cinemaRoom: MutableList<MutableList<Char>>) {
    // The number of purchased tickets;
    var purchasedTickets = 0
    for (row in 1..rows) {
        for (seat in 1..seats) {
            if (cinemaRoom[row - 1][seat - 1] == 'B') purchasedTickets++
        }
    }
    // Percentage of purchased tickets
    val percentage = (purchasedTickets * 100.0) / (rows * seats)
    val formatPercentage = "%.2f".format(percentage)

    // Current income;
    var currentIncome = 0
    for (row in 1..rows) {
        for (seat in 1..seats) {
            if (cinemaRoom[row - 1][seat - 1] == 'B') currentIncome += calculateTicketPrice(row, seat, cinemaRoom)
        }
    }

    // Possible Total income;
    var possibleTotalIncome = 0
    for (row in 1..rows) {
        for (seat in 1..seats) {
            possibleTotalIncome += calculateTicketPrice(row, seat, cinemaRoom)
        }
    }
    println("Number of purchased tickets: $purchasedTickets")
    println("Percentage: $formatPercentage%")
    println("Current income: $$currentIncome")
    println("Total income: $$possibleTotalIncome")
}

fun buyTicket(cinemaRoom: MutableList<MutableList<Char>>) {
    var hasSold = false
    while (!hasSold) {
        println()
        println("Enter a row number:")
        val row = readln().toInt()
        println("Enter a seat number in that row:")
        val seat = readln().toInt()
        if (row !in 1..cinemaRoom.size || seat !in 1..cinemaRoom.first().size) {
            println("Wrong input!")
        } else if (cinemaRoom[row - 1][seat - 1] == 'S') {
            val ticketPrice = calculateTicketPrice(row, seat, cinemaRoom)
            println("Ticket price: $$ticketPrice")
            cinemaRoom[row - 1][seat - 1] = 'B'
            hasSold = true
        } else println("That ticket has already been purchased!")
    }
}

fun buildCinemaRoom1(rows: Int, seats: Int, cinemaRoom: MutableList<MutableList<Char>>) {
    // cinema info
    for (i in 1..rows) {
        val chars = mutableListOf<Char>() // rows times char array
        for (j in 1..seats) { // seat times char
            chars.add('S')
        }
        cinemaRoom.add(chars)
    }
}

fun getCinemaInfo(rows: Int, seats: Int, cinemaRoom: MutableList<MutableList<Char>>) {
    // top label
    println("Cinema:")
    print("  ")
    for (s in 1..seats) print("$s ")
    println()
    // cinema info
    for (i in 1..rows) {
        print("$i")
        for (j in 1..seats) {
            print(" ${cinemaRoom[i - 1][j - 1]}")
        }
        println()
    }
}

fun calculateTicketPrice(row: Int, seat: Int, cinemaRoom: MutableList<MutableList<Char>>): Int {

    return if (cinemaRoom.first().size * cinemaRoom.size <= 60) {
        PRICE
    } else if (row <= cinemaRoom.size / 2) PRICE else PRICE - 2
}

