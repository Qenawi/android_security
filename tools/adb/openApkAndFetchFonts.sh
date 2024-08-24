#!/bin/bash

# Function to list installed packages and allow user selection
select_package() {
    echo "Fetching list of installed packages..."
    adb shell pm list packages -f > packages.txt

    # Extract package names
    awk -F= '{print NR, $2}' packages.txt

    # Prompt user to select a package by number
    read -p "Select a package by number: " package_number

    # Get the selected package name and path
    selected_package=$(awk -F= 'NR=='"$package_number"'{print $2}' packages.txt)
    apk_path=$(awk -F= 'NR=='"$package_number"'{print $1}' packages.txt | sed 's/package://g')

    echo "Selected package: $selected_package"
    echo "APK path: $apk_path"
}

# Function to fetch the APK from the device
fetch_apk() {
    mkdir -p apkpackage
    adb pull "$apk_path" "apkpackage/$(basename "$apk_path")"
    echo "APK fetched to apkpackage/$(basename "$apk_path")"
}

# Function to decompile the APK using Apktool
decompile_apk() {
    apktool d "apkpackage/$(basename "$apk_path")" -o apkpackage/decompiled
    echo "APK decompiled to apkpackage/decompiled"
}

# Function to extract fonts
extract_fonts() {
    mkdir -p apkpackage/fonts
    cp -r apkpackage/decompiled/res/fonts/* apkpackage/fonts/
    echo "Fonts extracted to apkpackage/fonts"
}

# Main script execution
adb devices

select_package
fetch_apk
decompile_apk
extract_fonts

echo "Process completed successfully."
