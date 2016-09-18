package com.slalom.aws.avs.sutr;
import com.intellij.lexer.*;
import com.intellij.psi.tree.IElementType;
import static com.slalom.aws.avs.sutr.psi.SutrTypes.*;

%%

%{
  public _SutrLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class _SutrLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL="\r"|"\n"|"\r\n"
LINE_WS=[\ \t\f]
WHITE_SPACE=({LINE_WS}|{EOL})+

COMMENT=#[^\r\n]*
PHRASE=[a-zA-Z0-9' ]+
WORD=[a-zA-Z']+
NAME=[a-zA-Z]+
IDENTIFIER=[a-zA-Z_]+
FUNCTION_IDENTIFIER=[a-zA-Z_\.]+

%state YYSUTR
%state YYSUTR_PHRASES
%%
<YYINITIAL> {
  {WHITE_SPACE}      { return com.intellij.psi.TokenType.WHITE_SPACE; }


  "def"              { yybegin(YYSUTR); return SutrDEF; }
  "literal"          { return SutrLITERAL; }
  "type"             { return SutrTYPE; }
  "from"             { return SutrFROM; }
  "import"             { return SutrIMPORT; }
 }

<YYINITIAL, YYSUTR> {
  {LINE_WS} { return com.intellij.psi.TokenType.WHITE_SPACE; }
  "{"                { return SutrLB; }
  "}"                { return SutrRB; }
  "("                { return SutrLP; }
  ")"                { return SutrRP; }
  "["                { yybegin(YYSUTR_PHRASES); return SutrLS; }
  "]"                { yybegin(YYSUTR); return SutrRS; }
  ","                { return SutrCOMMA; }
  {NAME}             { return SutrNAME; }
  {IDENTIFIER}       { return SutrIDENTIFIER; }
  {WORD}             { return SutrWORD; }
  {COMMENT}          { return SutrCOMMENT; }
  {FUNCTION_IDENTIFIER} {return SutrFUNCTION_IDENTIFIER; }
}

<YYSUTR> {
    {LINE_WS} { return com.intellij.psi.TokenType.WHITE_SPACE; }
    "def"              { return SutrWORD; }
    "literal"          { return SutrWORD; }
    "type"             { return SutrWORD; }
    "from"             { return SutrWORD; }
    "import"             { return SutrIMPORT; }
    {EOL}              { return SutrEOL; }
    "=>"               { yybegin(YYINITIAL); return SutrFP; }
}

<YYSUTR_PHRASES> {
  {WHITE_SPACE} { return com.intellij.psi.TokenType.WHITE_SPACE; }
  "]" { yybegin(YYINITIAL); return SutrRS; }
  {PHRASE} { return SutrPHRASE; }
}

[^] { return com.intellij.psi.TokenType.BAD_CHARACTER; }