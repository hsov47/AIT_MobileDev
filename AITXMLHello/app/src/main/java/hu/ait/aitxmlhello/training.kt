package hu.ait.aitxmlhello

fun main(){
    x(3)
    x()
    x(p2 = 78)

    var list: List<Int> = mutableListOf(3, 45, 6, 8, 9,1, 4)

    list.filter{it % 2 == 0}.forEach{println(it)}

    list.filter({num: Int -> num % 2 == 0 }).forEach({num -> println(num)})

    list.filter({num: Int -> num % 2 == 0 }).map({n: Int -> n * 3}).forEach({num -> println(num)})

    list.filter{it % 2 == 0 }.
        map{it * 3}.sortedBy{it}.forEach{println("lambda $it")}


    var filtering = fun(num: Int): Boolean = num % 2 == 0

    list.filter{filtering(it)}.
    map{it * 3}.sortedBy{it}.forEach{println("anonymous $it")}

    list.filter(::filtering).map{it * 3}.sortedBy{it}.forEach{println("anonymous $it")}

    val s1 = Student("eva", 30)
    val s2 = Student("Sarah", 19)
    val s3 = Student("Peter", 22)

    var students: List<Student> = mutableListOf(s1, s2, s3)

    students.filter {it.name.contains("e")}.filter { it.age < 20 }.forEach { (println(it)) }
    students.filter {"e" in it.name}.filter { (_, age) -> age < 20}.forEach { (println(it)) }

}

data class Student(val name: String, val age: Int) {

}

fun filtering(num: Int):Boolean {
    return num % 2 == 0
}

fun x(p: Int = 6, p2: Int = 5) {
    println(p)
    println(p2)
}

fun square(a: Int) = a * a

fun squareNotSingle(a: Int): Int {
    return a * a
}