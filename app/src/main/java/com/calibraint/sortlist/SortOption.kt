package com.calibraint.sortlist

sealed class SortOption(val name: String)

object None : SortOption("None")

object ByName : SortOption("ByName")

object BySymbols : SortOption("BySymbols")

fun getSortOptionFromName(name: String): SortOption = when (name) {
    "ByName" -> ByName
    "BySymbols" -> BySymbols
    else -> None
}
