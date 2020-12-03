package com.example.cft_converter.data

import android.annotation.SuppressLint
import com.example.cft_converter.data.database.RealmDb
import com.example.cft_converter.data.network.CurrencyRemoteDataSource
import com.example.cft_converter.domain.CurrencyMapper
import com.example.cft_converter.domain.ICurrencyRepository
import com.example.cft_converter.domain.entity.CurrencyBody
import io.reactivex.android.schedulers.AndroidSchedulers


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

    @SuppressLint("CheckResult")
    override fun requestListCurrencyFromDb(success: (List<CurrencyBody>) -> Unit, error: (String) -> Unit) {
        realmDb.requestCurrencyEntityList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {list->
                if (list.isEmpty()) {
                    requestListCurrencyFromNetwork(success, error)
                } else {
                    val mapper = CurrencyMapper()
                    val currencyEntityList = mapper.mapping(list)
                    success(currencyEntityList)
                }
            }
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