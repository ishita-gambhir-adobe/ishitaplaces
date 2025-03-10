package com.ishitaplaces

import android.location.Location
import com.adobe.marketing.mobile.Places
import com.adobe.marketing.mobile.places.PlacesPOI
import com.facebook.react.bridge.*
import com.google.android.gms.location.Geofence
import com.adobe.marketing.mobile.places.PlacesAuthorizationStatus


object RCTAEPPlacesDataBridge {

    // Add these constants at the top
private const val AEP_PLACES_GEOFENCE_LATITUDE = "latitude"
private const val AEP_PLACES_GEOFENCE_LONGITUDE = "longitude"
private const val AEP_PLACES_GEOFENCE_IDENTIFIER = "identifier"
private const val AEP_PLACES_GEOFENCE_RADIUS = "radius"
private const val AEP_PLACES_GEOFENCE_EXPIRATION_DURATION = "expirationDuration"


    fun geofenceFromMap(geofenceMap: ReadableMap, transitionType: Int): Geofence {
        return Geofence.Builder()
        .setRequestId(geofenceMap.getString("identifier")!!)
        .setCircularRegion(
            geofenceMap.getDouble("latitude"),
            geofenceMap.getDouble("longitude"),
            geofenceMap.getDouble("radius").toInt().toFloat()
        )
        .setExpirationDuration(geofenceMap.getDouble("expirationDuration").toLong())
        .setTransitionTypes(transitionType)
        .build()
    }

  fun locationFromMap(map: ReadableMap): Location {
    return Location("").apply {
      latitude = map.getDouble("latitude")
      longitude = map.getDouble("longitude")
      altitude = map.getDouble("altitude")
      speed = map.getDouble("speed").toFloat()
      accuracy = map.getDouble("accuracy").toFloat()
    }
  }

  fun writableArrayFromListPOIs(pois: List<PlacesPOI>): WritableArray {
    return WritableNativeArray().apply {
      pois.forEach { pushMap(convertPoiToMap(it)) }
    }
  }

  private fun convertPoiToMap(poi: PlacesPOI): WritableMap {
    return WritableNativeMap().apply {
      putString("identifier", poi.identifier)
      putString("name", poi.name)
      putDouble("latitude", poi.latitude)
      putDouble("longitude", poi.longitude)
      putDouble("radius", poi.radius.toDouble())
      putBoolean("userIsWithin", poi.containsUser()) // Correct property name
      putString("library", poi.library)
      putInt("weight", poi.weight)
      putMap("metadata", convertMetadata(poi.metadata))
    }
  }

  fun mapFromLocation(location: Location): WritableMap {
    return WritableNativeMap().apply {
      putDouble("latitude", location.latitude)
      putDouble("longitude", location.longitude)
      putDouble("altitude", location.altitude)
      putDouble("speed", location.speed.toDouble())
      putDouble("accuracy", location.accuracy.toDouble())
    }
  }

  private fun convertMetadata(metadata: Map<String, String>): WritableMap {
    return WritableNativeMap().apply {
      metadata.forEach { (key, value) -> putString(key, value) }
    }
  }

  // Add these constants
const val AEP_PLACES_AUTH_STATUS_DENIED = "PLACES_AUTH_STATUS_DENIED"
const val AEP_PLACES_AUTH_STATUS_ALWAYS = "PLACES_AUTH_STATUS_ALWAYS"
const val AEP_PLACES_AUTH_STATUS_UNKNOWN = "PLACES_AUTH_STATUS_UNKNOWN"
const val AEP_PLACES_AUTH_STATUS_RESTRICTED = "PLACES_AUTH_STATUS_RESTRICTED"
const val AEP_PLACES_AUTH_STATUS_WHEN_IN_USE = "PLACES_AUTH_STATUS_WHEN_IN_USE"

// Add conversion methods
fun placesAuthorizationStatusFromString(placesAuthStatus: String?): PlacesAuthorizationStatus {
    return when (placesAuthStatus) {
        AEP_PLACES_AUTH_STATUS_DENIED -> PlacesAuthorizationStatus.DENIED
        AEP_PLACES_AUTH_STATUS_ALWAYS -> PlacesAuthorizationStatus.ALWAYS
        AEP_PLACES_AUTH_STATUS_RESTRICTED -> PlacesAuthorizationStatus.RESTRICTED
        AEP_PLACES_AUTH_STATUS_WHEN_IN_USE -> PlacesAuthorizationStatus.WHEN_IN_USE
        else -> PlacesAuthorizationStatus.UNKNOWN
    }
}

fun stringFromPlacesAuthorizationStatus(status: PlacesAuthorizationStatus): String {
    return when (status) {
        PlacesAuthorizationStatus.DENIED -> AEP_PLACES_AUTH_STATUS_DENIED
        PlacesAuthorizationStatus.ALWAYS -> AEP_PLACES_AUTH_STATUS_ALWAYS
        PlacesAuthorizationStatus.WHEN_IN_USE -> AEP_PLACES_AUTH_STATUS_WHEN_IN_USE
        PlacesAuthorizationStatus.RESTRICTED -> AEP_PLACES_AUTH_STATUS_RESTRICTED
        else -> AEP_PLACES_AUTH_STATUS_UNKNOWN
    }
}
}
