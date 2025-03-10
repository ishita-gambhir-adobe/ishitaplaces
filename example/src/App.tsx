import { useState } from 'react';
import { Alert, ScrollView, Text, View, StyleSheet, Button } from 'react-native';
import { multiply, extensionVersion, getNearbyPointsOfInterest, getLastKnownLocation, clear, setAuthorizationStatus } from 'react-native-ishitaplaces';

export default function App() {
  const [result, setResult] = useState<number>(0);
  const [version, setVersion] = useState<string>('');
  const [pois, setPois] = useState<any[]>([]);
  const [location, setLocation] = useState<any>(null);

  const handleMultiply = () => {
    const calculation = multiply(3, 7);
    setResult(calculation);
    console.log('Multiplication result:', calculation);
  };

  const handleGetVersion = async () => {
    try {
      console.log('Fetching Places extension version...');
      const ver = await extensionVersion();
      setVersion(ver);
      console.log('Successfully fetched version:', ver);
      Alert.alert('Version Found', `Places version: ${ver}`);
    } catch (error) {
      console.error('Version fetch error:', error);
      Alert.alert('Error', 'Failed to get extension version');
    }
  };

  const handleGetNearbyPOIs = async () => {
    try {
      console.log('Fetching nearby POIs...');
      const sampleLocation = {
        latitude: 37.7749,
        longitude: -122.4194,
        altitude: 0,
        speed: 0,
        accuracy: 10
      };
      
      const nearbyPOIs = await getNearbyPointsOfInterest(sampleLocation, 5);
      setPois(nearbyPOIs);
      console.log('Nearby POIs:', nearbyPOIs);
      Alert.alert('Success', `Found ${nearbyPOIs.length} POIs`);
    } catch (error) {
      console.error('POI fetch error:', error);
      Alert.alert('Error', 'Failed to get nearby POIs');
    }
  };

  const handleGetLocation = async () => {
    try {
      console.log('Fetching last known location...');
      const loc = await getLastKnownLocation();
      setLocation(loc);
      console.log('Location found:', loc);
      Alert.alert('Location Found', JSON.stringify(loc, null, 2));
    } catch (error) {
      console.error('Location error:', error);
      Alert.alert('Error', 'Failed to get location');
    }
  };

  const handleClearData = () => {
    console.log('Clearing Places data...');
    clear();
    setPois([]);
    setLocation(null);
    Alert.alert('Cleared', 'All Places data cleared');
  };

  return (
    <ScrollView contentContainerStyle={styles.container}>
      <Text style={styles.title}>Places Module Tester</Text>

      <View style={styles.buttonContainer}>
        <Button
          title="Test Multiply (3x7)"
          onPress={handleMultiply}
          color="#007AFF"
        />
        <Text style={styles.result}>Result: {result}</Text>
      </View>

      <View style={styles.buttonContainer}>
        <Button
          title="Get Places Version"
          onPress={handleGetVersion}
          color="#34C759"
        />
        <Text style={styles.result}>Version: {version || 'Not fetched'}</Text>
      </View>

      <View style={styles.buttonContainer}>
        <Button
          title="Get Nearby POIs"
          onPress={handleGetNearbyPOIs}
          color="#FF9500"
        />
        <Text style={styles.result}>POIs Found: {pois.length}</Text>
      </View>

      <View style={styles.buttonContainer}>
        <Button
          title="Get Last Location"
          onPress={handleGetLocation}
          color="#AF52DE"
        />
        <Text style={styles.result}>
          Location: {location ? `${location.latitude}, ${location.longitude}` : 'Unknown'}
        </Text>
      </View>

      <View style={styles.buttonContainer}>
        <Button
          title="Clear Data"
          onPress={handleClearData}
          color="#FF3B30"
        />
      </View>
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: {
    flexGrow: 1,
    padding: 20,
    backgroundColor: '#F5FCFF',
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 20,
    textAlign: 'center',
    color: '#333',
  },
  buttonContainer: {
    marginBottom: 20,
    padding: 15,
    backgroundColor: '#FFF',
    borderRadius: 10,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 4,
    elevation: 3,
  },
  result: {
    marginTop: 10,
    fontSize: 16,
    color: '#666',
  },
});
