import 'package:flutter/material.dart';
import 'user_controller.dart';
import 'validation_mixin.dart';

class LoginPage extends StatefulWidget {
  @override
  _LoginPageState createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> with ValidationMixin {
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
                _usernameField(),
                SizedBox(height: 15.0,),
                _passwordField(),
                SizedBox(height: 15.0,),
                _loginButton(),
                SizedBox(height: 15.0,),
                Text(_errorMessage, style: TextStyle(color: Colors.red)),
              ],
            ),
          ),
        ),
      ),
    );
  }


  Widget _usernameField() {
    return TextFormField(
      textAlign: TextAlign.center,
      decoration: _inputDecoration('Username'),
//      keyboardType: TextInputType.emailAddress,
      onSaved: (String value) => _name = value,
      validator: validateName,
    );
  }

  Widget _passwordField() {
    return TextFormField(
      textAlign: TextAlign.center,
      decoration: _inputDecoration('Password'),
      obscureText: true,
      onSaved: (String value) => _password = value,
      validator: validatePassword,
    );
  }

  Widget _loginButton() {
    return RaisedButton(
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
    );
  }

  InputDecoration _inputDecoration(String hintText) {
    return InputDecoration(
      hintText: hintText,
      focusedBorder: UnderlineInputBorder(borderSide: BorderSide(color: Colors.deepPurple, width: 2.0)),
    );
  }
}