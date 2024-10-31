package com.artemObrazumov.drinkin.core.presentation

import android.content.Context
import com.artemObrazumov.drinkin.core.data.Resource

fun Resource.StringResource.resolve(
    context: Context
): String {
    return context.getString(this.reference)
}

fun Resource.StringResource.resolveDynamically(
    context: Context,
    vararg args: Any
): String {
    return context.getString(this.reference, *args)
}