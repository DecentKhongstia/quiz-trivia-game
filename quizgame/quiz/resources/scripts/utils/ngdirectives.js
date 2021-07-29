/*COMMAND KEY*/
const CTRL_KEY = 17;
const DELETE_KEY = 46;
const BACKSPACE_KEY = 8;
const CUT_CMD = 17 && 88;
const COPY_CMD = 17 && 67;
const PASTE_CMD = 17 && 86;

const PATTERN_ALPHA_NUMERIC_SPECIAL = /^[0-9a-zA-Z\ ()'.,]+$/;

angularApp.directive("patternAlphaNumericSpecial", function () {
  return {
    require: "?ngModel",
    link: function (scope, element, attributes, ngModelCtrl) {
      if (!ngModelCtrl) {
        return;
      }

      let oldVal = "";
      element.on("keyup", function (e) {
        if (
          !element.val().match(PATTERN_ALPHA_NUMERIC_SPECIAL) &&
          e.keyCode !== 46 && // delete
          e.keyCode !== 8 && // backspace
          e.keyCode !== CUT_CMD &&
          e.keyCode !== COPY_CMD &&
          e.keyCode !== PASTE_CMD
        ) {
          e.preventDefault();
          ngModelCtrl.$setViewValue("");
          ngModelCtrl.$render();
        } else {
          oldVal = element.val();
          ngModelCtrl.$setViewValue(oldVal);
          ngModelCtrl.$render();
        }
      });
      element.on("blur", function (e) { });
    },
  };
});

angularApp.directive("patternAlpha", function () {
  return {
    require: "?ngModel",
    link: function (scope, element, attributes, ngModelCtrl) {
      var oldVal = null;
      element.on("keyup", function (e) {
        if (
          !element.val().match(PATTERN_ALPHABETS) &&
          e.keyCode !== 46 && // delete
          e.keyCode !== 8 && // backspace
          e.keyCode !== CUT_CMD &&
          e.keyCode !== COPY_CMD &&
          e.keyCode !== PASTE_CMD
        ) {
          e.preventDefault();
          element.val(oldVal);
        } else {
          oldVal = element.val();
        }

        ngModelCtrl.$setViewValue(oldVal);
        ngModelCtrl.$render();
      });
      element.on("blur", function (e) { });
    },
  };
});

angularApp.directive("patternAlphaNumeric", function () {
  return {
    require: "?ngModel",
    link: function (scope, element, attributes, ngModelCtrl) {
      var oldVal = null;
      element.on("keyup", function (e) {
        if (
          !element.val().match(PATTERN_ALPHA_NUMERIC) &&
          e.keyCode !== 46 && // delete
          e.keyCode !== 8 && // backspace
          e.keyCode !== CUT_CMD &&
          e.keyCode !== COPY_CMD &&
          e.keyCode !== PASTE_CMD
        ) {
          e.preventDefault();
        } else {
          oldVal = element.val();
        }

        ngModelCtrl.$setViewValue(oldVal);
        ngModelCtrl.$render();
      });
      element.on("blur", function (e) { });
    },
  };
});
