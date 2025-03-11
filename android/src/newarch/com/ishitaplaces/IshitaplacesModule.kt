package com.ishitaplaces

import android.location.Location
import android.util.Log
import com.adobe.marketing.mobile.AdobeCallback
import com.adobe.marketing.mobile.Places
import com.adobe.marketing.mobile.places.PlacesPOI
import com.adobe.marketing.mobile.places.PlacesRequestError
import com.facebook.react.bridge.*
import com.facebook.react.module.annotations.ReactModule
import com.adobe.marketing.mobile.places.PlacesAuthorizationStatus
import android.os.Build

@ReactModule(name = IshitaplacesModule.NAME)
class IshitaplacesModule(reactContext: ReactApplicationContext) :
  NativeIshitaplacesSpec(reactContext) {

  override fun getName() = NAME

  override fun multiply(a: Double, b: Double) = a * b

  override fun extensionVersion(promise: Promise) {
    promise.resolve(Places.extensionVersion())
  }

  override fun getNearbyPointsOfInterest(
    location: ReadableMap,
    limit: Double,
    promise: Promise
  ) {
    try {
      Places.getNearbyPointsOfInterest(
        RCTAEPPlacesDataBridge.locationFromMap(location),
        limit.toInt(),
        object : AdobeCallback<List<PlacesPOI>> {
          override fun call(pois: List<PlacesPOI>) {
            promise.resolve(RCTAEPPlacesDataBridge.writableArrayFromListPOIs(pois))
          }
        },
        object : AdobeCallback<PlacesRequestError> {
          override fun call(error: PlacesRequestError) {
            promise.reject("GET_POIS_ERROR", "Error ${error.value}: ${error.name}")
          }
        }
      )
    } catch (e: Exception) {
      promise.reject("INVALID_INPUT", e.message)
    }
  }

  // IshitaplacesModule.kt
override fun processGeofence(geofence: ReadableMap, transitionType: Double) {
    try {
        // Original Android transition type adjustment
        val adjustedTransition = transitionType.toInt() + 1
        Places.processGeofence(
            RCTAEPPlacesDataBridge.geofenceFromMap(geofence, adjustedTransition),
            adjustedTransition
        )
    } catch (e: Exception) {
        Log.e(NAME, "Geofence error: ${e.message}")
    }
}

  override fun getCurrentPointsOfInterest(promise: Promise) {
    Places.getCurrentPointsOfInterest(object : AdobeCallback<List<PlacesPOI>> {
      override fun call(pois: List<PlacesPOI>) {
        promise.resolve(RCTAEPPlacesDataBridge.writableArrayFromListPOIs(pois))
      }
    })
  }

  override fun getLastKnownLocation(promise: Promise) {
    Places.getLastKnownLocation(object : AdobeCallback<Location?> {
      override fun call(location: Location?) {
        location?.let {
          promise.resolve(RCTAEPPlacesDataBridge.mapFromLocation(it))
        } ?: run {
          promise.resolve(null)
        }
      }
    })
  }

  override fun clear() {
    Places.clear()
  }

  override fun setAuthorizationStatus(authStatus: String?) {
    authStatus?.let {
        Places.setAuthorizationStatus(
            when (it) {
                "PLACES_AUTH_STATUS_ALWAYS" -> PlacesAuthorizationStatus.ALWAYS
                "PLACES_AUTH_STATUS_DENIED" -> PlacesAuthorizationStatus.DENIED
                "PLACES_AUTH_STATUS_RESTRICTED" -> PlacesAuthorizationStatus.RESTRICTED
                "PLACES_AUTH_STATUS_WHEN_IN_USE" -> PlacesAuthorizationStatus.WHEN_IN_USE
                else -> PlacesAuthorizationStatus.UNKNOWN
            }
        )
    }
  }

  companion object {
    const val NAME = "Ishitaplaces"
  }
}
