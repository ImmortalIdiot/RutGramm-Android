package di

import android.content.Context

internal interface ResourceProvider {
    fun getString(resId: Int): String
}

internal class ResourceProviderImpl(private val context: Context) : ResourceProvider {
    override fun getString(resId: Int): String = context.getString(resId)
}
