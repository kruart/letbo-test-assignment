import 'package:flutter/material.dart';

class LoginPage extends StatefulWidget {
  @override
  _LoginPageState createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Container(
          padding: EdgeInsets.all(35.0),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              TextField(
                textAlign: TextAlign.center,
                decoration: InputDecoration(hintText: 'Username'),
                keyboardType: TextInputType.emailAddress,
              ),
              SizedBox(height: 15.0,),
              TextField(
                textAlign: TextAlign.center,
                decoration: InputDecoration(hintText: 'Password'),
                obscureText: true,
              ),
              SizedBox(height: 15.0,),
              RaisedButton(
                child: Text('Login'),
                color: Colors.deepPurple,
                textColor: Colors.white,
                elevation: 10.0,
                onPressed: () {
                  Navigator.of(context).pushReplacementNamed('/homepage');
                },
              ),
            ],
          ),
        ),
      ),
    );
  }
}