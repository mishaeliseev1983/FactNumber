package com.melyseev.factnumber.data.cache

import com.melyseev.factnumber.data.NumberData

class MapperNumberDataToNumberCache: NumberData.Mapper<NumberCache> {
    override fun map(id: String, fact: String): NumberCache {
        return NumberCache(id = id, fact = fact, date = System.currentTimeMillis())
    }
}