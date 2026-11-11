package erasom.herramientas.aivy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import erasom.herramientas.aivy.databinding.ActivityMapsBinding

class MapsActivity : ToolActivity() {

    private lateinit var binding: ActivityMapsBinding
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configurar osmdroid (OpenStreetMap - open source)
        Configuration.getInstance().load(this, getSharedPreferences("osmdroid", MODE_PRIVATE))

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mapView = binding.mapView
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.setMultiTouchControls(true)

        // Posición inicial (Bogotá, Colombia)
        val startPoint = GeoPoint(4.7110, -74.0721)
        mapView.controller.setZoom(12.0)
        mapView.controller.setCenter(startPoint)

        // Marcador inicial
        val marker = Marker(mapView)
        marker.position = startPoint
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        marker.title = "Ubicación inicial"
        mapView.overlays.add(marker)

        binding.btnSearch.setOnClickListener {
            val query = binding.etQuery.text.toString().trim()
            if (query.isNotEmpty()) {
                // Abrir en navegador para búsqueda (OSM no tiene geocoding integrado sin API)
                val url = "https://www.openstreetmap.org/search?query=${android.net.Uri.encode(query)}"
                startActivity(android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(url)))
            }
        }

        binding.btnMyLocation.setOnClickListener {
            // Centrar en ubicación por defecto (se puede mejorar con GPS)
            mapView.controller.animateTo(startPoint)
            mapView.controller.setZoom(15.0)
        }

        binding.btnBack.setOnClickListener { finish() }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }
}
