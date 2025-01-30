import { Text, View, StyleSheet } from 'react-native';
import { multiply, extensionVersion } from 'react-native-ishitaplaces';

const result = multiply(3, 7);
// const placesExtensionVersion = extensionVersion()

const placesExtensionVersion = async () => {
  const version = await extensionVersion();
  console.log('AdobeExperienceSDK: Places version: ' + version);
};

export default function App() {
  placesExtensionVersion();
  return (
    <View style={styles.container}>
      <Text>Result: {result}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
});
