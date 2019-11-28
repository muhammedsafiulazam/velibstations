import com.muhammedsafiulazam.common.addon.AddOnManager
import com.muhammedsafiulazam.common.addon.AddOnType
import com.muhammedsafiulazam.common.core.AndroidUnitTest
import com.muhammedsafiulazam.common.database.IDatabaseManager
import com.muhammedsafiulazam.common.database.velib.event.VelibDatabaseEventType
import com.muhammedsafiulazam.common.event.Event
import com.muhammedsafiulazam.common.event.IEventManager
import com.muhammedsafiulazam.common.service.velib.model.Dataset
import com.muhammedsafiulazam.common.utils.CoroutineUtils
import kotlin.test.Test
import kotlin.test.asserter

class VelibDatabaseUnitTest : AndroidUnitTest() {
    @Test
    fun saveData() = CoroutineUtils.runBlocking() {
        var e: Event? = null

        val eventManager: IEventManager = AddOnManager.getAddOn(AddOnType.EVENT_MANAGER) as IEventManager
        eventManager.subscribe(callback = { event: Event -> Unit
            if (event.type.equals(VelibDatabaseEventType.SAVE_DATA)) {
                e = event
            }
        })

        val databaseManager: IDatabaseManager = AddOnManager.getAddOn(AddOnType.DATABASE_MANAGER) as IDatabaseManager
        val dataset: Dataset = Dataset(0, null, null)
        databaseManager.getVelibDatabase().saveData(dataset)

        CoroutineUtils.delay(DELAY_MINIMUM)
        asserter.assertTrue("saveData", e != e != null && e!!.data != null && e!!.error == null)
    }
}