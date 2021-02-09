package org.lighthousegames.logging

interface TagProvider {
    fun createTag(fromClass: String?): Pair<String, String>
}