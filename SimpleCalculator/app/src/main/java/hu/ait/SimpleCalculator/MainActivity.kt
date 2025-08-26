package hu.ait.SimpleCalculator

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import hu.ait.SimpleCalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        enableEdgeToEdge()

        setContentView(binding.root)

        var result: Int

        binding.btnMinus.setOnClickListener {
            // binding.tvTime.setText("${Date(System.currentTimeMillis().toString())}")
            var numA = binding.etNumA.text.toString().toInt()
            var numB = binding.etNumB.text.toString().toInt()
            result = numA - numB
            binding.tvTime.text = "Result: $result"
        }

        binding.btnAdd.setOnClickListener {
            var numA = binding.etNumA.text.toString().toInt()
            var numB = binding.etNumB.text.toString().toInt()
            result = numA + numB
            binding.tvTime.text = "Result: $result"
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}