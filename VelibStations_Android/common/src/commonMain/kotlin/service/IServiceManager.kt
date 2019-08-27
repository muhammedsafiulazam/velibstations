package service

import service.weather.IWeatherService

interface IServiceManager {

    fun getWeatherService() : IWeatherService
}