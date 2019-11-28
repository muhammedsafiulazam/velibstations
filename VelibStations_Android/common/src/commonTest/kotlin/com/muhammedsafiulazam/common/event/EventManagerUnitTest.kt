import com.muhammedsafiulazam.common.addon.AddOnManager
import com.muhammedsafiulazam.common.addon.AddOnType
import com.muhammedsafiulazam.common.core.CommonUnitTest
import com.muhammedsafiulazam.common.event.Event
import com.muhammedsafiulazam.common.event.IEventManager
import com.muhammedsafiulazam.common.utils.CoroutineUtils
import com.muhammedsafiulazam.common.service.model.Error
import kotlinx.coroutines.delay
import kotlin.test.Test
import kotlin.test.asserter

class EventManagerUnitTest : CommonUnitTest() {

    private val DUMMY_EVENT_TYPE: String = "DUMMY_EVENT_TYPE"
    private val DUMMY_EVENT_DATA: String = "DUMMY_EVENT_DATA"
    private val DUMMY_EVENT_ERROR: Error = Error("", "")

    @Test
    fun exchangeEvents() = CoroutineUtils.runBlocking() {
        var e: Event? = null

        val eventManager: IEventManager = AddOnManager.getAddOn(AddOnType.EVENT_MANAGER) as IEventManager
        eventManager.subscribe(callback = { event: Event -> Unit
            e = event
        })

        eventManager.send(createDummyEvent())

        CoroutineUtils.delay(DELAY_MINIMUM)
        asserter.assertTrue("exchangeEvents", e != null && e!!.data != null && e!!.error != null)
    }

    private fun createDummyEvent() : Event {
        return Event(DUMMY_EVENT_TYPE, DUMMY_EVENT_DATA, DUMMY_EVENT_ERROR)
    }
}