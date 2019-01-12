class ValidationMixin {
  String validateName(String value) {
    if (value.length < 4) {
      return "Username must be at least 4 characters";
    }
    return null;
  }

  String validatePassword(String value) {
    if (value.length < 4) {
      return "Password must be at least 4 characters";
    }
    return null;
  }
}