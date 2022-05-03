package uz.evkalipt.sevenmodullesson121

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.item_tab.view.*
import uz.evkalipt.sevenmodullesson121.adapters.MyPagerAdapter
import uz.evkalipt.sevenmodullesson121.databinding.ActivitySplashBinding
import uz.evkalipt.sevenmodullesson121.databinding.ItemTabBinding
import uz.evkalipt.sevenmodullesson121.models.Word

@Suppress("DEPRECATION")
class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding
    lateinit var list: ArrayList<Word>
    lateinit var myPagerAdapter: MyPagerAdapter
    lateinit var handler:Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadList()
        myPagerAdapter = MyPagerAdapter(list)
        binding.viewPagerSplash.adapter = myPagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPagerSplash)

        val tabCount = binding.tabLayout.tabCount
        for (i in 0..tabCount){
            var tabView = ItemTabBinding.inflate(layoutInflater)
            binding.tabLayout.getTabAt(i)?.customView = tabView.root

            if (i==0){
                tabView.circle2.visibility = View.VISIBLE
            }else{
                tabView.circle2.visibility = View.INVISIBLE
            }
        }

        binding.tabLayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                var tabView = tab?.customView
                tabView?.circle2?.visibility = View.VISIBLE
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                var tabView = tab?.customView
                tabView?.circle2?.visibility = View.INVISIBLE
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
        handler = Handler()
            handler.postDelayed({
                val tab11: TabLayout.Tab = binding.tabLayout.getTabAt(1)!!
                tab11.select()
            }, 1000)

        handler.postDelayed({
            val tab11: TabLayout.Tab = binding.tabLayout.getTabAt(2)!!
            tab11.select()
        }, 2000)

        handler.postDelayed({
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        },3000)
    }

    private fun loadList() {
        list = ArrayList()
        list.add(
            Word(
                "Detayli Kelime inceleme",
                "Sonuc ekraninda kelimenin anlamlarini inceleyin, tellafuzuni dinlayin, dilerseniz kutuphanenize kaydedin"
            )
        )
        list.add(
            Word(
                "Detayli Kelime inceleme",
                "Sonuc ekraninda kelimenin anlamlarini inceleyin, tellafuzuni dinlayin, dilerseniz kutuphanenize kaydedin"
            )
        )
        list.add(
            Word(
                "Detayli Kelime inceleme",
                "Sonuc ekraninda kelimenin anlamlarini inceleyin, tellafuzuni dinlayin, dilerseniz kutuphanenize kaydedin"
            )
        )

    }
}