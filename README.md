# Test Technical Assessments

This repository contains a sample mobile application implemented for both iOS and Android platforms. It demonstrates various UI components and serves as a foundation for testing various UI components and scenarios.

---

## Requirements

### iOS
- **Xcode**: Version 15.2 or later  
- **iOS Deployment Target**: iOS 17.1 or later  

### Android
- **Android Studio**: Version Ladybug or later  
- **Android SDK**: API Level 26 or higher (running on the device only)  

---

## Features

This sample application includes the following UI components:  
- **Picker Views**: Allow users to select options from a list.  
- **Buttons**: Standard buttons that perform actions when tapped.  
- **Alerts**: Pop-up dialogs with confirmation and cancellation options.  
- **Navigation Links**: Links that navigate to different pages.  
- **Content View**: A detailed page showcasing scrolling, title views, and advanced navigation.  

---

## Overview

Upon launching the app (Android), you’ll find:  
- **Home Page**: Includes links to explore various UI components like Pickers, Buttons, and Alerts.  
- **Content View**: A content-rich page for testing rendering, scrolling, and navigation capabilities.  
- **ComposeActions**: This is an addition to hold the logic for performing actions used by the test scripts
- **ComposeAssertions**: This is an addition to hold the logic for the assertions used by the test scripts
- **HomepageHelper**: Handles the logic for finding elements/aspects on the Homepage.
- **HomepageTests**: Houses all the tests related to functions and features on the Homepage.
---
## Assumptions
The assumptions made are the tests are to be only ran on a virtual device, therefore they are not instrumented. Addtionally, there is no missing content from the Homepage, and that content displayed is all content available.
---
## Getting Started

1. **Fork the Repository**:  
   fork the repository to your local machine.  

2. **Open the Project in the Appropriate IDE**:  
   - For Android: Open the Android project in **Android Studio**.  
   - Ensure that you can successfully open and run the project on the chosen platform. 

3. **Running the tests**:
    - Open HomepageTests.kt.
    - Use the runner or run buttons to start the selected test.
    - The IDE will start an AVD and notify when a test is completed.
    - Tests that pass successfully present a green check mark, otherwise will give a yellow x with an error why.

---

Happy testing! 🚀

