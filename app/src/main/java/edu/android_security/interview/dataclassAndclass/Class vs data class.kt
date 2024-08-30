package edu.android_security.interview.dataclassAndclass


// main method
data class DataClassSample(val name: String, val age: Int)
class ClassSample(val name: String, val age: Int)

fun main() {
    val dataClassSample = DataClassSample("John", 30)
    //  1 copy data class
    val dataClassSampleCP = dataClassSample.copy(age = 35)
    val dataClassSampleCP2 = dataClassSample.copy(age = 30)
    // 2 component in
    val (name) = dataClassSample
    val classSample = ClassSample("John", 30)
    // 3 toString
    println(dataClassSample.toString())
    // 4 equals
    println(dataClassSample == dataClassSampleCP)
    println(dataClassSample == dataClassSampleCP2)
    println(dataClassSample.hashCode() == dataClassSampleCP2.hashCode())
    // 5 hashCode
    println(dataClassSample.hashCode())
    println(dataClassSampleCP.hashCode())
    println(dataClassSampleCP2.hashCode())
    println(name)

}