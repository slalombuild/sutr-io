package com.slalom.aws.avs.utr;
import com.intellij.lexer.*;
import com.intellij.psi.tree.IElementType;
import static com.slalom.aws.avs.utr.psi.UtrTypes.*;

%%

%{
  public _UtrLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class _UtrLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL="\r"|"\n"|"\r\n"
LINE_WS=[\ \t\f]
WHITE_SPACE=({LINE_WS}|{EOL})+

WORD=[a-zA-Z']+
NAME=[a-zA-Z]+
IDENTIFIER=[a-zA-Z_]+
%state YYUTTERANCE

%%
<YYINITIAL> {
  {WHITE_SPACE}      { return com.intellij.psi.TokenType.WHITE_SPACE; }

  {IDENTIFIER}           { yybegin(YYUTTERANCE); return UtrINTENT; }

  [^] { return com.intellij.psi.TokenType.BAD_CHARACTER; }
}

<YYUTTERANCE>{
  {WORD}             { return UtrWORD; }
  {LINE_WS}         {return com.intellij.psi.TokenType.WHITE_SPACE;}
  "{"               { return UtrLB; }
    "}"               { return UtrRB; }
    "|"         {return UtrPIPE;}
  {EOL}              { yybegin(YYINITIAL); return UtrEOL; }
  [^] { return com.intellij.psi.TokenType.BAD_CHARACTER; }
}