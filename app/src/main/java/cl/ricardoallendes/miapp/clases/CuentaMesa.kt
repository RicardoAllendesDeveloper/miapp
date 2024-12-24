package cl.ricardoallendes.miapp.clases

class CuentaMesa(
    val mesa: Int,
    val items: MutableList<ItemMesa> = mutableListOf(),
    var aceptarPropina: Boolean = true
) {

    constructor(mesa: Int, itemMesa: ItemMesa) : this(mesa, mutableListOf(itemMesa))

    fun agregarItem(itemMenu: ItemMenu, cantidad: Int) {
        val itemMesa = ItemMesa(itemMenu, cantidad)
        items.add(itemMesa)
    }

    fun agregarItem(itemMesa: ItemMesa) {
        items.add(itemMesa)
    }

    fun calcularTotalSinPropina(): Int {
        var totalSinPropina = 0
        items.forEach {
            totalSinPropina += it.calcularSubtotal()
        }
        return totalSinPropina
    }

    fun calcularPropina(): Int {
        return if (aceptarPropina) {
            (calcularTotalSinPropina() * 0.10).toInt()
        } else {
            0
        }
    }

    fun calcularTotalConPropina(): Int {
        return calcularTotalSinPropina() + calcularPropina()
    }
}