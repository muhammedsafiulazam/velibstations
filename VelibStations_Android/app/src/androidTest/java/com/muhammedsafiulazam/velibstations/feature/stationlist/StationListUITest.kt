import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.muhammedsafiulazam.common.event.Event
import com.muhammedsafiulazam.velibstations.R
import com.muhammedsafiulazam.velibstations.core.BaseUITest
import com.muhammedsafiulazam.velibstations.core.IAfterWait
import com.muhammedsafiulazam.velibstations.core.IBeforeWait
import com.muhammedsafiulazam.velibstations.feature.stationlist.event.StationListEventType
import com.muhammedsafiulazam.velibstations.feature.stationlist.view.StationListActivity
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

/**
 * Created by Muhammed Safiul Azam on 29/07/2019.
 */

@RunWith(AndroidJUnit4ClassRunner::class)
class StationListUITest : BaseUITest() {

    @Rule @JvmField
    var mActivityTestRule: ActivityTestRule<StationListActivity> = ActivityTestRule(
        StationListActivity::class.java, true, false)

    @Test
    fun loadStationList(){
        wait(Arrays.asList(StationListEventType.VIEWMODEL_RESPONSE_LOAD_DATA), object : IBeforeWait {
            override fun beforeWait() {
                val intent = Intent(getContext(), StationListActivity::class.java)
                mActivityTestRule.launchActivity(intent)
                allowPermissions()
            }

        }, object : IAfterWait {
            override fun afterWait(events: List<Event>) {

                // Basic tests.
                onView(withId(R.id.stationlist_pgb_loader)).check(matches(not(isDisplayed())))
            }
        })
    }
}