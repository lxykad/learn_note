import 'package:flutter/material.dart';
import 'dart:ui';
import 'widget/search_page.dart';

void main() => runApp(new MaterialApp(
      home: _getWidget(window.defaultRouteName),
    ));

Widget _getWidget(String route) {
  if (route != null && route != '/') {
    switch (route) {
      case "search":
        return new SearchPage();
      default:
        return new Text(
          "路由不匹配",
          textDirection: TextDirection.ltr,
        );
    }
  } else {
//    return new Text(
//      "请设置路由",
//      textDirection: TextDirection.ltr,
//    );

    return new SearchPage();
  }
}
