package tw.com.donhi.movies

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import tw.com.donhi.movies.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: MovieAdapter
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        //取得 RecycleView 並做相關設定
        val recycler = binding.content.recycler
        recycler.setHasFixedSize(true)
        recycler.layoutManager = LinearLayoutManager(this)

        val client = OkHttpClient()
        CoroutineScope(Dispatchers.IO).launch {
            val request = Request.Builder()
                .url("https://api.themoviedb.org/3/movie/popular?language=zh-TW&page=1")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1NDU0MzM3MWUwZjA0OWIzMzg2Njg4OGMxZDQxYWE5YyIsIm5iZiI6MTcyNjY0MDk2Mi42MDk4OTEsInN1YiI6IjY2ZWE3MWQxYjI5MTdlYjE4MDBiNTAwMyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.EEB7Xuqy3GXRfxQ71XFEPXnk3TvWLyxWcRkQ6Ks226Y")
                .build()

            val response = client.newCall(request).execute()
            val json = response.body?.string()
            Log.d(TAG, "onCreate: $json")

            val result = Gson().fromJson(json, MovieResult::class.java)
            result.results.forEach { movie ->
                Log.d(TAG, "onCreate: ${movie.title}")
            }
            //Adapter
            adapter = MovieAdapter(result.results)
            //讓程式碼在UI執行緒裡執行
            runOnUiThread{
                recycler.adapter = adapter
            }

        }

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

}