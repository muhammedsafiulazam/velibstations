import com.muhammedsafiulazam.common.addon.AddOnManager
import com.muhammedsafiulazam.common.addon.AddOnType
import com.muhammedsafiulazam.common.event.Event
import com.muhammedsafiulazam.common.event.IEventManager
import com.muhammedsafiulazam.common.utils.CoroutineUtils
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.asserter

class EventManagerUnitTest {
    @BeforeTest
    fun beforeTest() {
        CoroutineUtils.unitTests()
    }

    @Test
    fun receiveEvents() = CoroutineUtils.runBlocking() {
        var e: Event? = null

        val eventManager: IEventManager = AddOnManager.getAddOn(AddOnType.EVENT_MANAGER) as IEventManager
        eventManager.subscribe(callback = { event: Event -> Unit
            e = event
        })

        val event: Event = Event("", null, null)
        eventManager.send(event)

        CoroutineUtils.delay(1000)
        asserter.assertTrue("", e != null)
    }
}