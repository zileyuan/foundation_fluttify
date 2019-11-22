import 'package:foundation_fluttify/src/type/platform/ios_type/ns_object.dart';

import '../../../constants.dart';

class CLFloor extends NSObject {
  Future<String> get level {
    return kMethodChannel.invokeMethod('CLFloor::get_level', {'refId': refId});
  }
}
