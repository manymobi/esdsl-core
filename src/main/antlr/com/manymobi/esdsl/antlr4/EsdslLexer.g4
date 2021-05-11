lexer grammar EsdslLexer;

channels {
    OFF_CHANNEL,
    COMMENT
}


REQUEST_METHOD
   : 'GET'
   | 'POST'
   | 'PUT'
   | 'DELETE'
   ;


SYMBOL
    :'\'[\''
    |'\']\''
    |'\'{\''
    |'\'}\''
    |'\',\''
    |'\'.\''
    ;

LOGIC_CHARACTER
    :'=='
    |'>'
    |'>='
    |'<'
    |'<='
    |'<>'
    |'!='
    ;
WRONG
    :'!'
    |'~'
    ;
SLASH : '/' ;
LPAREN : '(' ;
RPAREN : ')' ;
COLON : ':' ;
COMMA : ',' ;
LBRACK : '[' ;
RBRACK : ']' ;
LBRACE : '{' ;
RBRACE : '}' ;
EQUAL : '=' ;
TRUE : 'true' ;
FALSE : 'false' ;
NULL : 'null' ;
IF : '#if' ;
ELSE : '#else' ;
ELSEIF : '#elseif' ;
ENDIF : '#endif' ;
FOR : '#for' ;
ENDFOR: '#endfor';
OPEN : 'open';
CLOSE : 'close';
SEPARATOR : 'separator';
IN: 'in' ;
FUNC : '==>' ;
BANG : '!' | '~' ;
AND_OR_XOR
    :AND
    |'&&'
    |'|'
    |'||'
    |'^'
    ;
ID  : [a-zA-Z_][a-zA-Z0-9_]* ;

QUESTION_MARK : '?' ;

STRING
   : '"' (ESC | SAFECODEPOINT)* '"'
   | NULL
   ;


/*单引号*/
SINGLE_QUOTATION_MARK : '\'';

AND :'&';
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

LINE_SKIPPING
    :'\n' ->  channel(OFF_CHANNEL)
    ;

WS
   : [ \t\n\r]+ ->  channel(OFF_CHANNEL)
   ;

BLOCK_COMMENT:      '/*' .*? '*/'    -> channel(COMMENT);
LINE_COMMENT:       '//' ~[\r\n]*    -> channel(COMMENT);
