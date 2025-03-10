import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';

export interface Spec extends TurboModule {
  multiply(a: number, b: number): number;
  extensionVersion(): Promise<string>;
  
  getNearbyPointsOfInterest(
    location: {
      latitude: number;
      longitude: number;
      altitude: number;
      speed: number;
      accuracy: number;
    },
    limit: number
  ): Promise<PlacesPOI[]>;

  processGeofence(
    geofence: {
      identifier: string;
      latitude: number;
      longitude: number;
      radius: number;
      expirationDuration: number;
    },
    transitionType: number
  ): void;

  getCurrentPointsOfInterest(): Promise<PlacesPOI[]>;
  getLastKnownLocation(): Promise<PlacesLocation | null>;
  clear(): void;
  setAuthorizationStatus(authStatus?: string): void;
}

export interface PlacesPOI {
  identifier: string;
  name: string;
  latitude: number;
  longitude: number;
  radius: number;
  userIsWithin: boolean;
  library: string;
  weight: number;
  metadata: { [key: string]: string };
}

export interface PlacesLocation {
  latitude: number;
  longitude: number;
  altitude: number;
  speed: number;
  accuracy: number;
}

export default TurboModuleRegistry.getEnforcing<Spec>('Ishitaplaces');
