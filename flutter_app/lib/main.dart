import 'package:flutter/material.dart';
import 'loginpage.dart';

void main() {
  runApp(MainController());
}

class MainController extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
        home: LoginPage(),
    );
  }

}