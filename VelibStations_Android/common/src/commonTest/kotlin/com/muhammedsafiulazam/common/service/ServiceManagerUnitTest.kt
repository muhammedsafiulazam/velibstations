import com.muhammedsafiulazam.common.addon.AddOnManager
import com.muhammedsafiulazam.common.addon.AddOnType
import com.muhammedsafiulazam.common.core.CommonUnitTest
import com.muhammedsafiulazam.common.service.IServiceManager
import com.muhammedsafiulazam.common.service.velib.IVelibService
import kotlin.test.Test
import kotlin.test.asserter

class ServiceManagerUnitTest : CommonUnitTest() {

    @Test
    fun accessVelibService() {
        val serviceManager: IServiceManager = AddOnManager.getAddOn(AddOnType.SERVICE_MANAGER) as IServiceManager
        val velibService: IVelibService? = serviceManager.getVelibService()
        asserter.assertTrue("accessVelibService", velibService != null)
    }
}