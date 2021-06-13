parser grammar EsdslParser;

options { tokenVocab=EsdslLexer; }

esdslarray
    : esdsl*
    ;

esdsl
   : '==>' ID LINE_SKIPPING? request? json? (LINE_SKIPPING)?
   ;

request
    : REQUEST_METHOD uri
    ;


uri
   : '/'? path?  query? WS?
   ;


path
   : string? ('/' string?)*
   ;

query
   : '?' search
   ;

search
   : searchparameter (AND searchparameter?)*
   ;

searchparameter
   : string ('=' (string | NUMBER ))?
   ;

string
   :ID
   |parameter
   |TRUE
   |FALSE
   |NULL
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

parameter
    :PARAMETER
    ;

ifThenStatement
    : '#if' '(' expression ')' statement ('#elseif' '(' expression ')' statement)*('#else' statement)? '#endif'
    ;
forStatement
    :'#for' '('  (index ',')? item 'in' parameter forParameter*?  ')' statement '#endfor'
    ;
forParameter
   : 'open' '=' symbolTring
   | 'close''=' symbolTring
   | 'separator''=' symbolTring
   ;
symbolTring/*符号*/
    :SYMBOL*?
    |'\''STRING'\''
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
    : wrong? value logicCharacter value
    ;

logicCharacter
    : LOGIC_CHARACTER
    ;



wrong
    :WRONG
    ;


index
    :ID
    ;
item
    :ID
    ;





