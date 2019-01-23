import 'package:flutter/material.dart';
import 'search_list.dart';
import 'search_bar.dart';

class SearchPage extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => new SearchState();
}

class SearchState extends State<SearchPage> {
  GlobalKey<SearchListState> _listPageKey = new GlobalKey();

  @override
  Widget build(BuildContext context) {
    return new Scaffold(
        appBar: new SearchWidget(onSubmit: _startSearch),
        body: new Container(
          child: new SearchListWidget(key: _listPageKey),
        ));
  }

  _startSearch(String str) {
    _listPageKey.currentState.lodaData(str);
  }
}
