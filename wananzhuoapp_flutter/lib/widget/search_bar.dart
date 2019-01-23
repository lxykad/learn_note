import 'package:flutter/material.dart';

/// 自定义搜索 appbar
class SearchWidget extends StatefulWidget implements PreferredSizeWidget {
  final ValueChanged<String> onSubmit;

  const SearchWidget({Key key, this.onSubmit}) : super(key: key);

  @override
  State<StatefulWidget> createState() => new SearchState();

  @override
  Size get preferredSize => new Size.fromHeight(50);
}

class SearchState extends State<SearchWidget> {
  TextEditingController controller = new TextEditingController();

  @override
  Widget build(BuildContext context) {
    /// 通过SafeArea 取消默认沉浸式
    return new SafeArea(
      top: true,
      child: new Container(
        decoration: new BoxDecoration(color: Colors.blue),
        child: new Row(
          children: <Widget>[
            new IconButton(
                icon: new Icon(Icons.arrow_back),
                color: Colors.white,
                onPressed: _goBack),
            new Expanded(
              child: new TextField(
                controller: controller,
                decoration: new InputDecoration(
                  hintText: "试试搜索你想要的内容",
                  hintStyle: new TextStyle(color: Colors.white, fontSize: 13),
                ),
                style: new TextStyle(color: Colors.white),
                onSubmitted: _onSearchClick,
              ),
            ),
            new IconButton(
                icon: new Icon(Icons.search),
                color: Colors.white,
                onPressed: _onSearchViewClick),
          ],
        ),
      ),
    );
  }

  _goBack() {
    Navigator.of(context).pop();
  }

  _onSearchViewClick() {
    _onSearchClick(controller.text);
  }

  _onSearchClick(String str) {
    widget.onSubmit(str);
  }
}
