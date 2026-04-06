package com.rogue21.taskapp.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object TaskListRoute

@Serializable
object SettingsRoute
//No properties, nor arguments. Just a marker for a destination
//“Go to Settings. No extra info needed.”

@Serializable
data class AddEditTaskRoute(
    val taskId: Int? = null
)
//Has properties and Carries arguments
//Represents data + destination
//“Go to Add/Edit screen with this specific data.”