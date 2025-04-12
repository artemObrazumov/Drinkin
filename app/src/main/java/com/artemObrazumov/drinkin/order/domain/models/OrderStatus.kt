package com.artemObrazumov.drinkin.order.domain.models

import com.artemObrazumov.drinkin.R
import com.artemObrazumov.drinkin.core.data.Resource

enum class OrderStatus {
    IN_PROCESS, READY, CLOSED
}

fun OrderStatus.asStringResource(): Resource.StringResource =
    when(this) {
        OrderStatus.IN_PROCESS -> Resource.StringResource(R.string.in_process)
        OrderStatus.READY -> Resource.StringResource(R.string.ready)
        OrderStatus.CLOSED -> Resource.StringResource(R.string.finished)
    }
