package net.henryhc.redstone.common

/**
 * Gets the value from named [MatchResult.groups].
 */
fun MatchResult.getGroupValue(groupName: String): String = this.groups[groupName]?.value!!
