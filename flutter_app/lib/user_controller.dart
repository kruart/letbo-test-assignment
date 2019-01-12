import 'package:http/http.dart' show Response, post;
import 'dart:convert';

class UserController {
  static Future<Response> signin(String name, String password) async {
      const url = 'http://10.0.2.2:8080/auth/signin';
      const headers = {'Content-Type':'application/json'};
//    final body = jsonEncode({'name':'John Doe', 'password': 'qwerty'});
      final body = jsonEncode({'name': name, 'password': password});

      return await post(url, headers: headers, body: body)
          .catchError((e) => print(e));
  }
}