package id.trivia.dagger

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import id.trivia.dagger.api.Endpoints
import id.trivia.dagger.di.AppComponent
import id.trivia.dagger.di.DaggerAppComponent
import id.trivia.dagger.di.OkHttpModule
import id.trivia.dagger.di.RetrofitModule
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private val TAG = javaClass.simpleName
    lateinit var appComponent: AppComponent

    @Inject //object dari AppComponent
    lateinit var endpoints: Endpoints

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermission()

        appComponent = DaggerAppComponent.builder()
            .okHttpModule(OkHttpModule())
            .retrofitModule(RetrofitModule())
            .build()
        appComponent.inject(this)
        edit_text_search_user.setOnEditorActionListener { _, actionId, keyEvent ->
            showLoading()
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                endpoints.searchUsers("FirmanMFK")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            hideLoading()
                            val jsonResponse = JSONObject(it.string())
                            Log.d(TAG, "jsonResponse: $jsonResponse")
                            text_view_result.text = jsonResponse.toString()
                        },
                        {
                            hideLoading()
                            it.printStackTrace()
                        }
                    )
            }
            false
        }
    }

    private fun hideLoading() {
        progress_bar_activity_main.visibility = View.GONE
        text_view_result.visibility = View.VISIBLE
    }

    private fun showLoading() {
        progress_bar_activity_main.visibility = View.VISIBLE
        text_view_result.visibility = View.GONE
    }

    private fun checkPermission() {
        Dexter.withActivity(this)
            .withPermission(Manifest.permission.INTERNET)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    /* nothing to do in here */
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    /* nothing to do in here */
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    /* nothing to do in here */
                }
            })
            .check()
    }
}
