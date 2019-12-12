package tokopedia.app.jetmovie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.movie.ui.PopularMovieFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inflateFragment()
    }

    private fun inflateFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_container, PopularMovieFragment())
            .commit()
    }

}
