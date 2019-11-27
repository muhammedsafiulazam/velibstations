import com.muhammedsafiulazam.common.addon.AddOnManager
import com.muhammedsafiulazam.common.addon.AddOnType
import com.muhammedsafiulazam.common.core.CommonUnitTest
import com.muhammedsafiulazam.common.event.Event
import com.muhammedsafiulazam.common.event.IEventManager
import com.muhammedsafiulazam.common.service.IServiceManager
import com.muhammedsafiulazam.common.service.velib.event.VelibServiceEventType
import com.muhammedsafiulazam.common.utils.CoroutineUtils
import kotlin.test.Test
import kotlin.test.asserter

class VelibServiceTest : CommonUnitTest() {
    @Test
    fun getData() = CoroutineUtils.runBlocking() {
        var e: Event? = null

        val eventManager: IEventManager = AddOnManager.getAddOn(AddOnType.EVENT_MANAGER) as IEventManager
        eventManager.subscribe(callback = { event: Event -> Unit
            if (event.type.equals(VelibServiceEventType.GET_DATA)) {
                e = event
            }
        })

        val serviceManager: IServiceManager = AddOnManager.getAddOn(AddOnType.SERVICE_MANAGER) as IServiceManager
        serviceManager.getVelibService().getData()

        CoroutineUtils.delay(DELAY_AVERAGE)
        asserter.assertTrue("getData", e != null && e!!.data != null && e!!.error == null)
    }
}