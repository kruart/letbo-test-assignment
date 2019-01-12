import 'package:flutter/material.dart';
import 'loginpage.dart';
import 'homepage.dart';
import 'package:http/http.dart' show post;
import 'dart:convert';

void main() {
//  const url = 'http://10.0.2.2:8080/auth/signin';
//  const headers = {'Content-Type':'application/json'};
//  final body = jsonEncode({'name':'John Doe', 'password': 'qwerty'});
//  var resp = await post(url, headers: headers, body: body);

//  const url = 'http://10.0.2.2:8080/auth/signin';
//  const headers = {'Content-Type':'application/json'};
//  final body = jsonEncode({'name':'John Doe', 'password': 'qwerty'});
//  var statusCode = post(url, headers: headers, body: body);
//  print("fdsfsd");

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