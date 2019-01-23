import 'package:flutter/material.dart';

class EmptyWidget extends StatelessWidget {
  final String msg;

  const EmptyWidget({Key key, this.msg}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Text(null == msg ? "loading……" : msg),
    );
  }
}
