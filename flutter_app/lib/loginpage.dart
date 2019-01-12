import 'package:flutter/material.dart';
import 'user_controller.dart';

class LoginPage extends StatefulWidget {
  @override
  _LoginPageState createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  final _formKey = GlobalKey<FormState>();

  String _name;
  String _password;
  String _errorMessage = '';

  showErrorMessage(String body) {
    setState(() {
      _errorMessage = body;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Container(
          padding: EdgeInsets.all(35.0),
          child: Form(
            key: _formKey,
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                TextFormField(
                  textAlign: TextAlign.center,
                  decoration: InputDecoration(hintText: 'Username'),
                  keyboardType: TextInputType.emailAddress,
                  onSaved: (String value) {
                    _name = value;
                  },
                  validator: (String value) {
                    if (value.length < 4) {
                      return "Username must be at least 4 characters";
                    }
                  },
                ),
                SizedBox(height: 15.0,),
                TextFormField(
                  textAlign: TextAlign.center,
                  decoration: InputDecoration(hintText: 'Password'),
                  obscureText: true,
                  onSaved: (String value) {
                    _password = value;
                  },
                  validator: (String value) {
                    if (value.length < 4) {
                      return "Password must be at least 4 characters";
                    }
                  },
                ),
                SizedBox(height: 15.0,),
                RaisedButton(
                  child: Text('Login'),
                  color: Colors.deepPurple,
                  textColor: Colors.white,
                  elevation: 10.0,
                  onPressed: () {
                    if (_formKey.currentState.validate()) {
                      _formKey.currentState.save(); //calls onSaved methods in TextFormField elements
                      UserController.signin(_name, _password)
                          .then((e) => e.statusCode == 200 ? Navigator.of(context).pushReplacementNamed('/homepage') : showErrorMessage(e.body));
                    }
                  },
                ),
                SizedBox(height: 15.0,),
                Text(
                    _errorMessage,
                    style: TextStyle(color: Colors.red)),
              ],
            ),
          ),
        ),
      ),
    );
  }
}