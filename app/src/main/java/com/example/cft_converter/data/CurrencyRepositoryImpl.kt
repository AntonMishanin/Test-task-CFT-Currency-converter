package com.example.cft_converter.data

import com.example.cft_converter.data.database.RealmDb
import com.example.cft_converter.data.network.CurrencyRemoteDataSource
import com.example.cft_converter.domain.CurrencyMapper
import com.example.cft_converter.domain.ICurrencyRepository
import com.example.cft_converter.domain.entity.CurrencyBody

class CurrencyRepositoryImpl(
    private val realmDb: RealmDb,
    private val request: CurrencyRemoteDataSource
) : ICurrencyRepository {

   override fun requestListCurrencyFromNetwork(success: (List<CurrencyBody>)->Unit, error: (String) -> Unit) {
        request.requestListCurrency({
            saveCurrencyToDb(it, success, error)
        }, {
            error(it)
        })
    }

    override fun requestListCurrencyFromDb(success: (List<CurrencyBody>) -> Unit, error: (String) -> Unit) {
        realmDb.requestCurrencyEntityList({
            if (it.isEmpty()) {
                requestListCurrencyFromNetwork(success, error)
            } else {
                val mapper = CurrencyMapper()
                val currencyEntityList = mapper.mapping(it)
                success(currencyEntityList)
            }
        },{
            error(it)
        })
    }

    private fun saveCurrencyToDb(
        inputList: List<CurrencyBody>,
        success:(List<CurrencyBody>)->Unit,
        error:(String)->Unit
    ) {
        realmDb.saveCurrencyEntity(inputList,{
            requestListCurrencyFromDb(success, error)
        },{
            error(it)
        })
    }
}