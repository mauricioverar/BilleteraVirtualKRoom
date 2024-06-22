package com.mauriciovera.billeteravirtualkroom

import com.mauriciovera.billeteravirtualkroom.model.network.RetrofitHelp
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class RetrofitWalletInstanceTest {
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setup(){
        mockWebServer= MockWebServer()
    }

    @After
    fun tearDown(){
        mockWebServer.shutdown()
    }

    @Test
    fun testRetrofit(){
        val expectedBaseUrl = mockWebServer.url("/").toString()
        val retrofit = Retrofit.Builder()
            .baseUrl(expectedBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        RetrofitHelp.getInstance = retrofit
        val retrofitInstance = RetrofitHelp.getInstance
        Assert.assertEquals(expectedBaseUrl, retrofitInstance.baseUrl().toString())
        //import junit assert
    }
}