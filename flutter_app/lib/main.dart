import 'package:flutter/material.dart';
import 'loginpage.dart';
import 'homepage.dart';

void main() {
  runApp(MainController());
}

class MainController extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
        home: LoginPage(),
        routes: <String, WidgetBuilder> {
          '/landingpage': (BuildContext context) => LoginPage(),
          '/homepage': (BuildContext context) => HomePage(),
        }
    );
  }

}