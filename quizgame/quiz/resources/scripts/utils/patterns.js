var PATTERN_ALPHABETS = /^[a-zA-Z\.\ ]+$/;  
var PATTERN_ALPHABETS_OPT = /^[a-zA-Z\.\ ]*$/;
var PATTERN_ALPHA_NUMERIC = /^[0-9a-zA-Z\ ]+$/;
var PATTERN_ALPHA_NUMERIC_OPT= /^[0-9a-zA-Z\ ]*$/;
var PATTERN_ALPHABETS_SPACE_PERIOD_COMMA_QUOTE_BRACKETS = /^[a-zA-Z\.,'()\ ]+$/;

var PATTERN_NUMERIC = /^\d+$/;
var PATTERN_NUMERIC_OPT = /^\d*$/;
var PATTERN_PERCENTAGE2D = /^\d{1,2}(?:\.\d{1,2})?$/;
var PATTERN_PERCENTAGE2D_OPT = /^(\d{1,2}(?:\.\d{1,2})?)?$/;
var PATTERN_PINCODE = /^([1-9]){1}([0-9]){5}$/;
var PATTERN_PINCODE_OPT = /^(([1-9]){1}([0-9]){5})?$/;
var PATTERN_MONEY = /^[0-9][0-9]{0,7}(\.?([0-9]){1,2})*$/;
var PATTERN_MONEY_OPT = /^([1-9][0-9]{0,7}(\.?([0-9]){2})*)?$/;

var PATTERN_NUMERICDECIMAL = /^\d+(\.\d{1,3})?$/;

var PATTERN_MOBILE = /^([1-9]){1}([0-9]){9}$/;
var PATTERN_ADHAR = /^([1-9]){1}([0-9]){11}$/;
var PATTERN_MOBILE_OPT = /^(([1-9]){1}([0-9]){9})*$/;
var PATTERN_LANDLINE = /^([0-9]){10}[0-9]?[0-9]?[0-9]?/;
var PATTERN_LANDLINE_OPT = /^(([0-9]){10}[0-9]?[0-9]?[0-9]?)?$/;
var PATTERN_EMAIL = /^[_A-Za-z0-9-]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,})$/;
var PATTERN_EMAIL_OPT = /^([_A-Za-z0-9-]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,}))?$/;


var PATTERN_DATE = /^(0?[1-9]|[12][0-9]|3[01])[\/\-\.](0?[1-9]|1[012])[\/\-\.](\d{4})$/;
var PATTERN_YEAR = /^([1-9]){1}([0-9]){3}$/;
var PATTERN_MONTH = /^(0[1-9]|1[0-2])$/;
var PATTERN_DAY = /^(0[1-9]|[1-2][0-9]|3[0-1])$/;

var PATTERN_DATE_OPT = /^((0[1-9]|[1-2][0-9]|3[0-1])-(0[1-9]|1[0-2])-([1-9]){1}([0-9]){3})?$/;
var PATTERN_YEAR_OPT = /^(([1-9]){1}([0-9]){3})?$/;
var PATTERN_MONTH_OPT = /^((0[1-9]|1[0-2]))?$/;
var PATTERN_DAY_OPT = /^((0[1-9]|[1-2][0-9]|3[0-1]))?$/;

var PATTERN_GENDER = /^(M|F|X)$/;
var PATTERN_MARITALSTATUS = /^(M|U)$/;
var PATTERN_YESNO = /^(Y|N)$/;
var PATTERN_SCSTOBCGEN = /^(SC|ST|OBC|GEN)$/;
var PATTERN_DIVISION = /^(1st|2nd|3rd)$/;

var PATTERN_IPADDRESS = /(^[2][5][0-5].|^[2][0-4][0-9].|^[1][0-9][0-9].|^[0-9][0-9].|^[0-9].)([2][0-5][0-5].|[2][0-4][0-9].|[1][0-9][0-9].|[0-9][0-9].|[0-9].)([2][0-5][0-5].|[2][0-4][0-9].|[1][0-9][0-9].|[0-9][0-9].|[0-9].)([2][0-5][0-5]|[2][0-4][0-9]|[1][0-9][0-9]|[0-9][0-9]|[0-9])$/;

var PATTERN_MAX = /^.{1}$/;
var PATTERN_LENGTH = /^.{1,150}$/;

var PATTERN_FILE_IMG = /(\.jpg|\.png|\.JPG|\.PNG|\.jpeg|\.JPEG|image\/jpeg|image\/jpg|image\/png)$/;
var PATTERN_FILE_ENC = /(\.jpg|\.png|\.JPG|\.PNG|\.jpeg|\.JPEG|\.pdf|\.PDF|image\/jpeg|image\/jpg|image\/png|application\/pdf)$/;

var PATTERN_BASIC_RESTRICTED_CHARACTER = /^([^<]|\<[^a-zA-Z])*[<]?$/;
var PATTERN_ALL_RESTRICTED_CHARACTER = /^([^\<\>\"\%\&\)\(\&\+]*)$/;
var PATTERN_TIME = /^((1[0-2]|0?[1-9]):([0-5][0-9]) ([AaPp][Mm]))$/;
var PATTERN_TIME_OPT = /^((1[0-2]|0?[1-9]):([0-5][0-9]) ([\ ][AaPp][Mm]))?$/;

var PATTERN_LICENSENO = /^(SMB\/LIC\/[1-9]{1}[0-9]{3}\/[0-9]{4})$/;