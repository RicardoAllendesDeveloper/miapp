package cl.ricardoallendes.miapp.clases

class ItemMesa(
    val itemMenu: ItemMenu,
    var cantidad: Int
) {
    fun calcularSubtotal(): Int {
        return itemMenu.precio * cantidad
    }
}