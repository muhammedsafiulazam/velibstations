import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.muhammedsafiulazam.common.event.Event
import com.muhammedsafiulazam.tests.BaseUITest
import com.muhammedsafiulazam.tests.IAfterWait
import com.muhammedsafiulazam.tests.IBeforeWait
import com.muhammedsafiulazam.velibstations.feature.stationlist.StationListActivity
import com.muhammedsafiulazam.velibstations.feature.stationlist.event.StationListEventType
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Muhammed Safiul Azam on 29/07/2019.
 */

@RunWith(AndroidJUnit4ClassRunner::class)
class StationListUITest : BaseUITest() {

    @Rule @JvmField
    var mActivityTestRule: ActivityTestRule<StationListActivity> = ActivityTestRule(StationListActivity::class.java, true, false)

    @Test
    fun loadStationInfo(){
        wait(StationListEventType.LOAD_DATA_RESPONSE, object : IBeforeWait {
            override fun beforeWait() {
            }

        }, object : IAfterWait {
            override fun afterWait(events: List<Event>) {
            }
        })
    }
}