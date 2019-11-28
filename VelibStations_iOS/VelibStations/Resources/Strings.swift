// swiftlint:disable all
// Generated using SwiftGen — https://github.com/SwiftGen/SwiftGen

import Foundation

// swiftlint:disable superfluous_disable_command
// swiftlint:disable file_length

// MARK: - Strings

// swiftlint:disable explicit_type_interface function_parameter_count identifier_name line_length
// swiftlint:disable nesting type_body_length type_name
internal enum L10n {

  internal enum StationInfo {
    /// Station Info
    internal static let title = L10n.tr("Localizable", "StationInfo.Title")
    internal enum Button {
      /// Retry
      internal static let retry = L10n.tr("Localizable", "StationInfo.Button.Retry")
    }
    internal enum Error {
      /// Sorry! Failed to load data.
      internal static let data = L10n.tr("Localizable", "StationInfo.Error.Data")
    }
    internal enum Property {
      /// Code:
      internal static let code = L10n.tr("Localizable", "StationInfo.Property.Code")
      /// Latitude:
      internal static let latitude = L10n.tr("Localizable", "StationInfo.Property.Latitude")
      /// Longitude:
      internal static let longitude = L10n.tr("Localizable", "StationInfo.Property.Longitude")
      /// Name:
      internal static let name = L10n.tr("Localizable", "StationInfo.Property.Name")
      /// State:
      internal static let state = L10n.tr("Localizable", "StationInfo.Property.State")
      internal enum Credit {
        /// Credit Card:
        internal static let card = L10n.tr("Localizable", "StationInfo.Property.Credit.Card")
      }
      internal enum Density {
        /// Density Level:
        internal static let level = L10n.tr("Localizable", "StationInfo.Property.Density.Level")
      }
      internal enum Due {
        /// Due Date:
        internal static let date = L10n.tr("Localizable", "StationInfo.Property.Due.Date")
      }
      internal enum Kiosk {
        /// Kiosk State:
        internal static let state = L10n.tr("Localizable", "StationInfo.Property.Kiosk.State")
      }
      internal enum Max {
        internal enum Bike {
          /// Max Bike Overflow
          internal static let overflow = L10n.tr("Localizable", "StationInfo.Property.Max.Bike.Overflow")
        }
      }
      internal enum Nb {
        /// Bike:
        internal static let bike = L10n.tr("Localizable", "StationInfo.Property.NB.Bike")
        /// Dock:
        internal static let dock = L10n.tr("Localizable", "StationInfo.Property.NB.Dock")
        /// eBike:
        internal static let eBike = L10n.tr("Localizable", "StationInfo.Property.NB.eBike")
        /// eDock:
        internal static let eDock = L10n.tr("Localizable", "StationInfo.Property.NB.eDock")
        /// Overflow:
        internal static let overflow = L10n.tr("Localizable", "StationInfo.Property.NB.Overflow")
        internal enum Bike {
          /// Bike Overflow:
          internal static let overflow = L10n.tr("Localizable", "StationInfo.Property.NB.Bike.Overflow")
        }
        internal enum Free {
          /// Free Dock
          internal static let dock = L10n.tr("Localizable", "StationInfo.Property.NB.Free.Dock")
          /// Free eDock:
          internal static let eDock = L10n.tr("Localizable", "StationInfo.Property.NB.Free.eDock")
        }
        internal enum EBike {
          /// eBike Overflow:
          internal static let overflow = L10n.tr("Localizable", "StationInfo.Property.NB.eBike.Overflow")
        }
      }
      internal enum Overflow {
        /// Overflow Activation:
        internal static let activation = L10n.tr("Localizable", "StationInfo.Property.Overflow.Activation")
      }
    }
  }

  internal enum StationList {
    /// Search
    internal static let search = L10n.tr("Localizable", "StationList.Search")
    /// Velibs Stations
    internal static let title = L10n.tr("Localizable", "StationList.Title")
    internal enum Button {
      /// Retry
      internal static let retry = L10n.tr("Localizable", "StationList.Button.Retry")
    }
    internal enum Error {
      /// Sorry! Failed to load data.
      internal static let data = L10n.tr("Localizable", "StationList.Error.Data")
      /// Sorry! Failed to load location.
      internal static let location = L10n.tr("Localizable", "StationList.Error.Location")
      internal enum Address {
        /// Sorry! Invalid address.
        internal static let location = L10n.tr("Localizable", "StationList.Error.Address.Location")
      }
    }
  }
}
// swiftlint:enable explicit_type_interface function_parameter_count identifier_name line_length
// swiftlint:enable nesting type_body_length type_name

// MARK: - Implementation Details

extension L10n {
  private static func tr(_ table: String, _ key: String, _ args: CVarArg...) -> String {
    // swiftlint:disable:next nslocalizedstring_key
    let format = NSLocalizedString(key, tableName: table, bundle: Bundle(for: BundleToken.self), comment: "")
    return String(format: format, locale: Locale.current, arguments: args)
  }
}

private final class BundleToken {}
