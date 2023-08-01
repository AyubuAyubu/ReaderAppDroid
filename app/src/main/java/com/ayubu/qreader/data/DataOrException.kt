package com.ayubu.qreader.data

import com.google.common.cache.AbstractLoadingCache

data class DataOrException<T,Boolean,E:Exception>(
    var data:T?=null,
    var loading: Boolean?=null,
    var e:E?=null
)
