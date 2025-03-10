// index.ts
import Ishitaplaces from './NativeIshitaplaces';
import type {
  PlacesPOI,
  PlacesLocation,
} from './NativeIshitaplaces';

// Core Functions
export function multiply(a: number, b: number): number {
  return Ishitaplaces.multiply(a, b);
}

export async function extensionVersion(): Promise<string> {
  return Ishitaplaces.extensionVersion();
}

// Places Functions
export async function getNearbyPointsOfInterest(
  location: PlacesLocation,
  limit: number
): Promise<PlacesPOI[]> {
  return Ishitaplaces.getNearbyPointsOfInterest(location, limit);
}

export function processGeofence(
  geofence: {
    identifier: string;
    latitude: number;
    longitude: number;
    radius: number;
    expirationDuration: number;
  },
  transitionType: number
): void {
  Ishitaplaces.processGeofence(geofence, transitionType);
}

export async function getCurrentPointsOfInterest(): Promise<PlacesPOI[]> {
  return Ishitaplaces.getCurrentPointsOfInterest();
}

export async function getLastKnownLocation(): Promise<PlacesLocation | null> {
  return Ishitaplaces.getLastKnownLocation();
}

export function clear(): void {
  Ishitaplaces.clear();
}

export function setAuthorizationStatus(authStatus?: string): void {
  Ishitaplaces.setAuthorizationStatus(authStatus);
}

// Type Exports
export type { PlacesPOI, PlacesLocation };