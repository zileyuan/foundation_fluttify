// ignore_for_file: non_constant_identifier_names, camel_case_types, missing_return, unused_import
import 'dart:typed_data';

import 'package:foundation_fluttify/src/object/obejcts.dart';

import 'ns_object.dart';

class UIImage extends NSObject {
  static Future<UIImage> create(Uint8List bitmapBytes) async {
    final refId = await kMethodChannel
        .invokeMethod('UIImage::createUIImage', {'bitmapBytes': bitmapBytes});
    return UIImage()
      ..refId = refId
      ..tag__ = 'platform';
  }

  static Future<List<UIImage>> create_batch(
    List<Uint8List> bitmapBytesBatch,
  ) async {
    final List resultBatch = await kMethodChannel.invokeMethod(
      'UIImage::createUIImage_batch',
      [
        for (final bitmapBytes in bitmapBytesBatch) {'bitmapBytes': bitmapBytes}
      ],
    );
    return resultBatch
        .map((refId) => UIImage()
          ..refId = refId
          ..tag__ = 'platform')
        .toList();
  }

  Future<Uint8List> get data {
    return kMethodChannel.invokeMethod('UIImage::getData', {'refId': refId});
  }
}
