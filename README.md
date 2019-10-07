# Velib Stations
Velib Stations - Kotlin Multiplatform

API:
https://opendata.paris.fr/explore/dataset/velib-disponibilite-en-temps-reel/information/

Features:

  1. Load stations.
  2. Search address / places.
  3. Staton information.
  4. Error cases.

Sources:

   1. Architecture: MVVM + AddOn
   2. Managers: Activity + Location + Service + Database + Event.
   3. Events + Callbacks ( BroadcastChannel + ReceiveChannel ).
   4. Kotlinx: Coroutine + Serializer + SQLDelight + KTor + Etc.

Note: WIP! I'll write more about it soon in details.

Generate / update CommonKit framework for iOS:
Run command << ./gradlew :common:build >> on Terminal.

Add / delete Serializable model in common library:
Please update << ServiceSerializer >> class accordingly.

Google API Keys:

Create a resource file with following contents
<resources>
	<string name="google_api_key">GOOGLE API KEY</string>
</resources>

Create a plist file ( Keys.plist ) with following contents
<dict>
	<key>GoogleAPIKey</key>
	<string>GOOGLE API KEY</string>
</dict>

Thank you.

