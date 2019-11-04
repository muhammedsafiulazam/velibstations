import android.content.Intent
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.muhammedsafiulazam.common.addon.AddOnManager
import com.muhammedsafiulazam.common.addon.AddOnType
import com.muhammedsafiulazam.common.event.Event
import com.muhammedsafiulazam.common.service.velib.model.Record
import com.muhammedsafiulazam.common.view.ViewManager
import com.muhammedsafiulazam.test.BaseUITest
import com.muhammedsafiulazam.test.IAfterDelay
import com.muhammedsafiulazam.test.IBeforeDelay
import com.muhammedsafiulazam.test.RecyclerViewAssertion
import com.muhammedsafiulazam.test.RecyclerViewAssertion.withItemCount
import com.muhammedsafiulazam.velibstations.R
import com.muhammedsafiulazam.velibstations.feature.stationinfo.StationInfoActivity
import com.muhammedsafiulazam.velibstations.feature.stationinfo.event.StationInfoEventType
import com.tyro.oss.arbitrater.arbitraryInstance
import org.hamcrest.Matchers
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Muhammed Safiul Azam on 29/07/2019.
 */

@RunWith(AndroidJUnit4ClassRunner::class)
class StationInfoUITest : BaseUITest() {

    @Rule @JvmField
    var mActivityTestRule: ActivityTestRule<StationInfoActivity> = ActivityTestRule(StationInfoActivity::class.java, true, false)

    @Test
    fun loadStationInfo(){
        delay(StationInfoEventType.LOAD_DATA_RESPONSE, object : IBeforeDelay {
            override fun beforeWait() {
                val intent = Intent(getContext(), StationInfoActivity::class.java)
                val viewManager: ViewManager = AddOnManager.getAddOn(AddOnType.VIEW_MANAGEER) as ViewManager
                val record: Record = Record::class.arbitraryInstance()
                val identifier: String = viewManager.push(record) as String
                intent.putExtra(ViewManager.KEY_DATA_IDENTIFIER, identifier)
                mActivityTestRule.launchActivity(intent)
                allowPermissions()
            }

        }, object : IAfterDelay {
            override fun afterWait(events: List<Event>) {

                // Basic tests.
                onView(withId(R.id.stationinfo_ryv_fields)).check(withItemCount(Matchers.greaterThan(0)))
            }
        })
    }
}