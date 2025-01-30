import Ishitaplaces from './NativeIshitaplaces';

export function multiply(a: number, b: number): number {
  return Ishitaplaces.multiply(a, b);
}

export async function extensionVersion(): Promise<string> {
  return Ishitaplaces.extensionVersion();
}
