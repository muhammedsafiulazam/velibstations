package com.muhammedsafiulazam.common.service

import com.muhammedsafiulazam.common.service.velib.IVelibService
import com.muhammedsafiulazam.common.service.velib.VelibService
import com.muhammedsafiulazam.common.utils.ServiceUtils
import io.ktor.client.HttpClient

class ServiceManager : IServiceManager {

    private val mHttpClient: HttpClient = ServiceUtils.HTTP_CLIENT
    private val mBaseURL: String = "https://opendata.paris.fr/api/records/1.0/search/?"

    private val mServiceClient: ServiceClient by lazy {
        val serviceClient = ServiceClient(mHttpClient, mBaseURL)
        serviceClient
    }

    private val mVelibService: IVelibService by lazy {
        VelibService(mServiceClient)
    }

    override fun getVelibService(): IVelibService {
        return mVelibService
    }
}