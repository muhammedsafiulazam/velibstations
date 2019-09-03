package com.muhammedsafiulazam.velibstations.core

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import com.muhammedsafiulazam.velibstations.activity.BaseActivity
import com.muhammedsafiulazam.velibstations.addon.AddOnManager
import com.muhammedsafiulazam.velibstations.addon.AddOnType
import com.muhammedsafiulazam.common.event.Event
import com.muhammedsafiulazam.common.event.IEventManager
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.channels.ReceiveChannel
import org.junit.Assert
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * Created by Muhammed Safiul Azam on 29/07/2019.
 */

open class BaseUITest {

    private val MAX_WAIT_TIME_MILLIS: Long = 5000 // 5 secs
    private val MAX_WAIT_TIME_REACHED: String = "Max wait time reached while waiting for mEvents."
    private val EXCEPTION_OCCURRED = "Exception occurred, please check log for stacktrace.";

    private val mEvents: MutableList<Event> = mutableListOf()
    private val mEventTypes: MutableList<String> = mutableListOf()

    private var mCountDownLatch: CountDownLatch? = null
    private var mReceiveChannel: ReceiveChannel<Event>? = null

    private val mContext: Context by lazy {
        InstrumentationRegistry.getInstrumentation().getTargetContext()
    }

    private val mResources: Resources by lazy {
        mContext.resources
    }

    init {
        val eventManager: IEventManager? = AddOnManager.getAddOn(AddOnType.EVENT_MANAGER) as IEventManager?
        mReceiveChannel = eventManager?.subscribe(callback = { event: Event -> Unit
            onReceiveEvent(event)
        })
    }

    fun getContext() : Context {
        return mContext
    }

    fun getResources() : Resources {
        return mResources
    }

    fun getActivity(): Activity? {
        var activity: Activity? = null
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            val currentActivity: Activity
            val resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED)
            if (resumedActivities.iterator().hasNext()) {
                currentActivity = resumedActivities.iterator().next() as Activity
                activity = currentActivity
            }
        }
        return activity
    }

    fun assertActivity(activity: Class<out BaseActivity>) {
        val currentActivity: Activity? = getActivity()
        assertTrue(currentActivity != null && currentActivity::class.java.equals(activity::class.java))
    }

    fun wait(eventType: String, beforeWait: IBeforeWait? = null, afterWait: IAfterWait? = null) {
        wait(Arrays.asList(eventType), beforeWait, afterWait)
    }

    fun wait(eventTypes: List<String>, beforeWait: IBeforeWait? = null, afterWait: IAfterWait? = null) {
        clear()
        this.mEventTypes.addAll(eventTypes)

        // Count down latch.
        mCountDownLatch = CountDownLatch(eventTypes.size)

        // Before wait.
        try {
            beforeWait?.beforeWait()
        } catch (e: Exception) {
            Assert.fail(EXCEPTION_OCCURRED)
            mCountDownLatch!!.countDown()
        }

        // On wait.
        if (mCountDownLatch!!.getCount() > 0) {
            if (!mCountDownLatch!!.await(MAX_WAIT_TIME_MILLIS, TimeUnit.MILLISECONDS)) {
                Assert.fail(MAX_WAIT_TIME_REACHED)
            }
        }

        // After wait.
        if (afterWait != null) {
            afterWait.afterWait(mEvents)
        }
    }

    private fun clear() {
        mEvents.clear()
        mEventTypes.clear()
        mCountDownLatch?.countDown()
        mCountDownLatch = null
    }

    fun onReceiveEvent(event: Event) {
        if(mEventTypes.contains(event.type)) {
            mEventTypes.remove(event.type)
            mCountDownLatch?.countDown()
        }
    }
}