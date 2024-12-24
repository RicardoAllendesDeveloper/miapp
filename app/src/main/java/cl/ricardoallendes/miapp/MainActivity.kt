package cl.ricardoallendes.miapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import cl.ricardoallendes.miapp.clases.CuentaMesa
import cl.ricardoallendes.miapp.clases.ItemMenu
import cl.ricardoallendes.miapp.clases.ItemMesa
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var pastelDeChocloCantidad: EditText
    private lateinit var cazuelaCantidad: EditText
    private lateinit var pastelSubtotalTextView: TextView
    private lateinit var cazuelaSubtotalTextView: TextView
    private lateinit var propinaSwitch: Switch
    private lateinit var subtotalTextView: TextView
    private lateinit var propinaTextView: TextView
    private lateinit var totalTextView: TextView

    private val pastelDeChoclo = ItemMenu("Pastel de Choclo", 12000)
    private val cazuela = ItemMenu("Cazuela", 10000)

    private val cuentaMesa = CuentaMesa(1)
    private val formatoPesos: NumberFormat = NumberFormat.getCurrencyInstance(Locale("es", "CL"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pastelDeChocloCantidad = findViewById(R.id.etPastelCantidad)
        cazuelaCantidad = findViewById(R.id.etCazuelaCantidad)
        pastelSubtotalTextView = findViewById(R.id.tvPastelSubtotal)
        cazuelaSubtotalTextView = findViewById(R.id.tvCazuelaSubtotal)
        propinaSwitch = findViewById(R.id.switchPropina)
        subtotalTextView = findViewById(R.id.tvSubtotal)
        propinaTextView = findViewById(R.id.tvPropina)
        totalTextView = findViewById(R.id.tvTotal)

        cuentaMesa.agregarItem(pastelDeChoclo, 0)
        cuentaMesa.agregarItem(cazuela, 0)

        pastelDeChocloCantidad.addTextChangedListener(cantidadWatcher(pastelDeChoclo, pastelSubtotalTextView))
        cazuelaCantidad.addTextChangedListener(cantidadWatcher(cazuela, cazuelaSubtotalTextView))
        propinaSwitch.setOnCheckedChangeListener { _, isChecked ->
            cuentaMesa.aceptarPropina = isChecked
            actualizarTotales()
        }
    }

    private fun cantidadWatcher(itemMenu: ItemMenu, subtotalTextView: TextView): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val cantidad = s?.toString()?.toIntOrNull() ?: 0
                val itemMesa = cuentaMesa.items.find { it.itemMenu == itemMenu }
                itemMesa?.cantidad = cantidad
                subtotalTextView.text = " ${formatoPesos.format(itemMesa?.calcularSubtotal() ?: 0)}"
                actualizarTotales()
            }

            override fun afterTextChanged(s: Editable?) {}
        }
    }

    private fun actualizarTotales() {
        val totalSinPropina = cuentaMesa.calcularTotalSinPropina()
        val propina = cuentaMesa.calcularPropina()
        val totalConPropina = cuentaMesa.calcularTotalConPropina()

        subtotalTextView.text = formatoPesos.format(totalSinPropina)
        propinaTextView.text = formatoPesos.format(propina)
        totalTextView.text = formatoPesos.format(totalConPropina)
    }
}