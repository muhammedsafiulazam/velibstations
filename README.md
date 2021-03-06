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

   1. Architecture: SOLID + MVVM + CLEAN + AddOn
   2. Managers: Activity + Location + Service + Database + Event.
   3. Events + Callbacks ( BroadcastChannel + ReceiveChannel ).
   4. Kotlinx: Coroutine + Serializer + SQLDelight + KTor + Etc.

Google API Keys:
Please add keys in Keys.xml ( Android ) and Keys.plist ( iOS ) files.

Generate / update CommonKit framework for iOS:
Run command << ./gradlew :common:build >> on Terminal.

Add / delete Serializable model in common library:
Please update << ServiceSerializer >> class accordingly.

Thank you.

