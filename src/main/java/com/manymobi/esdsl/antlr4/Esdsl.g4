/** Taken from "The Definitive ANTLR 4 Reference" by Terence Parr */

// Derived from http://json.org
grammar Esdsl;


esdslarray
    : esdsl*
    ;

esdsl
   :  methodName LINE_SKIPPING? request? json? (LINE_SKIPPING)?
   ;


methodName
    :'==> ' STRING1
    ;

request
    : REQUEST_METHOD uri
    ;


REQUEST_METHOD
   : 'GET'
   | 'POST'
   | 'PUT'
   | 'DELETE'
   ;



uri
   : '/'? path? ('?' query)? WS?
   ;


path
   : string? ('/' string?)*
   ;

query
   : '?' search
   ;

search
   : searchparameter? ('&' searchparameter?)*
   ;

searchparameter
   : string ('=' (string | NUMBER ))?
   ;

string
   :STRING1
   |parameter
   ;


json
   : value
   ;

obj
   : '{' statement (','? statement)* '}'
   | '{' '}'
   ;

pair
   : STRING ':' statement
   ;

array
   : '[' statement ((',' value)|statement)* ']'
   | '[' ']'
   ;

value
   : STRING
   | parameter
   | NUMBER
   | obj
   | array
   | 'true'
   | 'false'
   | 'null'
   ;


ifThenStatement
	:	'#if' '(' expression ')' statement ('#elseif' '(' expression ')' statement)*('#else' statement)? '#endif'
	;
forStatement
    :'#for' '('  (index ',')?item 'in'  parameter  forParameter*?  ')' statement '#endfor'
    ;
forParameter
   : 'open''='symbolTring
   | 'close''=' symbolTring
   | 'separator''=' symbolTring
   ;
symbolTring/*符号*/
    :SYMBOL*?
    |'\''STRING'\''
    ;
SYMBOL
    :'\'[\''
    |'\']\''
    |'\'{\''
    |'\'}\''
    |'\',\''
    |'\'.\''
    ;

statement
    :pair(','pair)* ','?
    |value ','?
    |ifThenStatement ','?
    |forStatement ','?
    ;

expression
    :singleIfcondition  (AND_OR_XOR  singleIfcondition)*?
    ;
singleIfcondition
    : wrong?  value  logicCharacter  value
    ;
/*逻辑符*/
logicCharacter
    :'=='
    |'>'
    |'>='
    |'<'
    |'<='
    |'<>'
    |'!='
    ;
AND_OR_XOR
    :'&'
    |'&&'
    |'|'
    |'||'
    |'^'
    ;
wrong
    :'!'
    |'~'
    ;

index
    :STRING1
    ;
item
    :STRING1
    ;

STRING1
   : ([a-zA-Z_]) ([a-zA-Z~0-9_])*
   ;


STRING
   : '"' (ESC | SAFECODEPOINT)* '"'
   |'null'
   ;
parameter
    :PARAMETER
    ;
PARAMETER
  :'#{' (ESC | SAFECODEPOINT)* '}'
  |'${' (ESC | SAFECODEPOINT)* '}'
  |'@{' (ESC | SAFECODEPOINT)* '}'
  ;

fragment ESC
   : '\\' ([}"\\/bfnrt] | UNICODE)
   ;
fragment UNICODE
   : 'u' HEX HEX HEX HEX
   ;
fragment HEX
   : [0-9a-fA-F]
   ;
fragment SAFECODEPOINT
   : ~ [}"\\\u0000-\u001F]
   ;


NUMBER
   : '-'? INT ('.' [0-9] +)? EXP?
   ;


fragment INT
   : '0' | [1-9] [0-9]*
   ;

// no leading zeros

fragment EXP
   : [Ee] [+\-]? INT
   ;

// \- since - means "range" inside [...]

LINE_SKIPPING
    :'\n' -> skip
    ;

WS
   : [ \t\n\r] + -> skip
   ;
COMMENT:            '/*' .*? '*/'    -> skip;
LINE_COMMENT:       '//' ~[\r\n]*    -> skip;
