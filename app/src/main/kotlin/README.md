# MainActivity.kt Overview

This README provides an overview of the `MainActivity.kt` file which serves as an example of how to interact with the tbdex SDK in an Android application.

## Structure

The `MainActivity.kt` includes several key components:

- Initialization of the main activity and setting the content view.
- Fetching a DID (Decentralized Identifier) from a PFI server.
- Retrieving a Verifiable Credential (VC) using the DID.
- Displaying the VC and offerings from a PFI server in the UI.

## Usage

To run the example, simply build and run the application. The main activity will perform the following actions:

1. Create a new DID for the app and store it securely.
2. Fetch a DID from the PFI server.
3. Request a VC using the app's DID.
4. Retrieve offerings from the PFI server and display them in a RecyclerView.

Please refer to the inline comments in `MainActivity.kt` for more detailed explanations of each step and the corresponding code.

