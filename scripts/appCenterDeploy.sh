#!/bin/bash -e

# Distribute the APK via App Center

APP_NAME="Qenawi/BuildExample"
FILE_PATH="app/build/outputs/apk/debug/app-debug.apk"
GROUP_NAME="Testers"  # Replace with your distribution group
RELEASE_NOTES="Automated release via GitHub Actions"
TOKEN="$Token"  # Replace with your actual token or use an environment variable

# Run the appcenter distribute command
appcenter distribute release \
  --app "$APP_NAME" \
  --file "$FILE_PATH" \
  --group "$GROUP_NAME" \
  --release-notes "$RELEASE_NOTES" \
  --token "$TOKEN"
