package cl.ricardoallendes.miapp.clases

class ItemMenu(
    val nombre: String,
    val precio: Int
) {
    constructor(nombre: String, precio: String) : this(nombre, precio.toInt())
}